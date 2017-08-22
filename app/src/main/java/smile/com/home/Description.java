package smile.com.home;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by   05 on 19-07-2017.
 */
public class Description implements Parcelable {
    private String description;
    private String fullImageUrl;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullImageURL() {
        return fullImageUrl;
    }

    public void setFullImageUrl(String fullImageUrl) {
        this.fullImageUrl = fullImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeString(this.fullImageUrl);
    }

    public Description() {
    }

    protected Description(Parcel in) {
        this.description = in.readString();
        this.fullImageUrl = in.readString();
    }

    public static final Parcelable.Creator<Description> CREATOR = new Parcelable.Creator<Description>() {
        @Override
        public Description createFromParcel(Parcel source) {
            return new Description(source);
        }

        @Override
        public Description[] newArray(int size) {
            return new Description[size];
        }
    };
}
