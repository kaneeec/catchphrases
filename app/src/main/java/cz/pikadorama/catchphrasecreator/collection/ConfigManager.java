package cz.pikadorama.catchphrasecreator.collection;

import android.util.Log;

import com.google.common.io.Files;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import cz.pikadorama.catchphrasecreator.Const;

/**
 * Created by Tomas on 14.9.2015.
 */
public class ConfigManager {

    public static Config load(String fileUri) {
        try {
            String json = Files.toString(new File(fileUri), Charset.defaultCharset());
            Gson gson = new Gson();
            return gson.fromJson(json, Config.class);
        } catch (IOException e) {
            Log.e(Const.TAG, e.getMessage(), e);
            return null;
        }
    }

    public static void store(Config config) {
        Gson gson = new Gson();
        gson.toJson(config);
    }

}
