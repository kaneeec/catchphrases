package cz.pikadorama.framework.bundle;

import android.util.LruCache;

/**
 * Created by Tomas on 13.9.2015.
 */
public class ActivityParams {

    private static final LruCache<String, Object> values = new LruCache<>(5);

    public static void store(String key, Object value) {
        values.put(key, value);
    }

    public static <T> T load(String key) {
        return (T) values.get(key);
    }

}
