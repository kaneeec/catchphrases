package cz.pikadorama.catchphrasecreator.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

import cz.pikadorama.catchphrasecreator.Const;
import cz.pikadorama.catchphrasecreator.R;

/**
 * Created by Tomas on 14.9.2015.
 */
public class SoundPlayer {

    private static SoundPlayer instance;

    private final Context context;
    private final MediaPlayer mediaPlayer;

    public SoundPlayer(Context context) {
        this.context = context;
        this.mediaPlayer = new MediaPlayer();
    }

    public synchronized static SoundPlayer getInstance(Context context) {
        if (instance == null) {
            instance = new SoundPlayer(context);
        }
        return instance;
    }

    public synchronized void play(byte[] buffer) {
        try {
            File soundFile = File.createTempFile("cp-sound-to-play", "sound", context.getCacheDir());
            soundFile.deleteOnExit();
            Files.write(buffer, soundFile);

            mediaPlayer.reset();
            mediaPlayer.setDataSource(context, Uri.fromFile(soundFile));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ex) {
            Log.e(Const.TAG, ex.getMessage(), ex);
            Toast.makeText(context, R.string.error_cannot_play_sound, Toast.LENGTH_SHORT).show();
        }
    }

}
