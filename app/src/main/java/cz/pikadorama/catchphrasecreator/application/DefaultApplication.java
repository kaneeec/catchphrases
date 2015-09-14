package cz.pikadorama.catchphrasecreator.application;

import android.app.Application;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import cz.pikadorama.catchphrasecreator.collection.Config;
import cz.pikadorama.catchphrasecreator.collection.ConfigManager;
import cz.pikadorama.catchphrasecreator.database.SQLiteOpenHelperImpl;
import cz.pikadorama.catchphrasecreator.pojo.CatchPhrase;
import cz.pikadorama.catchphrasecreator.pojo.Collection;
import cz.pikadorama.catchphrasecreator.pojo.State;
import cz.pikadorama.catchphrasecreator.Const;
import cz.pikadorama.framework.database.DbHelperStore;
import cz.pikadorama.framework.database.dao.Dao;
import cz.pikadorama.framework.database.dao.DaoManager;
import cz.pikadorama.framework.util.Archives;

/**
 * Created by Tomas on 9.8.2015.
 */
public class DefaultApplication extends Application {

    public static final Class<?>[] DAO_TYPES = {CatchPhrase.class, Collection.class};

    @Override
    public void onCreate() {
        super.onCreate();

        // register helper
        DbHelperStore.registerHelper(new SQLiteOpenHelperImpl(this), DAO_TYPES);

        Dao<CatchPhrase> catchPhraseDao = DaoManager.getDao(CatchPhrase.class);
        Dao<Collection> collectionDao = DaoManager.getDao(Collection.class);

        collectionDao.deleteAll();
        catchPhraseDao.deleteAll();

        File fileToPlay = new File(Environment.getExternalStorageDirectory(), "sound.mp3");
        byte[] buffer = new byte[0];
        try {
            buffer = Files.toByteArray(fileToPlay);
        } catch (IOException e) {
            Log.e(Const.TAG, e.getMessage(), e);
        }

        File zipFile = new File(Environment.getExternalStorageDirectory(), "lakatoš.zip");
        File tempDir = new File(getCacheDir(), UUID.randomUUID().toString());

        Archives.unzip(zipFile, tempDir);

        Optional<File> match = Files.fileTreeTraverser().breadthFirstTraversal(tempDir).firstMatch(new Predicate<File>() {
            @Override
            public boolean apply(File input) {
                return input.getName().equals("metadata.json");
            }
        });
        if (match.isPresent()) {
            Config config = ConfigManager.load(match.get().getAbsolutePath());
            Toast.makeText(this, match.get().getName() + " is present. Path: " + match.get().getPath(), Toast
                    .LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nothing...", Toast.LENGTH_SHORT).show();
        }

        catchPhraseDao.create(new CatchPhrase("Ahoj !", buffer));
        catchPhraseDao.create(new CatchPhrase("Cau !", buffer));
        catchPhraseDao.create(new CatchPhrase("Nazdar !", buffer));
        catchPhraseDao.create(new CatchPhrase("Bazar !", buffer));
        catchPhraseDao.create(new CatchPhrase("Zdar !", buffer));

        List<CatchPhrase> catchPhraseList = catchPhraseDao.findAll();

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
