package cz.pikadorama.catchphrasecreator.pojo;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.io.Serializable;
import java.util.Arrays;

import cz.pikadorama.framework.database.DbDataType;
import cz.pikadorama.framework.database.annotation.DbColumn;
import cz.pikadorama.framework.database.annotation.DbTable;
import cz.pikadorama.framework.database.dao.DaoQueryHelper;

/**
 * Created by Tomas on 8.8.2015.
 */
@DbTable(name = "catchphrases", mappingClass = CatchPhrase.CatchPhraseHelper.class)
public class CatchPhrase implements BaseColumns, Serializable {

    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_SOUND = "sounddata";

    @DbColumn(name = _ID, type = DbDataType.INTEGER, properties = "primary key autoincrement")
    private Integer id;

    @DbColumn(name = COLUMN_TEXT, type = DbDataType.TEXT)
    private String text;

    @DbColumn(name = COLUMN_SOUND, type = DbDataType.BLOB)
    private byte[] soundData;

    public CatchPhrase(int id, String text, byte[] soundData) {
        this.id = id;
        this.text = text;
        this.soundData = soundData;
    }

    public CatchPhrase(String text, byte[] soundData) {
        this.text = text;
        this.soundData = soundData;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getSoundData() {
        return soundData;
    }

    public void setSoundData(byte[] soundData) {
        this.soundData = soundData;
    }

    @Override
    public String toString() {
        return "CatchPhrase{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", soundData=" + Arrays.toString(soundData) +
                '}';
    }

    public static final class CatchPhraseHelper implements DaoQueryHelper<CatchPhrase> {

        @Override
        public CatchPhrase cursorToObject(Cursor cursor) {
            Integer id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            String text = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXT));
            byte[] soundData = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_SOUND));
            return new CatchPhrase(id, text, soundData);
        }

        @Override
        public ContentValues objectToContentValues(CatchPhrase obj) {
            ContentValues cv = new ContentValues();
            cv.put(_ID, obj.getId());
            cv.put(COLUMN_TEXT, obj.getText());
            cv.put(COLUMN_SOUND, obj.getSoundData());
            return cv;
        }

        @Override
        public Integer getId(CatchPhrase obj) {
            return obj.getId();
        }
    }
}
