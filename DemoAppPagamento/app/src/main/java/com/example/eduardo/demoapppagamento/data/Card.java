package com.example.eduardo.demoapppagamento.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "cards")
public class Card implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String number;
    private int expiryMonth;
    private int expiryYear;
    private int cvv;

    /*

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "card_type")
    public String type;

    @ColumnInfo(name = "card_number")
    public String number;

    @ColumnInfo(name = "expiry_month")
    public int expiry_month;

    @ColumnInfo(name = "expiry_year")
    public int expiry_year;

    @ColumnInfo(name = "cvv")
    public int cvv;*/


    public Card (int id, String number, int expiryMonth, int expiryYear, int cvv) {
        this.id = id;
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
    }

    @Ignore
    public Card (String number, int expiryMonth, int expiryYear, int cvv) {
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

}
