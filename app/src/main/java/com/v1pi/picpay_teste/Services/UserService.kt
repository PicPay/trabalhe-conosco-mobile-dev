package com.v1pi.picpay_teste.Services

import com.v1pi.picpay_teste.Domains.User
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET

interface UserService {
    @GET("users")
    fun users() : Flowable<List<User>>
}