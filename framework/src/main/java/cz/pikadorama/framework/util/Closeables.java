package cz.pikadorama.framework.util;

import android.util.Log;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Tomas on 11.8.2015.
 */
public class Closeables {

    private Closeables() {}

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {    
            try {
                closeable.close();
            } catch (IOException e) {
                Log.w("CP", e.getMessage(), e);
            } catch (Exception e) {
                Log.e("CP", e.getMessage(), e);
            }
        }
    }

    public static void close(Closeable closeable, boolean swallowIoException) throws IOException {
        com.google.common.io.Closeables.close(closeable, swallowIoException);
    }

}
