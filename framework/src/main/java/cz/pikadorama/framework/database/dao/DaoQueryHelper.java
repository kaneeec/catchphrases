package cz.pikadorama.framework.database.dao;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Tomas on 9.8.2015.
 */
public interface DaoQueryHelper<T> {

    T cursorToObject(Cursor cursor);

    ContentValues objectToContentValues(T obj);

    Integer getId(T obj);

}
