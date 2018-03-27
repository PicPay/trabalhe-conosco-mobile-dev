package mobiledev.erickgomes.picpayapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by erickgomes on 23/03/2018.
 */

public class Friend implements Parcelable {

    private int id;
    private String name;
    private String img;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.img);
        dest.writeString(this.username);
    }

    public Friend() {
    }

    protected Friend(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.img = in.readString();
        this.username = in.readString();
    }

    public static final Parcelable.Creator<Friend> CREATOR = new Parcelable.Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel source) {
            return new Friend(source);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
}
