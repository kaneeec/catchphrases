package cz.pikadorama.catchphrasecreator.sharing;

/**
 * Created by Tomas on 14.9.2015.
 */
public class Sound {

    private String text;
    private String fileName;

    public Sound(String text, String fileName) {

        this.text = text;
        this.fileName = fileName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
