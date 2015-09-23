package cz.pikadorama.catchphrasecreator.activity;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

import cz.pikadorama.catchphrasecreator.R;
import cz.pikadorama.catchphrasecreator.adapter.CatchPhraseAdapter;
import cz.pikadorama.catchphrasecreator.pojo.CatchPhrase;
import cz.pikadorama.catchphrasecreator.pojo.Collection;
import cz.pikadorama.catchphrasecreator.Const;
import cz.pikadorama.catchphrasecreator.util.SoundPlayer;
import cz.pikadorama.framework.util.ActivityParams;

public class CatchphrasesActivity extends BaseActivity {

    private List<CatchPhrase> catchPhraseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catchphrases);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        Toolbar toolbar = requireView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Collection collection = ActivityParams.load(Const.BundleParam.COLLECTION);
        catchPhraseList = collection.getCatchPhrases();
        List<String> catchPhrases = Lists.transform(catchPhraseList, new Function<CatchPhrase, String>() {
            @Override
            public String apply(CatchPhrase input) {
                return input.getText();
            }
        });

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new CatchPhraseAdapter(this, catchPhrases));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                CatchPhrase catchPhrase = catchPhraseList.get(position);
                SoundPlayer.getInstance(CatchphrasesActivity.this).play(catchPhrase.getSoundData());
                Toast.makeText(CatchphrasesActivity.this, catchPhraseList.get(position).getText(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
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
}
