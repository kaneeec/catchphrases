package cz.pikadorama.catchphrasecreator.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import cz.pikadorama.catchphrasecreator.R;
import cz.pikadorama.catchphrasecreator.pojo.CatchPhrase;
import cz.pikadorama.framework.database.dao.Dao;
import cz.pikadorama.framework.database.dao.DaoManager;

/**
 * Created by Tomas on 11.8.2015.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CatchPhrase phrase = new CatchPhrase("text", null);
        Dao<CatchPhrase> dao = DaoManager.getDao(CatchPhrase.class);
        dao.create(phrase);

        TextView view = (TextView) findViewById(R.id.text);
        view.setText(dao.findAll().get(0).getText());
    }

}
