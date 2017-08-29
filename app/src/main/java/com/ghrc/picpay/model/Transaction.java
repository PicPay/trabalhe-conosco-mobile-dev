package com.ghrc.picpay.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Guilherme on 28/08/2017.
 */

public class Transaction implements Parcelable {
    private String card_number, cvv,expiry_date, destination_user_id,data;
   private double value;

    public Transaction(String card_number, String cvv, String expiry_date, String destination_user_id, String data, double value) {
        this.card_number = card_number;
        this.cvv = cvv;
        this.expiry_date = expiry_date;
        this.destination_user_id = destination_user_id;
        this.value = value;
        this.data = data;
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
/*   //{
   "card_number":"1111111111111111",
           "cvv":789,
           "value":79.9,
           "expiry_date":"01/18",
           "destination_user_id":1002
}*/

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
        dest.writeDouble(this.value);
    }

    protected Transaction(Parcel in) {
        this.card_number = in.readString();
        this.cvv = in.readString();
        this.expiry_date = in.readString();
        this.destination_user_id = in.readString();
        this.data = in.readString();
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
