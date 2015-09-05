package cz.pikadorama.catchphrasecreator.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import cz.pikadorama.catchphrasecreator.R;
import cz.pikadorama.catchphrasecreator.adapter.CollectionsAdapter;
import cz.pikadorama.catchphrasecreator.pojo.Collection;
import cz.pikadorama.framework.database.dao.Dao;
import cz.pikadorama.framework.database.dao.DaoManager;

/**
 * Created by Tomas on 11.8.2015.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = requireView(R.id.toolbar);
        setSupportActionBar(toolbar);

        Dao<Collection> dao = DaoManager.getDao(Collection.class);
        List<Collection> collections = dao.findAll();

        RecyclerView view = findView(R.id.collections);
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(new CollectionsAdapter(collections));
    }

}
