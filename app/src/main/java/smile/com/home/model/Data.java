package smile.com.home.model;

import java.util.ArrayList;
import java.util.List;

import smile.com.home.itemData;

/**
 * Created by   05 on 22-08-2017.
 */

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
    }


    public List<itemData> getItemDatasList() {
        return itemDatas;
    }

    public itemData getItemData(int position) {
        return itemDatas.get(position);
    }

}
