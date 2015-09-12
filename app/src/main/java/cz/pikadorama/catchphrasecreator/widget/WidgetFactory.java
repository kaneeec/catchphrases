package cz.pikadorama.catchphrasecreator.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Intent intent;

    public WidgetFactory(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;

        int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
// FIXME: 12.9.2015
//        if (widgetId != -1) {
//            SharedPreferences preferences = context.getSharedPreferences(Widget.getKey(widgetId), Context.MODE_PRIVATE);
//            String restoredText = preferences.getString(Widget.SELECTED_ITEMS_PARAM, null);
//
//            if (restoredText != null && !restoredText.isEmpty()) {
//                String[] ids = restoredText.split(",");
//
//                for (String id : ids) {
//                    sounds.add(Sound.values()[Integer.parseInt(id)]);
//                }
//            } else {
//                sounds.addAll(Arrays.asList(Sound.values()));
//            }
//        } else {
//            sounds.addAll(Arrays.asList(Sound.values()));
//        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    } // FIXME: 12.9.2015

    @Override
    public RemoteViews getViewAt(int position) {
        // FIXME: 12.9.2015
//        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.grid_view_item);
//        remoteView.setTextViewText(R.id.grid_item_label, sounds.get(position).getMessage());
//
//        // set onclick listener
//        Bundle extras = new Bundle();
//        extras.putInt(Widget.ACTION_ITEM_PARAM, sounds.get(position).ordinal());
//        Intent listenerIntent = new Intent();
//        listenerIntent.putExtras(extras);
//        remoteView.setOnClickFillInIntent(R.id.grid_item_label, listenerIntent);
//
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
