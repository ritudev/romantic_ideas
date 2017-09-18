package smile.com.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import smile.com.home.model.Data;
import smile.com.home.utils.FavouriteUtil;

public class FavActivity extends AppCompatActivity {

    private AdView mAdView;
    private AdBuilder mAdBuilder;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        mAdView = (AdView) findViewById(R.id.adView);
        mAdBuilder = new AdBuilder(mAdView);
        mAdBuilder.showBannerAds();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ((TextView)findViewById(R.id.topbarheader)).setText("My Favourites");
    }

    @Override
    protected void onStart() {
        if (FavouriteUtil.getInstance().getFavouriteList() == null || FavouriteUtil.getInstance().getFavouriteList().isEmpty()){
            findViewById(R.id.no_fav_TV).setVisibility(View.VISIBLE);
        }else {
            SolventRecyclerViewAdapter rcAdapter = new SolventRecyclerViewAdapter(this, FavouriteUtil.getInstance().getFavouriteList());
            recyclerView.setAdapter(rcAdapter);
        }
        super.onStart();
    }
}
