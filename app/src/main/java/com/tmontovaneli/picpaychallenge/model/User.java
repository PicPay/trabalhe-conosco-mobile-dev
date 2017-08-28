package com.tmontovaneli.picpaychallenge.model;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by tmontovaneli on 21/08/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable, Comparable<User> {

    private Long id;

    private String name;

    private String img;

    private String username;

    private Bitmap bitmap;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getUsername() {
        return username;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public int compareTo(@NonNull User o) {
        return this.getName().compareTo(o.getName());
    }
}
