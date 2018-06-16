package com.example.eduardo.demoapppagamento.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "cards")
public class Card {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "card_type")
    public String type;

    @ColumnInfo(name = "card_number")
    public String number;

    @ColumnInfo(name = "expiry_month")
    public int expiry_month;

    @ColumnInfo(name = "expiry_year")
    public int expiry_year;

    @ColumnInfo(name = "cvv")
    public String cvv;

}
