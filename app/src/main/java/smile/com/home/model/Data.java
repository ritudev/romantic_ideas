package smile.com.home.model;

import java.util.ArrayList;
import java.util.List;

import smile.com.home.itemData;
import smile.com.home.utils.SharedPrefUtils;

import static smile.com.home.AppUtil.jsonStringToListData;
import static smile.com.home.AppUtil.listToJsonString;

public class Data {
    private static Data sInstance = new Data();
    private List<itemData> itemDatas = new ArrayList<>();

    private Data(){
    }

    public static Data getInstance(){
        return sInstance;
    }

    public void initDataFromResponse(List<itemData> itemDatasFromResponse) {
        itemDatas.addAll(itemDatasFromResponse);
        storeDataInSharedPref(itemDatas);
    }

    public List<itemData> getItemDatasList() {
        if(itemDatas == null || itemDatas.isEmpty()){
            itemDatas = getDataFromSharedPref();
        }
        return itemDatas;
    }

    public itemData getItemData(int position) {
        if(itemDatas.isEmpty() || position > itemDatas.size()){
            getItemDatasList();
            if(itemDatas.isEmpty() || position > itemDatas.size()) {
                return null;
            }
        }
        return itemDatas.get(position);
    }

    public List<itemData> getDataFromSharedPref(){
        String itemDataString = SharedPrefUtils.getInstance().getString("DATA");
        if(itemDataString != null) {
            return jsonStringToListData(itemDataString);
        }
        return new ArrayList<>();
    }

    public void storeDataInSharedPref(List<itemData> itemDatas){
        String itemDataString = listToJsonString(itemDatas);
        SharedPrefUtils.getInstance().putString("DATA",itemDataString);
    }
}
