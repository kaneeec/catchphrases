package cz.pikadorama.catchphrasecreator.pojo;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

import cz.pikadorama.framework.database.DbDataType;
import cz.pikadorama.framework.database.annotation.DbColumn;
import cz.pikadorama.framework.database.annotation.DbTable;
import cz.pikadorama.framework.database.dao.DaoManager;
import cz.pikadorama.framework.database.dao.DaoQueryHelper;
import cz.pikadorama.framework.util.Strings;

/**
 * Created by Tomas on 8.8.2015.
 */
@DbTable(name = "collections", mappingClass = Collection.CollectionHelper.class)
public class Collection implements BaseColumns, Serializable {

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_PHRASE_IDS = "phrase_ids";

    @DbColumn(name = _ID, type = DbDataType.INTEGER, properties = "primary key autoincrement")
    private Integer id;

    @DbColumn(name = COLUMN_NAME, type = DbDataType.TEXT)
    private String name;

    @DbColumn(name = COLUMN_RATING, type = DbDataType.REAL)
    private double rating;

    @DbColumn(name = COLUMN_COLOR, type = DbDataType.INTEGER)
    private int color;

    @DbColumn(name = COLUMN_STATE, type = DbDataType.TEXT)
    private State state;

    @DbColumn(name = COLUMN_PHRASE_IDS, type = DbDataType.TEXT)
    private List<CatchPhrase> catchPhrases;

    public Collection(int id, String name, double rating, int color, State state, List<CatchPhrase> catchPhrases) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.color = color;
        this.state = state;
        this.catchPhrases = catchPhrases;
    }

    public Collection(String name, double rating, int color, State state, List<CatchPhrase> catchPhrases) {
        this.name = name;
        this.rating = rating;
        this.color = color;
        this.state = state;
        this.catchPhrases = catchPhrases;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<CatchPhrase> getCatchPhrases() {
        return catchPhrases;
    }

    public void setCatchPhrases(List<CatchPhrase> catchPhrases) {
        this.catchPhrases = catchPhrases;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", color=" + color +
                ", state=" + state +
                ", catchPhrases=" + catchPhrases +
                '}';
    }

    public static final class CollectionHelper implements DaoQueryHelper<Collection> {

        @Override
        public Collection cursorToObject(Cursor cursor) {
            Integer id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            double rating = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_RATING));
            State state = State.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATE)));
            int color = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COLOR));

            String stringOfIds = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHRASE_IDS));
            List<String> ids = Strings.split(stringOfIds);
            List<CatchPhrase> phrases = DaoManager.getDao(CatchPhrase.class).getByIds(Lists.transform(ids, new Function<String, Integer>() {
                @Override
                public Integer apply(String input) {
                    return Integer.valueOf(input);
                }
            }));

            return new Collection(id, name, rating, color, state, phrases);
        }

        @Override
        public ContentValues objectToContentValues(Collection obj) {
            ContentValues cv = new ContentValues();
            cv.put(_ID, obj.getId());
            cv.put(COLUMN_NAME, obj.getName());
            cv.put(COLUMN_RATING, obj.getRating());
            cv.put(COLUMN_STATE, obj.getState().name());
            cv.put(COLUMN_COLOR, obj.getColor());

            String ids = Strings.join(Lists.transform(obj.getCatchPhrases(), new Function<CatchPhrase, String>() {
                @Override
                public String apply(CatchPhrase input) {
                    return String.valueOf(input.getId());
                }
            }), ",");
            cv.put(COLUMN_PHRASE_IDS, ids);

            return cv;
        }

        @Override
        public Integer getId(Collection obj) {
            return obj.getId();
        }
    }
}
