package cz.pikadorama.catchphrasecreator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cz.pikadorama.catchphrasecreator.R;
import cz.pikadorama.catchphrasecreator.pojo.Collection;
import cz.pikadorama.framework.util.Views;

/**
 * Created by Tomas on 5.9.2015.
 */
public class CollectionAdapter extends ArrayAdapter<Collection> {

    public CollectionAdapter(Context context, List<Collection> collections) {
        super(context, 0, collections);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.item_collection, parent, false);
        Collection collection = getItem(position);

        TextView name = Views.require(root, R.id.input_collection_name);
        name.setText(collection.getName());
//
//        IconView icon = Views.require(root, R.id.icon);
//        icon.setText(collection.getName());

        return root;
    }
}
