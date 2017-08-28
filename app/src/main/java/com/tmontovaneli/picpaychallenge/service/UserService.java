package com.tmontovaneli.picpaychallenge.service;

import com.tmontovaneli.picpaychallenge.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by tmontovaneli on 21/08/17.
 */

public interface UserService {


    @GET("users")
    Call<List<User>> listRepos();


}
