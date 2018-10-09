package br.com.picpay.core.modelResponse;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import br.com.picpay.core.util.StringUtil;

public class TransactionResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("timestamp")
    private int timestamp;

    @SerializedName("value")
    private float value;

    @SerializedName("destination_user")
    private UserResponse destinationUser;

    @SerializedName("success")
    private boolean success;

    @SerializedName("status")
    private String status;

    @NonNull
    public String getId() {
        return StringUtil.fixString(id);
    }

    public int getTimestamp() {
        return timestamp;
    }

    public float getValue() {
        return value;
    }

    @Nullable
    public UserResponse getDestinationUser() {
        return destinationUser;
    }

    public boolean isSuccess() {
        return success;
    }

    @NonNull
    public String getStatus() {
        return StringUtil.fixString(status);
    }
}
