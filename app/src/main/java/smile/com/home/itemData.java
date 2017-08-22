package smile.com.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by   05 on 28-04-2017.
 */

public class itemData implements Parcelable {
    @SerializedName("name")
    private String itemText;
    @SerializedName("photo")
    private String imageUrl;
    private boolean selected;
    private Description description;

    public itemData(String name, String photo) {
        this.itemText = name;
        this.imageUrl = photo;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Description getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        return itemText.equals(((itemData)o).itemText);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemText);
        dest.writeString(this.imageUrl);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.description, flags);
    }

    protected itemData(Parcel in) {
        this.itemText = in.readString();
        this.imageUrl = in.readString();
        this.selected = in.readByte() != 0;
        this.description = in.readParcelable(Description.class.getClassLoader());
    }

    public static final Parcelable.Creator<itemData> CREATOR = new Parcelable.Creator<itemData>() {
        @Override
        public itemData createFromParcel(Parcel source) {
            return new itemData(source);
        }

        @Override
        public itemData[] newArray(int size) {
            return new itemData[size];
        }
    };
}
