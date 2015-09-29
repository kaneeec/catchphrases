package cz.pikadorama.catchphrasecreator.sharing;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.io.Files;
import com.google.gson.Gson;

import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import cz.pikadorama.catchphrasecreator.Const;
import cz.pikadorama.catchphrasecreator.pojo.CatchPhrase;
import cz.pikadorama.catchphrasecreator.pojo.Collection;
import cz.pikadorama.catchphrasecreator.pojo.State;
import cz.pikadorama.framework.database.DaoManager;
import cz.pikadorama.framework.database.dao.Dao;
import cz.pikadorama.framework.util.Archives;

/**
 * Created by Tomas on 14.9.2015.
 */
public class ConfigManager {

    public static final String METADATA_FILE = "metadata.json";
    public static final double DEFAULT_RATING = 0.0;

    /**
     * Import Collection bundle from a zip file.
     *
     * @param zipFile zip file
     * @param state state of the collection
     * @param context context
     * @throws ZipException in case the file cannot be read
     * @throws IOException in case the file cannot be read
     */
    public static Collection loadZip(File zipFile, State state, Context context) throws ZipException, IOException {
        // TODO make background process
        final File tempDir = new File(context.getCacheDir(), UUID.randomUUID().toString());

        Archives.unzip(zipFile, tempDir);

        Optional<File> match = Files.fileTreeTraverser().breadthFirstTraversal(tempDir).firstMatch(new Predicate<File>() {
            @Override
            public boolean apply(File input) {
                return input.getName().equals(METADATA_FILE);
            }
        });

        if (match.isPresent()) {
            Config config = load(match.get());
            File metadataDir = match.get().getParentFile();

            Dao<CatchPhrase> catchPhraseDao = DaoManager.getDao(CatchPhrase.class);
            Dao<Collection> collectionDao = DaoManager.getDao(Collection.class);

            List<CatchPhrase> catchPhrases = new ArrayList<>();
            for (Sound sound : config.getSounds()) {
                File file = new File(metadataDir, sound.getFileName());
                try {
                    byte[] data = Files.toByteArray(file);
                    CatchPhrase newCatchPhrase = new CatchPhrase(sound.getText(), data);
                    long id = catchPhraseDao.create(newCatchPhrase);
                    newCatchPhrase.setId((int) id);
                    catchPhrases.add(newCatchPhrase);
                } catch (IOException e) {
                    throw new IOException("Catch phrase does not contain correct sound data, skipping. File: " + sound
                            .getFileName(), e);
                }
            }

            Collection newCollection = new Collection(config.getCollectionName(), DEFAULT_RATING, Color.parseColor(config.getCollectionColor()),
                    state, catchPhrases);
            collectionDao.create(newCollection);
            return newCollection;
        } else {
            throw new IOException("Incorrect ZIP file format. Please follow the documentation.");
        }
    }

    private static Config load(File file) throws IOException {
        if (!file.exists()) {
            throw new IllegalArgumentException("Specified file does not exist. Path: " + file.getAbsolutePath());
        }
        String json = Files.toString(file, Charset.defaultCharset());
        Gson gson = new Gson();
        return gson.fromJson(json, Config.class);
    }

    private static void store(Config config, File file) throws IOException {
        Objects.requireNonNull(config, "Config to store cannot be null.");
        if (!file.exists()) {
            throw new IllegalArgumentException("Specified file does not exist. Path: " + file.getAbsolutePath());
        }
        Gson gson = new Gson();
        String json = gson.toJson(config);
        Files.write(json, file, Charset.defaultCharset());
    }

}
