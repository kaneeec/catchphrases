package cz.pikadorama.catchphrasecreator.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cz.pikadorama.catchphrasecreator.Const;
import cz.pikadorama.catchphrasecreator.R;
import cz.pikadorama.catchphrasecreator.adapter.CollectionAdapter;
import cz.pikadorama.catchphrasecreator.config.ConfigManager;
import cz.pikadorama.catchphrasecreator.pojo.Collection;
import cz.pikadorama.catchphrasecreator.pojo.State;
import cz.pikadorama.framework.database.DaoManager;
import cz.pikadorama.framework.database.dao.Dao;
import cz.pikadorama.framework.util.ActivityParams;
import cz.pikadorama.framework.util.FilePicker;

/**
 * Created by Tomas on 11.8.2015.
 */
public class MainActivity extends BaseActivity {

    private CollectionAdapter adapter;
    private int drawerSelectedItemId = R.id.your_collections;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case FilePicker.FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    String filePath = FilePicker.resolvePath(MainActivity.this, intent.getData());
                    try {
                        Toast.makeText(MainActivity.this, "Loading data ...", Toast.LENGTH_SHORT).show();
                        ConfigManager.loadZip(new File(filePath), State.IMPORTED, MainActivity.this);
                        initListView();
                    } catch (ZipException | IOException e) {
                        Log.e(Const.TAG, e.getMessage(), e);
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void initDrawer() {
        final DrawerLayout drawer = findView(R.id.drawer);
        final NavigationView view = findView(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerSelectedItemId = item.getItemId();
                item.setChecked(true);
                drawer.closeDrawers();
                return true;
            }
        });
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                switch (drawerSelectedItemId) {
                    case R.id.your_collections:
                        // no action here
                        // TODO
                        break;
                    case R.id.manage_collections:
                        Intent intent = FilePicker.getIntent(MainActivity.this);
                        startActivityForResult(intent, FilePicker.FILE_SELECT_CODE);
                        // TODO
                        break;
                    case R.id.settings:
                        startActivity(new Intent(MainActivity.this, ManageCollectionActivity.class));
                        // TODO
                        break;
                    case R.id.about:
                        // TODO
                        break;
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    private void initListView() {
        Dao<Collection> dao = DaoManager.getDao(Collection.class);
        List<Collection> collections = dao.findAll();
        adapter = new CollectionAdapter(this, collections);

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
