package cz.pikadorama.catchphrasecreator.application;

import android.app.Application;

import cz.pikadorama.catchphrasecreator.database.SQLiteOpenHelperImpl;
import cz.pikadorama.catchphrasecreator.pojo.CatchPhrase;
import cz.pikadorama.catchphrasecreator.pojo.Group;
import cz.pikadorama.framework.database.DbHelperStore;

/**
 * Created by Tomas on 9.8.2015.
 */
public class DefaultApplication extends Application {

    public static final Class<?>[] DAO_TYPES = {CatchPhrase.class, Group.class};

    @Override
    public void onCreate() {
        super.onCreate();

        // register helper
        DbHelperStore.registerHelper(new SQLiteOpenHelperImpl(getApplicationContext()), DAO_TYPES);
    }
}
