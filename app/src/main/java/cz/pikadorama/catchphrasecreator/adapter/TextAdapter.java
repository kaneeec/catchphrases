package cz.pikadorama.catchphrasecreator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cz.pikadorama.catchphrasecreator.R;
import cz.pikadorama.framework.util.Views;

public class TextAdapter extends ArrayAdapter<String> {

    public TextAdapter(Context context, List<String> messages) {
        super(context, 0, messages);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view_item, null);

        TextView textView = Views.require(gridView, R.id.grid_item_label);
        textView.setText(getItem(position));

        return gridView;
    }

}
