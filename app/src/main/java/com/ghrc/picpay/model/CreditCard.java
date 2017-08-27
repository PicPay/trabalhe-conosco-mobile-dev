package com.ghrc.picpay.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Guilherme on 27/08/2017.
 */

public class CreditCard implements Parcelable {
    private String id,card_number, cvv, expiry_date;

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

    public CreditCard() {
    }

    public CreditCard(String id,String card_number, String cvv, String expiry_date) {
       this.id  = id;
        this.card_number = card_number;
        this.cvv = cvv;
        this.expiry_date = expiry_date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.card_number);
        dest.writeString(this.cvv);
        dest.writeString(this.expiry_date);
    }

    protected CreditCard(Parcel in) {
        this.id = in.readString();
        this.card_number = in.readString();
        this.cvv = in.readString();
        this.expiry_date = in.readString();
    }

    public static final Parcelable.Creator<CreditCard> CREATOR = new Parcelable.Creator<CreditCard>() {
        @Override
        public CreditCard createFromParcel(Parcel source) {
            return new CreditCard(source);
        }

        @Override
        public CreditCard[] newArray(int size) {
            return new CreditCard[size];
        }
    };
}
