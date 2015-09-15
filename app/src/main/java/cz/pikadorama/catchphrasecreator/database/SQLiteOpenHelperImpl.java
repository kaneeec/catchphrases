package cz.pikadorama.catchphrasecreator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cz.pikadorama.catchphrasecreator.application.DefaultApplication;
import cz.pikadorama.framework.database.DbUtil;

/**
 * Created by Tomas on 8.8.2015.
 */
public class SQLiteOpenHelperImpl extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "catchphrases.db";

    public SQLiteOpenHelperImpl(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DbUtil.createTables(db, DefaultApplication.DAO_TYPES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
