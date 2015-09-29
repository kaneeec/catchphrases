package cz.pikadorama.catchphrasecreator.activity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import cz.pikadorama.catchphrasecreator.Const;
import cz.pikadorama.catchphrasecreator.R;
import cz.pikadorama.catchphrasecreator.adapter.CatchPhraseAdapter;
import cz.pikadorama.catchphrasecreator.pojo.CatchPhrase;
import cz.pikadorama.catchphrasecreator.pojo.Collection;
import cz.pikadorama.catchphrasecreator.util.SoundPlayer;
import cz.pikadorama.framework.util.ActivityParams;

public class CatchphrasesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catchphrases);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        Collection collection = ActivityParams.load(Const.BundleParam.COLLECTION);

        initToolbar(collection);
        initStatusBar(collection);
        initGridView(collection);
    }

    private void initGridView(Collection collection) {
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new CatchPhraseAdapter(this, collection.getCatchPhrases()));
        gridview.setOnItemClickListener(new GridViewItemListener(this));
    }

    private void initStatusBar(Collection collection) {
        Window window = getWindow();
        window.setStatusBarColor(collection.getColor());
    }

    private void initToolbar(Collection collection) {
        Toolbar toolbar = requireView(R.id.toolbar);
        toolbar.setBackgroundColor(collection.getColor());
        toolbar.setTitle(collection.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static final class GridViewItemListener implements AdapterView.OnItemClickListener {

        private final Context context;

        public GridViewItemListener(Context context) {
            this.context = context;
        }

        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            CatchPhrase catchPhrase = (CatchPhrase) parent.getItemAtPosition(position);
            SoundPlayer.getInstance(context).play(catchPhrase.getSoundData());
        }
    }
}
