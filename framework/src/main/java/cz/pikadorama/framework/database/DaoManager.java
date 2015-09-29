package cz.pikadorama.framework.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.pikadorama.framework.database.annotation.DbTable;
import cz.pikadorama.framework.database.dao.Dao;
import cz.pikadorama.framework.database.dao.DaoQueryHelper;
import cz.pikadorama.framework.util.Strings;


/**
 * Created by Tomas on 9.8.2015.
 */
public class DaoManager {

    private static final Map<Class<?>, DaoQueryHelper<?>> daoQueryHelpers = new HashMap<>();
    private static final Map<Class<?>, Dao<?>> daos = new HashMap<>();

    /**
     * Register dao query helper implementation for the given DAO type.
     *
     * @param daoType        DAO type
     * @param daoQueryHelper DAO query helper implementation
     */
    public static void registerDaoQueryHelper(Class<?> daoType, DaoQueryHelper<?> daoQueryHelper) {
        daoQueryHelpers.put(daoType, daoQueryHelper);
    }

    /**
     * Register DAO for the given DAO type. Overrides any DAO of the same type registered before.
     *
     * @param daoType DAO type
     * @param dao     DAO implementation
     */
    public static void registerDao(Class<?> daoType, Dao<?> dao) {
        daos.put(daoType, dao);
    }

    /**
     * Get DAO implementation for the given DAO type.
     *
     * @param daoType DAO type
     * @return DAO implementation
     */
    public static <T> Dao<T> getDao(Class<T> daoType) {
        if (daos.containsKey(daoType)) {
            return (Dao<T>) daos.get(daoType);
        } else {
            Dao<T> dao = new DefaultDao<T>(daoType);
            registerDao(daoType, dao);
            return dao;
        }
    }

    private static <T> DaoQueryHelper<T> checkAndGetHelper(Class<?> daoType) {
        DaoQueryHelper<T> helper = (DaoQueryHelper<T>) daoQueryHelpers.get(daoType);
        if (helper == null) {
            throw new IllegalArgumentException("There is no Database Query Helper registered for class " + daoType);
        }
        return helper;
    }

    private static final class DefaultDao<T> implements Dao<T> {

        private final String tableName;
        private final String[] columnNames;
        private final DaoQueryHelper<T> helper;

        private DefaultDao(Class<T> daoType) {
            this.helper = checkAndGetHelper(daoType);
            this.tableName = daoType.getAnnotation(DbTable.class).name();

            List<String> columnNamesList = DbUtil.getColumnNames(tableName);
            this.columnNames = columnNamesList.toArray(new String[columnNamesList.size()]);
        }

        @Override
        public T getById(int id) {
            try (SQLiteDatabase db = DbHelperManager.getInstance().getReadableDatabase()) {
                try (Cursor cursor = db.query(tableName, columnNames, BaseColumns._ID + " = ?", new String[]{String.valueOf(id)}, null, null, null)) {
                    return helper.cursorToObject(cursor);
                }
            }
        }

        @Override
        public List<T> getByIds(List<Integer> ids) {
            if (ids == null || ids.isEmpty()) {
                return Collections.emptyList();
            }

            List<String> stringIds = Lists.transform(ids, new Function<Integer, String>() {
                @Override
                public String apply(Integer input) {
                    return String.valueOf(input);
                }
            });

            try (SQLiteDatabase db = DbHelperManager.getInstance().getReadableDatabase()) {
                try (Cursor cursor = db.query(tableName, columnNames, BaseColumns._ID + " IN " + Strings.makeSqlPlaceholders(stringIds.size()), stringIds.toArray(new String[stringIds.size()]), null, null, null)) {
                    List<T> list = new ArrayList<>();
                    while (cursor.moveToNext()) {
                        list.add(helper.cursorToObject(cursor));
                    }
                    return list;
                }
            }
        }

        @Override
        public long create(T obj) {
            if (obj != null) {
                try (SQLiteDatabase db = DbHelperManager.getInstance().getWritableDatabase()) {
                    ContentValues values = helper.objectToContentValues(obj);
                    long id = db.insert(tableName, null, values);
                    helper.setId(obj, (int) id);
                    return id;
                }
            }
            return -1;
        }

        @Override
        public void update(T obj) {
            if (obj != null) {
                try (SQLiteDatabase db = DbHelperManager.getInstance().getReadableDatabase()) {
                    ContentValues values = helper.objectToContentValues(obj);
                    db.update(tableName, values, BaseColumns._ID + " = ?", new String[]{String.valueOf(helper.getId(obj))});
                }
            }
        }

        @Override
        public void delete(T obj) {
            if (obj != null) {
                try (SQLiteDatabase db = DbHelperManager.getInstance().getWritableDatabase()) {
                    db.delete(tableName, BaseColumns._ID + " = ?", new String[]{String.valueOf(helper.getId(obj))});
                }
            }
        }

        @Override
        public void delete(int id) {
            try (SQLiteDatabase db = DbHelperManager.getInstance().getWritableDatabase()) {
                db.delete(tableName, BaseColumns._ID + " = ?", new String[]{String.valueOf(id)});
            }
        }

        @Override
        public void deleteAll() {
            try (SQLiteDatabase db = DbHelperManager.getInstance().getWritableDatabase()) {
                db.execSQL("delete from " + tableName);
            }
        }

        @Override
        public List<T> findAll() {
            try (SQLiteDatabase db = DbHelperManager.getInstance().getReadableDatabase()) {
                try (Cursor cursor = db.query(tableName, columnNames, null, null, null, null, null)) {
                    List<T> list = new ArrayList<>();
                    while (cursor.moveToNext()) {
                        list.add(helper.cursorToObject(cursor));
                    }
                    return list;
                }
            }
        }

        @Override
        public List<T> query(String query, String[] columnNames) {
            if (query == null || query.isEmpty() || columnNames == null | columnNames.length == 0) {
                return Collections.emptyList();
            }
            try (SQLiteDatabase db = DbHelperManager.getInstance().getReadableDatabase()) {
                try (Cursor cursor = db.rawQuery(query, columnNames)) {
                    List<T> list = new ArrayList<>();
                    while (cursor.moveToNext()) {
                        list.add(helper.cursorToObject(cursor));
                    }
                    return list;
                }
            }
        }
    }

}
