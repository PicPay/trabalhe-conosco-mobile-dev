package br.com.kassianoresende.picpay.service

import br.com.kassianoresende.picpay.model.User
import io.reactivex.Observable
import retrofit2.http.GET


interface ApiService {

    @GET("tests/mobdev/users")
    fun getUsers(): Observable<List<User>>


}

