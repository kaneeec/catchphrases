package cz.pikadorama.catchphrasecreator.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.pikadorama.catchphrasecreator.Const;
import cz.pikadorama.catchphrasecreator.R;
import cz.pikadorama.catchphrasecreator.pojo.CatchPhrase;
import cz.pikadorama.catchphrasecreator.pojo.Collection;
import cz.pikadorama.catchphrasecreator.pojo.State;
import cz.pikadorama.catchphrasecreator.util.SoundPlayer;
import cz.pikadorama.framework.database.DaoManager;
import cz.pikadorama.framework.database.dao.Dao;
import cz.pikadorama.framework.event.EventManager;
import cz.pikadorama.framework.event.EventType;
import cz.pikadorama.framework.util.FilePicker;
import cz.pikadorama.framework.util.Views;

/**
 * Created by Tomas on 26.9.2015.
 */
public class CreateCollectionActivity extends BaseActivity {

    private List<LineObjects> lines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collection);

        initToolbar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case FilePicker.FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    loadSoundFromFile(intent);
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_add_catchphrase:
                Intent intent = FilePicker.getIntent(this);
                startActivityForResult(intent, FilePicker.FILE_SELECT_CODE);
                break;
            case R.id.menu_save:
                saveCollection();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        Toolbar toolbar = requireView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_cancel);
    }

    private void saveCollection() {
        Dao<CatchPhrase> catchPhraseDao = DaoManager.getDao(CatchPhrase.class);
        List<CatchPhrase> catchPhrases = new ArrayList<>();
        for (LineObjects line : lines) {
            CatchPhrase catchPhrase = new CatchPhrase(line.getEditText().getText().toString(), line.getSoundData());
            catchPhraseDao.create(catchPhrase);
            catchPhrases.add(catchPhrase);
        }

        EditText collectionEditText = requireView(R.id.input_collection_name);
        Collection collection = new Collection(collectionEditText.getText().toString(), 0.0, getResources().getColor
                (R.color.primary), State.PERSONAL_PRIVATE, catchPhrases);

        Dao<Collection> collectionDao = DaoManager.getDao(Collection.class);
        collectionDao.create(collection);

        EventManager.notifyEventProcessors(EventType.DATABASE_UPDATED);
    }

    private void loadSoundFromFile(Intent intent) {
        try {
            // load data
            String filePath = FilePicker.resolvePath(CreateCollectionActivity.this, intent.getData());
            File file = new File(filePath);
            byte[] soundData = Files.toByteArray(file);

            // initialize item and add it to the layout
            LinearLayout layout = requireView(R.id.layout);
            View item = LayoutInflater.from(CreateCollectionActivity.this).inflate(R.layout
                            .item_manage_collection_item,
                    null);

            EditText editText = Views.require(item, R.id.text);
            editText.setText(file.getName());

            Button playButton = Views.require(item, R.id.play_sound_button);
            playButton.setOnClickListener(new PlayCatchPhraseButtonListener(CreateCollectionActivity.this, soundData));

            LineObjects line = new LineObjects(editText, playButton, layout, item, soundData);
            lines.add(line);

            layout.addView(item);

            // prepare remove button
            Button removeButton = Views.require(item, R.id.remove_item_button);
            removeButton.setOnClickListener(new RemoveCatchPhraseButtonListener(line, lines));
        } catch (IOException e) {
            Log.e(Const.TAG, e.getMessage(), e);
            Toast.makeText(this, "Tento soubor nelze načíst. Zkuste jiný.", Toast.LENGTH_SHORT).show();
        }
    }

    private static final class RemoveCatchPhraseButtonListener implements View.OnClickListener {

        private final LineObjects line;
        private final List<LineObjects> lines;

        public RemoveCatchPhraseButtonListener(LineObjects line, List<LineObjects> lines) {
            this.line = line;
            this.lines = lines;
        }

        @Override
        public void onClick(View v) {
            lines.remove(line);
            line.getParentLayout().removeView(line.getItem());
        }
    }

    private static final class PlayCatchPhraseButtonListener implements View.OnClickListener {

        private final Context context;
        private final byte[] data;

        public PlayCatchPhraseButtonListener(Context context, byte[] data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            SoundPlayer.getInstance(context).play(data);
        }
    }

    public static final class LineObjects {
        private final LinearLayout parentLayout;
        private final View item;

        private final EditText editText;
        private final Button playButton;

        private byte[] soundData;

        public LineObjects(EditText editText, Button playButton, LinearLayout parentLayout, View item) {
            this.editText = editText;
            this.playButton = playButton;
            this.parentLayout = parentLayout;
            this.item = item;
        }

        public LineObjects(EditText editText, Button playButton, LinearLayout parentLayout, View item, byte[] soundData) {
            this(editText, playButton, parentLayout, item);
            this.soundData = soundData;
        }

        public byte[] getSoundData() {
            return soundData;
        }

        public Button getPlayButton() {
            return playButton;
        }

        public LinearLayout getParentLayout() {
            return parentLayout;
        }

        public View getItem() {
            return item;
        }

        public EditText getEditText() {
            return editText;
        }
    }
}
