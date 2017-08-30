package com.ghrc.picpay.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Guilherme on 28/08/2017.
 */

public class Transaction implements Parcelable {
    private String card_number, cvv,expiry_date, destination_user_id,data,user_name, img_user;
    private double value;

    public Transaction(String destination_user_id, String data, String user_name, String img_user, double value) {
        this.destination_user_id = destination_user_id;
        this.data = data;
        this.user_name = user_name;
        this.img_user = img_user;
        this.value = value;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getImg_user() {
        return img_user;
    }

    public void setImg_user(String img_user) {
        this.img_user = img_user;
    }

    public Transaction(){}

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getDestination_user_id() {
        return destination_user_id;
    }

    public void setDestination_user_id(String destination_user_id) {
        this.destination_user_id = destination_user_id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.card_number);
        dest.writeString(this.cvv);
        dest.writeString(this.expiry_date);
        dest.writeString(this.destination_user_id);
        dest.writeString(this.data);
        dest.writeString(this.user_name);
        dest.writeString(this.img_user);
        dest.writeDouble(this.value);
    }

    public Transaction(Parcel in) {
        this.card_number = in.readString();
        this.cvv = in.readString();
        this.expiry_date = in.readString();
        this.destination_user_id = in.readString();
        this.data = in.readString();
        this.user_name = in.readString();
        this.img_user = in.readString();
        this.value = in.readDouble();
    }

    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel source) {
            return new Transaction(source);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
