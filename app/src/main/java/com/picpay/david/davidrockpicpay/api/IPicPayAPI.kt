package com.picpay.david.davidrockpicpay.api
import com.picpay.david.davidrockpicpay.models.TransactionResponse
import com.picpay.david.davidrockpicpay.models.User
import retrofit2.http.*
import io.reactivex.Single


interface IPicPayAPI{

    @GET("/users")
    fun GetUsers(): Single<List<User>>

    @POST("/transaction")
    fun SendMoney(@Body disponibilidade: TransactionResponse): Single<TransactionResponse>


}