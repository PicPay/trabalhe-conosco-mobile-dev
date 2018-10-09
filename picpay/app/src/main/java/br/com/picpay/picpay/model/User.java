package br.com.picpay.picpay.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import br.com.picpay.core.util.StringUtil;

public class User implements Parcelable {

    private String id;
    private String name;
    private String img;
    private String username;

    public User() {
    }

    private User(Parcel in) {
        id = in.readString();
        name = in.readString();
        img = in.readString();
        username = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    public String getId() {
        return StringUtil.fixString(id);
    }

    @NonNull
    public String getName() {
        return StringUtil.fixString(name);
    }

    @NonNull
    public String getImg() {
        return StringUtil.fixString(img);
    }

    @NonNull
    public String getUsername() {
        return StringUtil.fixString(username);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(img);
        dest.writeString(username);
    }
}
