package br.com.picpay.picpay.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import br.com.picpay.core.util.StringUtil;

public class Transaction {

    private String id;
    private int timestamp;
    private float value;
    private User destinationUser;
    private boolean success;
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
    public User getDestinationUser() {
        return destinationUser;
    }

    public boolean isSuccess() {
        return success;
    }

    @NonNull
    public String getStatus() {
        return StringUtil.fixString(status);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setDestinationUser(User destinationUser) {
        this.destinationUser = destinationUser;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
