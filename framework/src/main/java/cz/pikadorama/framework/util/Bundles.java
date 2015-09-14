package cz.pikadorama.framework.util;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by Tomas on 13.9.2015.
 */
public class Bundles {

    private Bundles() {
    }

    public static <E extends Enum<E>> Bundle of(E key, Serializable value) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(key.name(), value);
        return bundle;
    }

    public static <E extends Enum<E>> Bundle of(E key1, Serializable value1, E key2, Serializable
            value2) {
        Bundle bundle = of(key1, value1);
        bundle.putSerializable(key2.name(), value2);
        return bundle;
    }

    public static <T, E extends Enum<E>> T getValueFromIntent(Intent intent, E key) {
        return (T) intent.getSerializableExtra(key.name());
    }
}
