package smile.com.home;

import android.content.Context;
import android.content.res.Resources;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import io.fabric.sdk.android.Fabric;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class Application extends android.app.Application implements TextToSpeech.OnInitListener {

    private static final int MIN_DAYS_FOR_BOOK_REQUEST = 1;
    public static final float SPEECH_RATE = 0.85f;
    private static Application sInstance;
    private static android.app.Application appInstance;
    private TextToSpeech tts;
    private String TAG = Application.class.getSimpleName();
    private static Locale loc = new Locale("hin", "IND");

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Answers(), new Crashlytics());
        appInstance = this;
        AppUtil.setContext(this);

/*
        Fabric.with(this, new Crashlytics());
        Fabric.with(this, new Answers());

        //Init Network data if available.
        NetworkUtil.getInstance().populateNetworkInfoInLocalPaperDb(this);
        NetworkUtil.getInstance().populateOthersAppData(this);

        FirebaseMessaging.getInstance().subscribeToTopic("ver" + AppUtils.getInstance().getAppVersionCode());
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        */
    }

    public static Context getContext() {
        return appInstance.getApplicationContext();
    }

    public static synchronized Application getInstance() {
        if (sInstance == null) {
            sInstance = new Application();
        }
        return sInstance;
    }

    @Nullable
    public String getDataJSONFromAsset(String fileName) {
        String json;
        try {
            InputStream is = getContext().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public String getAppVersion() {
        return BuildConfig.VERSION_NAME;
    }

    public int getAppVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    public TextToSpeech getTTS() {
        if (tts == null) {
            tts = new TextToSpeech(getContext(), this);
        }
        return tts;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(loc);
            tts.setPitch(1); // set pitch level
            tts.setSpeechRate(SPEECH_RATE); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            }

        } else {
            tts = null;
            Log.e("TTS", "Initilization Failed");
        }

    }

    @Override
    public void onTerminate() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onTerminate();
    }


    /**
     * This method checks if dialog fragment is active or not
     *
     * @param frag
     * @return
     */
    public static boolean isDialogFragmentUIActive(DialogFragment frag) {
        return (frag != null && frag.getActivity() != null && !frag.getActivity().isFinishing() && frag.getDialog() != null &&
                frag.getDialog().isShowing() && frag.isResumed());
    }

    public float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

}