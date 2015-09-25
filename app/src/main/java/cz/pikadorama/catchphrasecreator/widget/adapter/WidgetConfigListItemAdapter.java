package cz.pikadorama.catchphrasecreator.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cz.pikadorama.catchphrasecreator.R;

public class WidgetConfigListItemAdapter extends BaseAdapter {

    private Context context;
    private List<String> messages;

    public WidgetConfigListItemAdapter(Context context, List<String> messages) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.item_catch_phrase_widget, parent, false);
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(messages.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public String getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
 
