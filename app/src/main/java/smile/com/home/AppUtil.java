package smile.com.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by   05 on 26-07-2017.
 */

public class AppUtil {


    private static Context context;
    private static AppUtil sInstance = new AppUtil();

    public static AppUtil getInstance() {
        return sInstance;
    }


    public static Context getContext() {
        return context;
    }

    public static Context getApplicationContext() {
        return context;
    }

    public static void setContext(Context context) {
        AppUtil.context = context;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static String getApplicationPlayStoreLink(){
        String PACKAGE_NAME = getContext().getPackageName();
        return "https://play.google.com/store/apps/details?id="+ PACKAGE_NAME;
    }


    public void launchAppStoreForRating() {
        Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;

        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        goToMarket.addFlags(flags);

        try {
            getContext().startActivity(goToMarket);
        } catch (Exception e) {
        }
    }
    public void shareApp(Activity activity) {
        try {
            String appName = getContext().getResources().getString(R.string.app_name);
            String PACKAGE_NAME = getContext().getPackageName();

            String shareMessage = Html.fromHtml(new StringBuilder()
                    .append("<br><p>To get fun activity idea, download the app -")
                    .append(AppUtil.getApplicationPlayStoreLink())
                    .append("</p>").toString()).toString();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, appName);
            i.putExtra(Intent.EXTRA_TEXT, shareMessage);
            activity.startActivity(Intent.createChooser(i, appName));
            Answers.getInstance().logCustom(new CustomEvent("SHARED_APPLICATION"));
        } catch (Exception e) {
            Log.e("appUtils", "shareApp: " + e);
        }
    }

    public void shareMessage(Context context, String messageToShare) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, messageToShare);
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        } catch (Exception ex) {
            Log.e("appUtils", ex.toString(), ex);
        }
    }

    public void logInFabric(String event, String category) {
        Answers.getInstance().logCustom(new CustomEvent(event)
                .putCustomAttribute("Category", category));
    }

    /**
     * This method dismiss a dialog fragment.
     *
     * @param activity
     * @param dialogFragment
     */
    public static void dismissDialogFragment(Activity activity, DialogFragment dialogFragment) {
        if (Application.isDialogFragmentUIActive(dialogFragment)
                && null != activity && activity.isFinishing()) {
            dialogFragment.dismissAllowingStateLoss();
        }
    }

    /*public Drawable changePngColor(int drawableSrc, int color) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), drawableSrc).mutate();
        drawable.setColorFilter(ContextCompat.getColor(getContext(), color), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }

    public void launchAppStoreForRating() {
        Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;

        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        goToMarket.addFlags(flags);

        try {
            startActivity(goToMarket);
        } catch (Exception e) {
        }
    }

    public boolean isFragmentUIActive(android.support.v4.app.Fragment frag) {
        return frag != null && frag.getActivity() != null && frag.isAdded() && !frag.isDetached() && !frag.isRemoving();
    }


    *//**
     * Load image from networks and save it to cache.
     * From next time it will serve from cache.
     *
     * @param imageUrl Url of the Image
     * @param image    ImageView
     *//*
    public void loadImage(final String imageUrl, final ImageView image, Context context) {
*//*
        Picasso.with(getContext())
                .load(imageUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .error(ContextCompat.getDrawable(getContext(), R.drawable.common_full_open_on_phone))
                .placeholder(ContextCompat.getDrawable(getContext(), R.drawable.common_full_open_on_phone))
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        Picasso.with(AppUtils.getInstance())
                                .load(imageUrl)
                                .error(ContextCompat.getDrawable(getContext(), R.drawable.common_full_open_on_phone))
                                .placeholder(ContextCompat.getDrawable(getContext(), R.drawable.common_full_open_on_phone))
                                .into(image);
                    }
                });
*//*
    }

    public void openPlayStore(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(i);

*//*        Answers.getInstance().logCustom(new CustomEvent("PLAY_STORE_OPENED")
                .putCustomAttribute("Category", url));
 *//*   }

   *//* public void openPlayStoreForPackage(String marketUrl) {
        final Uri marketUri = Uri.parse(marketUrl);
        Answers.getInstance().logCustom(new CustomEvent("PLAY_STORE_OPENED")
                .putCustomAttribute("Category", marketUrl));
        try {
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, marketUri));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "Couldn't find PlayStore on this device", Toast.LENGTH_SHORT).show();
        }
    }

    public void openWebView(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder().setShowTitle(true);
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        builder.setStartAnimations(this, R.anim.enter_from_right, R.anim.exit_to_left);
        builder.setExitAnimations(this, R.anim.enter_from_left, R.anim.exit_to_right);
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
*/


/*public List<Integer> getBgColorForMoreApps() {
        TypedArray ta = getContext().getResources().obtainTypedArray(R.array.more_apps_background_colors);
        Integer[] colors = new Integer[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            colors[i] = ta.getColor(i, 0);
        }
        ta.recycle();
        return Arrays.asList(colors);
    }*//*

    public void onLanguageChanged() {
        tts = new TextToSpeech(getContext(), this);
    }

*/


    public static String getRandomImageForList(ArrayList<String> imageList){
        int size = imageList.size()-1;
        Random rand = new Random();
        int rndInt = rand.nextInt(size); // n = the number of images, that start at idx 1
        String imgName = imageList.get(rndInt);
        return imgName;
    }

    // range is 0-range
    public static int getRandomNumberForList(int range){
        Random rand = new Random();
        return rand.nextInt(range) + 1; // n = the number of images, that start at idx 1
    }

    public static void setImage(Context context, ImageView imgView, String imgName){
        int id = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
        imgView.setImageResource(id);
    }

    public static void prepareImageList(ArrayList<String> imageList) {
        imageList.add("ic_flower");
        imageList.add("ic_heart");
        imageList.add("ic_person");
    }


    public Drawable changePngColor(int drawableSrc, int color) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), drawableSrc).mutate();
        drawable.setColorFilter(ContextCompat.getColor(getContext(), color), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }

}
