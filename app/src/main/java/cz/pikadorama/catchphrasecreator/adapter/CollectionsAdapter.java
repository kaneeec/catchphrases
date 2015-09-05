package cz.pikadorama.catchphrasecreator.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cz.pikadorama.catchphrasecreator.R;
import cz.pikadorama.catchphrasecreator.pojo.Collection;
import cz.pikadorama.framework.util.Views;

/**
 * Created by Tomas on 5.9.2015.
 */
public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.ViewHolder> {

    private final List<Collection> collections;

    public CollectionsAdapter(List<Collection> collections) {
        this.collections = collections;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_collection_card, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Collection collection = collections.get(position);
        viewHolder.setText(collection.getName());
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.textView = Views.require(view, R.id.collection_name);
        }

        public void setText(String text) {
            this.textView.setText(text);
        }
    }
}
