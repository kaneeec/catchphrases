package cz.pikadorama.catchphrasecreator.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.pikadorama.catchphrasecreator.Const;
import cz.pikadorama.catchphrasecreator.R;
import cz.pikadorama.catchphrasecreator.pojo.CatchPhrase;
import cz.pikadorama.catchphrasecreator.pojo.Collection;
import cz.pikadorama.catchphrasecreator.util.SoundPlayer;
import cz.pikadorama.framework.database.DaoManager;
import cz.pikadorama.framework.database.dao.Dao;
import cz.pikadorama.framework.util.Views;

/**
 * Created by Tomas on 25.9.2015.
 */
public class ManageCollectionActivity extends BaseActivity {

    private Map<Integer, CatchPhrase> phrasesById = new HashMap<>();
    private List<LineObjects> lines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_collection);

        Dao<Collection> collectionDao = DaoManager.getDao(Collection.class);
        Collection collection = collectionDao.findAll().get(0);

        LinearLayout layout = findView(R.id.layout);

        for (CatchPhrase catchPhrase : collection.getCatchPhrases()) {
            Log.d(Const.TAG, catchPhrase.toString());
            View item = LayoutInflater.from(ManageCollectionActivity.this).inflate(R.layout
                    .item_manage_collection_item, null);

            EditText itemText = Views.require(item, R.id.text);
            itemText.setText(catchPhrase.getText());

            Button button = Views.require(item, R.id.play_sound_button);
            button.setId(catchPhrase.getId());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CatchPhrase catchPhrase1 = phrasesById.get(v.getId());
                    SoundPlayer.getInstance(ManageCollectionActivity.this).play(catchPhrase1.getSoundData());
                }
            });

            layout.addView(item);

            phrasesById.put(catchPhrase.getId(), catchPhrase);
            lines.add(new LineObjects(itemText, button));
        }

        Button editButton = findView(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (LineObjects line : lines) {
                    CatchPhrase catchPhrase = phrasesById.get(line.getPlayButton().getId());
                    catchPhrase.setText(line.getEditText().getText().toString());
                    Dao<CatchPhrase> catchPhraseDao = DaoManager.getDao(CatchPhrase.class);
                    catchPhraseDao.update(catchPhrase);
                    finish();
                }
            }
        });

    }

    private static final class LineObjects {
        private final EditText editText;
        private final Button playButton;

        public LineObjects(EditText editText, Button playButton) {
            this.editText = editText;
            this.playButton = playButton;
        }

        public Button getPlayButton() {
            return playButton;
        }

        public EditText getEditText() {
            return editText;
        }
    }
}
