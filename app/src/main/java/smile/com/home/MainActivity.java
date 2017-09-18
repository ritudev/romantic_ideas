package smile.com.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import smile.com.home.model.Data;
import smile.com.home.utils.FavouriteUtil;

import static smile.com.home.Constants.DESCRIPTION;
import static smile.com.home.Constants.SELECTED_ITEM;
import static smile.com.home.Constants.TITLE;


public class MainActivity extends AppCompatActivity implements RatingDialog.RatingDialogFormListener, RatingDialog.RatingDialogListener {

    private static final float RATING_THRESHOLD = 4;
    private LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    AdView mAdView;
    AdBuilder mAdBuilder;
    ProgressBar mProgressBar;
    private ImageView loader;

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private RatingDialog ratingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.wallet_progressBar);
        loader = (ImageView) findViewById(R.id.loader);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (!AppUtil.isNetworkAvailable()) {
            showNetworkNotAvailableError();
            showOfflineDataIfAny();
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
            loader.setVisibility(View.GONE);
        }
        mAdView = (AdView) findViewById(R.id.adView);
        mAdBuilder = new AdBuilder(mAdView);
        mAdBuilder.showBannerAds();
        initDrawer();

        createRequest();
        FavouriteUtil.getInstance().loadFavouriteFromSharedPref();
    }

    private void initDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        mPlanetTitles = getResources().getStringArray(R.array.drawer_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setBackgroundColor(getResources().getColor(R.color.white));


        ToolBarItemAdapter itemAdapter = new ToolBarItemAdapter(this, R.layout.drawer_list_item);
        // Set the adapter for the list view
        mDrawerList.setAdapter(itemAdapter);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_nav_icon));
        setToolBarTitle(getSupportActionBar().getTitle());
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_nav_icon,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                setToolBarTitle(getTitle());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                setToolBarTitle(getTitle());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setToolBarTitle(CharSequence title) {
        Spannable text = new SpannableString(title);
        text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.white)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(text);
    }

    private void showNetworkNotAvailableError() {
        findViewById(R.id.errorTextView).setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        loader.setVisibility(View.GONE);
    }

    private void createRequest() {
        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("https://dixits-school-manual.firebaseapp.com/dateIdea.json")
                .build();

        client.setConnectTimeout(15, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(15, TimeUnit.SECONDS);    // socket timeout
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showNetworkNotAvailableError();
                        showOfflineDataIfAny();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    onFailure(response.request(), null);
                    return;
                }
                InputStream inputStream = response.body().byteStream();
                GsonBuilder mGsonBuilder = new GsonBuilder();
                Gson mGson = mGsonBuilder.create();
                Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                final response GsonResponse = mGson.fromJson(reader, response.class);
                if (GsonResponse == null) {
                    onFailure(response.request(), null);
                    return;
                }
                 runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                        loader.setVisibility(View.GONE);
                        List<itemData> itemDataListFromResponse = GsonResponse.getItemData();
                        Collections.shuffle(itemDataListFromResponse);
                        updateAdapter(itemDataListFromResponse);
                        Data.getInstance().initDataFromResponse(itemDataListFromResponse);
                    }
                });
            }
        });
    }

    private void showOfflineDataIfAny() {
        TextView view = (TextView)findViewById(R.id.errorTextView);
        view.setText("Working Offline, Please check your internet connection to get new ideas");
        updateAdapter(Data.getInstance().getItemDatasList());
    }

    private void updateAdapter(List<itemData> itemDataListFromResponse) {
        if(itemDataListFromResponse != null && !itemDataListFromResponse.isEmpty()) {
            SolventRecyclerViewAdapter rcAdapter = new SolventRecyclerViewAdapter(MainActivity.this, itemDataListFromResponse);
            recyclerView.setAdapter(rcAdapter);
        }
    }

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

        /**
         * Swaps fragments in the menu content view
         */
        private void selectItem(int position) {
            switch (position) {
                case 0:
                    openFavActivity();
                    break;
            case 1:
                initRatingDialog(RATING_THRESHOLD, 1);
                break;
            case 2:
                openTodayIdea();
                break;
            /*case 2:
                AppUtils.getInstance().shareApp(this);
                break;
            case 3:
                changeLanguage();
                break;
            */default:
            }
            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    private void openTodayIdea() {
        Intent intent = new Intent(this, DescriptionActivity.class);
        if(Data.getInstance().getItemDatasList() == null || Data.getInstance().getItemDatasList().isEmpty()) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show();
        } else {
            itemData itemData = AppUtil.getTodayIdea();
            if (itemData.getDescription() != null) {
                intent.putExtra(DESCRIPTION, itemData.getDescription());
            }
            intent.putExtra(TITLE, itemData.getItemText());
            intent.putExtra(SELECTED_ITEM, itemData);
            startActivity(intent);
            AppUtil.getInstance().logInFabric("open_detail_page_today idea_", itemData.getItemText());
        }
    }

    private void openFavActivity() {
        Intent intent = new Intent(this, FavActivity.class);
        startActivity(intent);
    }
/*

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Test User");
    }
*/



    //###################Code for rating Dialog ###########################33

    private void initRatingDialog(float ratingThreshold, int sessionThreshold) {
        ratingDialog = new RatingDialog.Builder(this)
                .threshold(ratingThreshold)
                .session(sessionThreshold)
                .onRatingBarFormSumbit(this)
                .onRatingChanged(this)
                .build();

        if (ratingThreshold == 1 && sessionThreshold == 1) {
            ratingDialog.clearSharedPrefForRating();
        }

        ratingDialog.show();
        Answers.getInstance().logCustom(new CustomEvent("RATING_DIALOG_SHOWN"));
    }

    @Override
    public void onRatingSelected(float rating, boolean thresholdCleared) {
        if (thresholdCleared) {
            AppUtil.getInstance().launchAppStoreForRating();
            ratingDialog.showNever();
            ratingDialog.dismiss();
        }
        //Tracking
        Answers.getInstance().logCustom(new CustomEvent("APP_RATED_" + rating));
    }

    @Override
    public void onFormSubmitted(String feedback) {
        Answers.getInstance().logCustom(new CustomEvent("FEEDBACK")
                .putCustomAttribute("USER_FEEDBACK", feedback));
    }

    @Override
    public void finish() {
        FavouriteUtil.getInstance().saveFavouriteInSharedPref();
        super.finish();
    }
}