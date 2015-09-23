package cz.pikadorama.framework.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import cz.pikadorama.framework.Const;
import cz.pikadorama.framework.database.annotation.DbTable;
import cz.pikadorama.framework.util.Closeables;

/**
 * Created by Tomas on 5.9.2015.
 */
public final class DbHelperManager {

    private static SQLiteOpenHelper helper = null;

    /**
     * Register your {@link SQLiteOpenHelper} implementation. After the helper is registered, whole database
     * structure is created and initialized. You can than use {@link DaoManager}
     * to get DAO implementations for desired classes.
     *
     * @param helperToRegister {@link SQLiteOpenHelper} implementation
     * @param entityClasses array of entity classes that you want to be handled automatically by the framework
     *                      (they must be annotated with the {@link DbTable} annotation}
     * @see DaoManager
     */
    public synchronized static final void registerHelper(SQLiteOpenHelper helperToRegister, Class<?> ... entityClasses) {
        if (helper != null) {
            Log.w(Const.TAG, "Another SQLiteOpenHelper is already registered. Skipping this one.");
        } else {
            helper = helperToRegister;
            DbUtil.initDbUtil(entityClasses);

            // create tables when first run
            SQLiteDatabase db = DbHelperManager.getInstance().getReadableDatabase();
            Closeables.closeQuietly(db);
        }
    }

    /**
     * Returns instance of database helper.
     *
     * @return database helper instance
     * @throws IllegalStateException in case there is no {@link SQLiteOpenHelper} implementation
     */
    public synchronized static final SQLiteOpenHelper getInstance() {
        if (helper == null) {
            throw new IllegalStateException("There is no SQLiteOpenHelper implementation registered.");
        }
        return helper;

    }

}
