package cz.pikadorama.catchphrasecreator.collection;

import java.util.List;

/**
 * Created by Tomas on 14.9.2015.
 */
public class Config {

    private String id;
    private String collectionName;
    private String collectionColor;
    private String author;
    private String authorEmail;
    private List<Sound> sounds;

    public Config(String id, String collectionName, String collectionColor, String author, String authorEmail, List<Sound> sounds) {
        this.id = id;
        this.collectionName = collectionName;
        this.collectionColor = collectionColor;
        this.author = author;
        this.authorEmail = authorEmail;
        this.sounds = sounds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionColor() {
        return collectionColor;
    }

    public void setCollectionColor(String collectionColor) {
        this.collectionColor = collectionColor;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public List<Sound> getSounds() {
        return sounds;
    }

    public void setSounds(List<Sound> sounds) {
        this.sounds = sounds;
    }
}
