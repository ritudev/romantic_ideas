package smile.com.home;

import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import static android.view.View.VISIBLE;

public class AdBuilder extends AdListener {
    private static AdView mAdView;
    //public static final boolean DEBUG_ENABLED = BuildConfig.DEBUG;
    public static final boolean DEBUG_ENABLED = false;
    private InterstitialAd mInterstitialAd;

    public AdBuilder(AdView adView) {
        this.mAdView = adView;
    }

    /**
     * Show Ad only to release builds.
     */
    public void showBannerAds() {
        if (!DEBUG_ENABLED) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR) // All Emulator
                    .addTestDevice(AdUtil.TEST_DEVICE_ID)
                    .build();
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(this);
        }
    }

    @Override
    public void onAdLoaded() {
        super.onAdLoaded();
        mAdView.setVisibility(VISIBLE);
    }

    public void pauseAd() {
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    public void destroyAd() {
        if (mAdView != null) {
            mAdView.destroy();
        }
    }

    public void resumeAd() {
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    public void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else if (DEBUG_ENABLED) {
            Toast.makeText(Application.getContext(), "Build Type: " + BuildConfig.BUILD_TYPE + ". Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isInterstitialAdLoaded() {
        if (mInterstitialAd == null) {
            return false;
        } else {
            return mInterstitialAd.isLoaded();
        }
    }

    public void loadInterstitial(String adUnitId) {
        if (!DEBUG_ENABLED) {
            mInterstitialAd = newInterstitialAd(adUnitId);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdUtil.TEST_DEVICE_ID)
                    .setRequestAgent("android_studio:ad_template").build();
            mInterstitialAd.loadAd(adRequest);
        }
    }

    private InterstitialAd newInterstitialAd(String adUnitId) {
        InterstitialAd interstitialAd = new InterstitialAd(Application.getContext());
        interstitialAd.setAdUnitId(adUnitId);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdClosed() {
            }
        });
        return interstitialAd;
    }
}
