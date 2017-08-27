package com.ghrc.picpay.api;


import com.ghrc.picpay.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Guilherme on 26/08/2017.
 */

public interface PicPayApi {
    @GET("users")
    Call<ArrayList<User>> getUsers();

}
