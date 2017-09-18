package smile.com.home.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import smile.com.home.itemData;
import smile.com.home.response;

/**
 * Created by   05 on 22-08-2017.
 */

public class FavouriteUtil {

    private static final String FAVOURITES_LIST = "favourites";
    private static FavouriteUtil sInstance = new FavouriteUtil();
    private List<itemData> favItemDatas = new ArrayList<>();

    private FavouriteUtil(){
    }

    public static FavouriteUtil getInstance(){
        return sInstance;
    }

    public void saveFavouriteInSharedPref() {
        if(favItemDatas == null || favItemDatas.isEmpty()){
            SharedPrefUtils.getInstance().putString(FAVOURITES_LIST, null);
            return;
        }
        response response = new response();
        response.setItemData(favItemDatas);
        GsonBuilder mGsonBuilder = new GsonBuilder();
        Gson mGson = mGsonBuilder.create();
        String favListAsString = mGson.toJson(response, response.class);
        SharedPrefUtils.getInstance().putString(FAVOURITES_LIST, favListAsString);
    }

    public void loadFavouriteFromSharedPref() {
        String favListAsString = SharedPrefUtils.getInstance().getString(FAVOURITES_LIST);
        GsonBuilder mGsonBuilder = new GsonBuilder();
        Gson mGson = mGsonBuilder.create();
        response response = mGson.fromJson(favListAsString, response.class);
        if(response != null) {
            favItemDatas = response.getItemData();
        }
    }

    public void addFavouriteInList(itemData itemData) {
        favItemDatas.add(itemData);
    }

    public List<itemData> getFavouriteList() {
        return favItemDatas;
    }

    public boolean isInFavList(itemData itemData) {
        if(favItemDatas.contains(itemData)){
            return true;
        }
        return false;
    }

    public void removeFavouriteInList(itemData itemData) {
        favItemDatas.remove(itemData);
    }
}