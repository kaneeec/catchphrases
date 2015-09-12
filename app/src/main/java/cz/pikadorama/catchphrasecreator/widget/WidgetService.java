package cz.pikadorama.catchphrasecreator.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import cz.pikadorama.catchphrasecreator.widget.WidgetFactory;

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetFactory(getApplicationContext(), intent);
    }
}
