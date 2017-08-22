package smile.com.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import smile.com.home.model.Data;
import smile.com.home.utils.FavouriteUtil;

import static smile.com.home.Constants.DESCRIPTION;
import static smile.com.home.Constants.SELECTED_ITEM;
import static smile.com.home.Constants.SELECTED_POSITION;
import static smile.com.home.Constants.TITLE;

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private AdBuilder mAdBuilder;
    private AdView mAdView;
    Description description;
    String title;
    private ImageView favIcon;
    itemData itemData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        description = getIntent().getParcelableExtra(DESCRIPTION);
        title = getIntent().getStringExtra(TITLE);
        itemData = getIntent().getParcelableExtra(SELECTED_ITEM);
        ApplicationVariable.updateNoOfTimeDescriptionActivityOpenedByOne();
        TextView titleTV = (TextView) findViewById(R.id.title);
        if (description == null) {
            description = new Description();
            description.setDescription(title);
            titleTV.setVisibility(View.GONE);
        } else {
            titleTV.setText(title);
        }

        ImageView imageView = (ImageView) findViewById(R.id.mainImage);
        AppUtil.setImage(this, imageView, "image" + AppUtil.getRandomNumberForList(4));
        ((TextView) findViewById(R.id.description)).setText(Html.fromHtml(description.getDescription()));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Load Interstitial Ad
                if (mAdBuilder != null && !isFinishing()) {
                    mAdBuilder.showInterstitial();
                    ApplicationVariable.updateNoOfTimeInterstitialAdShownByOne();
                }
            }
        }, AdUtil.TIME_AFTER_INTERSTITIAL_AD_SHOW);


        mAdView = (AdView) findViewById(R.id.adView);
        mAdBuilder = new AdBuilder(mAdView);
        mAdBuilder.showBannerAds();

        if (AdUtil.showMoreInterstitialAd()) {
            mAdBuilder.loadInterstitial(getString(R.string.Description_Interstitial_ad_id));
        }

        if (description.getFullImageURL() != null) {
            findViewById(R.id.mainImage).setVisibility(View.VISIBLE);
        }

        initFavIcon();

    }

    private void initFavIcon() {
        favIcon = (ImageView)findViewById(R.id.fav_icon);
        favIcon.setOnClickListener(this);
        if(FavouriteUtil.getInstance().isInFavList(itemData)){
            favIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_red_heart));
        }else{
            favIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_empty_heart));
        }
    }

    public void onClickShare(View view) {
        String shareMessage = Html.fromHtml(new StringBuilder()
                .append(description.getDescription())
                .append("<br><p>To get more fun activity idea like this, download the app -")
                .append(AppUtil.getApplicationPlayStoreLink())
                .append("</p>").toString()).toString();
        AppUtil.getInstance().shareMessage(this, shareMessage);
        AppUtil.getInstance().logInFabric("Share_activity", title);
    }

    public void onClickAppShare(View view) {
        AppUtil.getInstance().shareApp(this);
    }

    @Override
    protected void onDestroy() {
        mAdBuilder.destroyAd();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fav_icon :
                if(FavouriteUtil.getInstance().isInFavList(itemData)){
                    favIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_empty_heart));
                    ScaleAnimation animation = new ScaleAnimation(1f, 1.5f, 1f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    favIcon.startAnimation(animation);
                    FavouriteUtil.getInstance().removeFavouriteInList(itemData);
                } else {
                    favIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_red_heart));
                    ScaleAnimation animation = new ScaleAnimation(1f, 1.5f, 1f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    favIcon.startAnimation(animation);
                    FavouriteUtil.getInstance().addFavouriteInList(itemData);
                }
                break;
        }
    }
}
