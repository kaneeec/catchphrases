package cz.pikadorama.catchphrasecreator.application;

import android.app.Application;
import android.graphics.Color;

import cz.pikadorama.catchphrasecreator.database.SQLiteOpenHelperImpl;
import cz.pikadorama.catchphrasecreator.pojo.CatchPhrase;
import cz.pikadorama.catchphrasecreator.pojo.Collection;
import cz.pikadorama.catchphrasecreator.pojo.State;
import cz.pikadorama.framework.database.DbHelperStore;
import cz.pikadorama.framework.database.dao.Dao;
import cz.pikadorama.framework.database.dao.DaoManager;

/**
 * Created by Tomas on 9.8.2015.
 */
public class DefaultApplication extends Application {

    public static final Class<?>[] DAO_TYPES = {CatchPhrase.class, Collection.class};

    @Override
    public void onCreate() {
        super.onCreate();

        // register helper
        DbHelperStore.registerHelper(new SQLiteOpenHelperImpl(getApplicationContext()), DAO_TYPES);

        Dao<Collection> dao = DaoManager.getDao(Collection.class);
        dao.create(new Collection("Collection 1", 4.5, Color.CYAN, State.PERSONAL_PRIVATE, null));
        dao.create(new Collection("Collection 2", 4.0, Color.RED, State.COMMUNITY, null));
        dao.create(new Collection("Collection 3", 4.9, Color.GREEN, State.PERSONAL_SHARED, null));
        dao.create(new Collection("Collection 4", 1.1, Color.BLUE, State.PERSONAL_PRIVATE, null));
        dao.create(new Collection("Collection 5", 5.0, Color.GRAY, State.PERSONAL_SHARED, null));
        dao.create(new Collection("Collection 6", 3.7, Color.YELLOW, State.COMMUNITY, null));
        dao.create(new Collection("Collection 7", 2.2, Color.LTGRAY, State.PERSONAL_SHARED, null));
        dao.create(new Collection("Collection 8", 3.9, Color.MAGENTA, State.IMPORTED, null));
    }
}
