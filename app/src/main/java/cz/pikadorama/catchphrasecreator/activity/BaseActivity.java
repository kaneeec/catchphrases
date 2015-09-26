package cz.pikadorama.catchphrasecreator.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import cz.pikadorama.catchphrasecreator.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // set status bar color
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.primary));
    }
}
