package cz.pikadorama.catchphrasecreator.application;

import android.app.Application;

import cz.pikadorama.catchphrasecreator.database.SQLiteOpenHelperImpl;
import cz.pikadorama.catchphrasecreator.pojo.CatchPhrase;
import cz.pikadorama.catchphrasecreator.pojo.Collection;
import cz.pikadorama.framework.database.DaoManager;
import cz.pikadorama.framework.database.DbHelperManager;
import cz.pikadorama.framework.database.dao.Dao;

/**
 * Created by Tomas on 9.8.2015.
 */
public class DefaultApplication extends Application {

    public static final Class<?>[] DAO_TYPES = {CatchPhrase.class, Collection.class};

    @Override
    public void onCreate() {
        super.onCreate();

        // register helper
        DbHelperManager.registerHelper(new SQLiteOpenHelperImpl(this), DAO_TYPES);

        Dao<CatchPhrase> catchPhraseDao = DaoManager.getDao(CatchPhrase.class);
        Dao<Collection> collectionDao = DaoManager.getDao(Collection.class);

        collectionDao.deleteAll();
        catchPhraseDao.deleteAll();
    }
}
