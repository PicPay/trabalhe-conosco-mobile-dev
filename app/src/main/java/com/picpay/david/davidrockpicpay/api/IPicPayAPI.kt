package com.picpay.david.davidrockpicpay.api
import com.picpay.david.davidrockpicpay.models.User
import retrofit2.http.*
import io.reactivex.Single


interface IPicPayAPI{

    @GET("/users")
    fun enviarSenha(@Query("login") login: String): Single<User>

    @POST("/transaction")
    fun alternarDisponibilidade(@Body disponibilidade: Disponibilidade): Single<RespostaPadrao>


}