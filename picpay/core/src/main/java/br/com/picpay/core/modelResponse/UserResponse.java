package br.com.picpay.core.modelResponse;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import br.com.picpay.core.util.StringUtil;

public class UserResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("img")
    private String img;

    @SerializedName("username")
    private String userName;

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
    public String getUserName() {
        return StringUtil.fixString(userName);
    }
}
