package cz.pikadorama.catchphrasecreator.application;

import android.app.Application;
import android.graphics.Color;

import java.util.List;

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

        Dao<CatchPhrase> catchPhraseDao = DaoManager.getDao(CatchPhrase.class);
        catchPhraseDao.create(new CatchPhrase("Ahoj !", new byte[] {0x00}));
        catchPhraseDao.create(new CatchPhrase("Cau !", new byte[] {0x00}));
        catchPhraseDao.create(new CatchPhrase("Nazdar !", new byte[] {0x00}));
        catchPhraseDao.create(new CatchPhrase("Bazar !", new byte[] {0x00}));
        catchPhraseDao.create(new CatchPhrase("Zdar !", new byte[] {0x00}));

        List<CatchPhrase> catchPhraseList = catchPhraseDao.findAll();

        Dao<Collection> collectionDao = DaoManager.getDao(Collection.class);
        collectionDao.create(new Collection("Collection 1", 4.5, Color.CYAN, State.PERSONAL_PRIVATE, catchPhraseList));
        collectionDao.create(new Collection("Collection 2", 4.0, Color.RED, State.COMMUNITY, catchPhraseList));
        collectionDao.create(new Collection("Collection 3", 4.9, Color.GREEN, State.PERSONAL_SHARED, catchPhraseList));
        collectionDao.create(new Collection("Collection 4", 1.1, Color.BLUE, State.PERSONAL_PRIVATE, catchPhraseList));
        collectionDao.create(new Collection("Collection 5", 5.0, Color.GRAY, State.PERSONAL_SHARED, catchPhraseList));
        collectionDao.create(new Collection("Collection 6", 3.7, Color.YELLOW, State.COMMUNITY, catchPhraseList));
        collectionDao.create(new Collection("Collection 7", 2.2, Color.LTGRAY, State.PERSONAL_SHARED, catchPhraseList));
        collectionDao.create(new Collection("Collection 8", 3.9, Color.MAGENTA, State.IMPORTED, catchPhraseList));
    }
}
