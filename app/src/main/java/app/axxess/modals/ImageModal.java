package app.axxess.modals;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageModal {

    @SerializedName("data")
    public List<ImageItem> data;



    public static class ImageItem implements Parcelable{
        @SerializedName("id")
        public String id;
        @SerializedName("title")
        public String title;
        @SerializedName("images")
        public List<Links> images;

        protected ImageItem(Parcel in) {
            id = in.readString();
            title = in.readString();
            images = in.createTypedArrayList(Links.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(title);
            dest.writeTypedList(images);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
            @Override
            public ImageItem createFromParcel(Parcel in) {
                return new ImageItem(in);
            }

            @Override
            public ImageItem[] newArray(int size) {
                return new ImageItem[size];
            }
        };
    }




    public static class Links implements Parcelable{
        @SerializedName("link")
        public String link;
        @SerializedName("type")
        public String type;

        protected Links(Parcel in) {
            link = in.readString();
            type = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(link);
            dest.writeString(type);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Links> CREATOR = new Creator<Links>() {
            @Override
            public Links createFromParcel(Parcel in) {
                return new Links(in);
            }

            @Override
            public Links[] newArray(int size) {
                return new Links[size];
            }
        };
    }

}
