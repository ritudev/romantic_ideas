package smile.com.home.utils;

import java.util.ArrayList;
import java.util.List;

import smile.com.home.itemData;

/**
 * Created by   05 on 22-08-2017.
 */

public class FavouriteUtil {

    private static FavouriteUtil sInstance = new FavouriteUtil();
    private List<itemData> itemDatas = new ArrayList<>();

    private FavouriteUtil(){

    }

    public static FavouriteUtil getInstance(){
        return sInstance;
    }
    public void saveFavouriteInSharedPref() {
    }

    public void loadFavouriteFromSharedPref() {
    }

    public void addFavouriteInList(itemData itemData) {
        itemDatas.add(itemData);
    }

    public List<itemData> getFavouriteList() {
        return itemDatas;
    }

    public boolean isInFavList(itemData itemData) {
        if(itemDatas.contains(itemData)){
            return true;
        }
        return false;
    }

    public void removeFavouriteInList(itemData itemData) {
        itemDatas.remove(itemData);
    }
}