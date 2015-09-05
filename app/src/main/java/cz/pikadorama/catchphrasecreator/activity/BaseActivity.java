package cz.pikadorama.catchphrasecreator.activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Tomas on 5.9.2015.
 */
public class BaseActivity extends AppCompatActivity {

    public <T> T findView(int id) {
        return (T) findViewById(id);
    }

    public <T> T requireView(int id) {
        T view = (T) findViewById(id);
        if (view == null) {
            throw new IllegalArgumentException("View with id " + id + " not found.");
        }
        return view;
    }

}
