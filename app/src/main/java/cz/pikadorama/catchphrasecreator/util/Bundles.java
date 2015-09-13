package cz.pikadorama.catchphrasecreator.util;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by Tomas on 13.9.2015.
 */
public class Bundles {

    private Bundles() {
    }

    public static Bundle of(Const.BundleParam key, Serializable value) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(key.name(), value);
        return bundle;
    }

    public static Bundle of(Const.BundleParam key1, Serializable value1, Const.BundleParam key2, Serializable value2) {
        Bundle bundle = of(key1, value1);
        bundle.putSerializable(key2.name(), value2);
        return bundle;
    }

    public static <T> T getValueFromIntent(Intent intent, Const.BundleParam key) {
        return (T) intent.getSerializableExtra(key.name());
    }
}
