package cz.pikadorama.framework.util;

import android.util.Log;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Tomas on 11.8.2015.
 */
public class Resources {

    private Resources() {}

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {    
            try {
                closeable.close();
            } catch (IOException e) {
                Log.e("CP", e.getMessage(), e);
            }
        }
    }

}
