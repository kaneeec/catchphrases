package cz.pikadorama.catchphrasecreator.pojo;

/**
 * Created by Tomas on 8.8.2015.
 */
public enum State {

    /**
     * Collection imported from community.
     */
    COMMUNITY,

    /**
     * Private collection. Created by the user.
     */
    PERSONAL_PRIVATE,

    /**
     * Also created by the user but shared to the community.
     */
    PERSONAL_SHARED,

    /**
     * Imported from a zip bundle.
     */
    IMPORTED,

    /**
     * Imported from a zip bundle and shared to the community.
     */
    IMPORTED_SHARED;

}
