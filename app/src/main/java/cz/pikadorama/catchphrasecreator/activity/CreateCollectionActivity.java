package cz.pikadorama.catchphrasecreator.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.common.io.Files;

import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.pikadorama.catchphrasecreator.Const;
import cz.pikadorama.catchphrasecreator.R;
import cz.pikadorama.catchphrasecreator.config.ConfigManager;
import cz.pikadorama.catchphrasecreator.pojo.State;
import cz.pikadorama.catchphrasecreator.util.SoundPlayer;
import cz.pikadorama.framework.util.FilePicker;
import cz.pikadorama.framework.util.Views;

/**
 * Created by Tomas on 26.9.2015.
 */
public class CreateCollectionActivity extends BaseActivity {

    private List<EditCollectionActivity.LineObjects> lines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collection);

        Button loadButton = findView(R.id.load_button);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FilePicker.getIntent(CreateCollectionActivity.this);
                startActivityForResult(intent, FilePicker.FILE_SELECT_CODE);
            }
        });

        Button saveButton = findView(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case FilePicker.FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        String filePath = FilePicker.resolvePath(CreateCollectionActivity.this, intent.getData());
                        File file = new File(filePath);
                        byte[] soundData = Files.toByteArray(file);

                        View item = LayoutInflater.from(CreateCollectionActivity.this).inflate(R.layout
                                        .item_manage_collection_item,
                                null);

                        EditText editText = Views.require(item, R.id.text);
                        editText.setText(file.getName());

                        Button playButton = Views.require(item, R.id.play_sound_button);
                        playButton.setOnClickListener(new PlayListener(CreateCollectionActivity.this, soundData));

                        LinearLayout layout = findView(R.id.layout);
                        layout.addView(item);

                        // TODO add line
                        // TODO remove line button
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    private static final class PlayListener implements View.OnClickListener {

        private final Context context;
        private final byte[] data;

        public PlayListener(Context context, byte[] data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            SoundPlayer.getInstance(context).play(data);
        }
    }
}
