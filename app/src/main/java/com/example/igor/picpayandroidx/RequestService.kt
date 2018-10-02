package com.example.igor.androidxtest

import com.example.igor.picpayandroidx.Model.Transaction
import com.example.igor.picpayandroidx.Model.TransactionRequest
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RequestService {
    @GET("users")
    fun getContacts() : Call<List<Contact>>

    @POST("transaction")
    fun postTransaction(@Body request: TransactionRequest?) : Call<JsonObject>

}