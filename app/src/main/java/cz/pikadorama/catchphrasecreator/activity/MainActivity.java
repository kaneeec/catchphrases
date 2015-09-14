package cz.pikadorama.catchphrasecreator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import cz.pikadorama.catchphrasecreator.R;
import cz.pikadorama.catchphrasecreator.adapter.CollectionsAdapter;
import cz.pikadorama.catchphrasecreator.pojo.Collection;
import cz.pikadorama.catchphrasecreator.Const;
import cz.pikadorama.framework.util.ActivityParams;
import cz.pikadorama.framework.database.dao.Dao;
import cz.pikadorama.framework.database.dao.DaoManager;

/**
 * Created by Tomas on 11.8.2015.
 */
public class MainActivity extends BaseActivity {

    private CollectionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initListView();
        initDrawer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DrawerLayout drawer = findView(R.id.drawer);
                drawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initDrawer() {
        final DrawerLayout drawer = findView(R.id.drawer);
        final NavigationView view = findView(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.your_collections:
                        // no action here
                        break;
                    case R.id.manage_collections:
                        // TODO
                        break;
                    case R.id.settings:
                        // TODO
                        break;
                    case R.id.about:
                        // TODO
                        break;
                }
                item.setChecked(true);
                drawer.closeDrawers();
                return true;
            }
        });
    }

    private void initListView() {
        Dao<Collection> dao = DaoManager.getDao(Collection.class);
        List<Collection> collections = dao.findAll();
        adapter = new CollectionsAdapter(this, collections);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Collection collection = adapter.getItem(position);
                ActivityParams.store(Const.BundleParam.COLLECTION, collection);
                startActivity(new Intent(MainActivity.this, CatchphrasesActivity.class));
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = requireView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerLayout drawer = findView(R.id.drawer);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

}
