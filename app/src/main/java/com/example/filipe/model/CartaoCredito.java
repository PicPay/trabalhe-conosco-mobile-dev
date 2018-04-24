package com.example.filipe.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by filipe on 23/04/18.
 */

public class CartaoCredito extends RealmObject implements Parcelable {

    @PrimaryKey
    private long id;

    private String card_number;
    private int cvv;
    private String expiry_date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.card_number);
        dest.writeInt(this.cvv);
        dest.writeString(this.expiry_date);
    }

    public CartaoCredito() {
    }

    protected CartaoCredito(Parcel in) {
        this.id = in.readLong();
        this.card_number = in.readString();
        this.cvv = in.readInt();
        this.expiry_date = in.readString();
    }

    public static final Creator<CartaoCredito> CREATOR = new Creator<CartaoCredito>() {
        @Override
        public CartaoCredito createFromParcel(Parcel source) {
            return new CartaoCredito(source);
        }

        @Override
        public CartaoCredito[] newArray(int size) {
            return new CartaoCredito[size];
        }
    };
}
