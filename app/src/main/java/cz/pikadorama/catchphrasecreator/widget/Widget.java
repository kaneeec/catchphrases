package cz.pikadorama.catchphrasecreator.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import cz.pikadorama.catchphrasecreator.R;


public class Widget extends AppWidgetProvider {

    public static final String ACTION_ITEM_CLICK = "Widget.Action.Item.Click";
    public static final String ACTION_ITEM_PARAM = "Widget.Action.Item.Param";

    public static final String SELECTED_ITEMS_PARAM = "Widget.Selected.Items.Param";

    public static final String PREFERENCES = "Widget.Selected.Items.Settings";

    public static String getKey(int widgetId) {
        return Widget.PREFERENCES + String.valueOf(widgetId);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ACTION_ITEM_CLICK)) {
            int position = intent.getIntExtra(ACTION_ITEM_PARAM, -1);
//            if (position >= 0) {
//                MediaPlayer mp = MediaPlayer.create(context, Sound.values()[position].getSoundId());
//                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        mp.release();
//                    }
//                });
//                mp.start();
//            } else {
//                Toast.makeText(context, R.string.error_no_sound_selected , Toast.LENGTH_SHORT).show();
//            }
        }
        super.onReceive(context, intent);
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {
        // set service for the remote view
        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        // set unique uri
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        // set adapter
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setRemoteAdapter(R.id.gridview, intent);

        // set onclick listener
        Intent listenerIntent = new Intent(context, Widget.class);
        listenerIntent.setAction(ACTION_ITEM_CLICK);
        listenerIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, listenerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.gridview, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}


