package com.ghrc.picpay.api;


import com.ghrc.picpay.model.Transaction;
import com.ghrc.picpay.model.User;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Guilherme on 27/08/2017.
 */

public interface PicPayApi {
    @GET("users")
    Call<ArrayList<User>> getUsers();

    @POST("transaction")
    @Headers("Content-Type: application/json")
    Call<JsonObject> sendTransaction(@Body RequestBody object);
}
