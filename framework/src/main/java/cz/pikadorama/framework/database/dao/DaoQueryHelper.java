package cz.pikadorama.framework.database.dao;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Tomas on 9.8.2015.
 */
public interface DaoQueryHelper<T> {

    /**
     * Transform cursor data to object of type T.
     *
     * @param cursor cursor
     * @return object with data from the cursor
     */
    T cursorToObject(Cursor cursor);

    /**
     * Create content values from the given object for further use in database access.
     *
     * @param obj object
     * @return content values from the object
     */
    ContentValues objectToContentValues(T obj);

    /**
     * Return object's ID. If the object is null or the ID is not set, return null.
     *
     * @param obj object
     * @return ID or null
     */
    Integer getId(T obj);

}
