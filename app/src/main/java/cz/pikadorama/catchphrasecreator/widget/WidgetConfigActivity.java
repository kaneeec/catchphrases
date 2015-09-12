package cz.pikadorama.catchphrasecreator.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import cz.pikadorama.catchphrasecreator.R;
import cz.pikadorama.catchphrasecreator.widget.adapter.WidgetConfigListItemAdapter;

public class WidgetConfigActivity extends Activity {

    private WidgetConfigListItemAdapter adapter;

    int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_widget);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        adapter = new WidgetConfigListItemAdapter(this, null); // FIXME: 12.9.2015
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selector, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:
                selectionDone();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectionDone() {
        String selectedItems = getSelectedItems();

        if (selectedItems.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.error_no_sound_selected, Toast.LENGTH_SHORT).show();
            return;
        }

        // save preferences
        SharedPreferences preferences = getSharedPreferences(Widget.getKey(widgetId), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Widget.SELECTED_ITEMS_PARAM, selectedItems);
        editor.commit();

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, resultValue);

        finish();
    }

    private String getSelectedItems() {
        String persons = "";
        ListView listView = (ListView) findViewById(android.R.id.list);

        for (int i = 0; i < listView.getCount(); i++) {
            if (listView.isItemChecked(i)) {
                if (!persons.isEmpty()) {
                    persons += ",";
                }
                persons += i;
            }
        }

        return persons;
    }
}
