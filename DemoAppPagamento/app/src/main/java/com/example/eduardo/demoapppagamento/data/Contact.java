package com.example.eduardo.demoapppagamento.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

//Ex: {"id":1001,"name":"Eduardo Santos","img":"https://randomuser.me/api/portraits/men/9.jpg","username":"@eduardo.santos"}
@Entity(tableName = "contacts")
public final class Contact {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final int mId;

    @ColumnInfo(name = "name")
    private final String mName;

    @ColumnInfo(name = "img")
    private final String mImg;

    @ColumnInfo(name = "username")
    private final String mUsername;

    @Ignore
    public Contact (int id, String name, String img, String username) {
        mId = id;
        mName = name;
        mImg = img;
        mUsername = username;
    }

    @NonNull
    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getImg() {
        return mImg;
    }

    public String getUsername() {
        return mUsername;
    }

}
