package cz.pikadorama.catchphrasecreator.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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
import cz.pikadorama.catchphrasecreator.pojo.Collection;
import cz.pikadorama.catchphrasecreator.pojo.State;
import cz.pikadorama.catchphrasecreator.sharing.ConfigManager;
import cz.pikadorama.framework.database.DaoManager;
import cz.pikadorama.framework.database.dao.Dao;
import cz.pikadorama.framework.event.EventManager;
import cz.pikadorama.framework.event.EventProcessor;
import cz.pikadorama.framework.event.EventType;
import cz.pikadorama.framework.util.ActivityParams;
import cz.pikadorama.framework.util.FilePicker;

/**
 * Created by Tomas on 11.8.2015.
 */
public class CollectionActivity extends BaseActivity implements EventProcessor {

    private DrawerSelectedItemHistory drawerItem = new DrawerSelectedItemHistory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        initToolbar();
        initDrawer();
        EventManager.registerEventProcessor(this, EventType.DATABASE_UPDATED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventManager.unregisterEventProcessor(this, EventType.DATABASE_UPDATED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collections, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DrawerLayout drawer = findView(R.id.drawer);
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_add_collection:
                ActivityParams.store(Const.BundleParam.MODE_EDIT, false);
                startActivity(new Intent(this, CollectionManagementActivity.class));
                break;
            case R.id.menu_load_collection:
                startActivityForResult(FilePicker.getIntent(this), FilePicker.FILE_SELECT_CODE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case FilePicker.FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    loadCollectionFromZip(intent);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void initDrawer() {
        DrawerLayout drawerLayout = findView(R.id.drawer);
        drawerLayout.setDrawerListener(new DrawerListener(this, drawerItem));

        NavigationView view = findView(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationViewListener(drawerItem, drawerLayout));
    }

    private void initListView() {
        Dao<Collection> dao = DaoManager.getDao(Collection.class);
        List<Collection> collections = dao.findAll();

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(new CollectionAdapter(this, collections));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Collection collection = (Collection) parent.getItemAtPosition(position);
                ActivityParams.store(Const.BundleParam.COLLECTION, collection);
                startActivity(new Intent(CollectionActivity.this, CatchphrasesActivity.class));
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = requireView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerLayout drawer = requireView(R.id.drawer);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void loadCollectionFromZip(Intent intent) {
        String filePath = FilePicker.resolvePath(CollectionActivity.this, intent.getData());
        try {
            Collection collection = ConfigManager.loadZip(new File(filePath), State.IMPORTED, CollectionActivity.this);
            EventManager.notifyEventProcessors(EventType.DATABASE_UPDATED);
            Toast.makeText(this, "Kolekce '" + collection.getName() + "' byla přidána.", Toast.LENGTH_SHORT).show();
        } catch (ZipException | IOException e) {
            Log.e(Const.TAG, e.getMessage(), e);
            Toast.makeText(this, "Kolekci se nepodařilo načíst. Zkontrolujte, že máte správný archiv.", Toast
                    .LENGTH_LONG).show();
        }
    }

    @Override
    public void processEvent(EventType eventType) {
        if (eventType == EventType.DATABASE_UPDATED) {
            initListView();
        }
    }

    private static final class DrawerSelectedItemHistory {
        private int previousId = -1;
        private int currentId = -1;

        public void setCurrentId(int currentId) {
            this.previousId = this.currentId;
            this.currentId = currentId;
        }

        public int getCurrentId() {
            return currentId;
        }

        public boolean hasIdChanged() {
            return previousId != currentId;
        }
    }

    private static final class NavigationViewListener implements NavigationView.OnNavigationItemSelectedListener {

        private final DrawerSelectedItemHistory drawerItemHistory;
        private final DrawerLayout drawerLayout;

        public NavigationViewListener(DrawerSelectedItemHistory drawerItemHistory, DrawerLayout drawerLayout) {
            this.drawerItemHistory = drawerItemHistory;
            this.drawerLayout = drawerLayout;
        }

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            drawerItemHistory.setCurrentId(item.getItemId());
            drawerLayout.closeDrawers();
            return true;
        }
    }

    private static final class DrawerListener extends DrawerLayout.SimpleDrawerListener {

        private final Context context;
        private final DrawerSelectedItemHistory drawerItemHistory;

        public DrawerListener(Context context, DrawerSelectedItemHistory drawerItemHistory) {
            this.context = context;
            this.drawerItemHistory = drawerItemHistory;
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            if (drawerItemHistory.hasIdChanged()) {
                switch (drawerItemHistory.getCurrentId()) {
                    case R.id.settings:
                    case R.id.about:
                        Toast.makeText(context, "Zatím nic...", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }
}
