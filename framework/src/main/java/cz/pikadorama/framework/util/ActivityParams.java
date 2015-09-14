package cz.pikadorama.framework.util;

import android.util.LruCache;

/**
 * Created by Tomas on 13.9.2015.
 */
public class ActivityParams {

    private static final LruCache<Enum<?>, Object> values = new LruCache<>(5);

    /**
     * Store param for activity intent.
     *
     * @param key param key
     * @param value param value
     */
    public static <E extends Enum<E>> void store(E key, Object value) {
        values.put(key, value);
    }

    /**
     * Load param as it is in the activity intent. The parameter is removed after fetch.
     *
     * @param key param key
     * @param <T> param value type
     * @return parameter value
     */
    public static <T, E extends Enum<E>> T load(E key) {
        Object value = values.get(key);
        values.remove(key);
        return (T) value;
    }

}
