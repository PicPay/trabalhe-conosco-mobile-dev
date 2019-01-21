package com.jvtnascimento.picpay.services.api

import com.jvtnascimento.picpay.models.Transaction
import com.jvtnascimento.picpay.models.TransactionRequest
import com.jvtnascimento.picpay.models.TransactionResponse
import com.jvtnascimento.picpay.models.User
import com.jvtnascimento.picpay.services.Constants
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.ArrayList

interface ApiServiceInterface {
    @GET("users")
    fun getUsers(): Observable<ArrayList<User>>

    @POST("transaction")
    fun pay(@Body transaction: TransactionRequest): Observable<TransactionResponse>

    companion object {
        var retrofit: Retrofit? = null

        fun create(): ApiServiceInterface {
            if (this.retrofit == null) {
                this.retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .build()
            }

            return this.retrofit!!.create(ApiServiceInterface::class.java)
        }
    }
}