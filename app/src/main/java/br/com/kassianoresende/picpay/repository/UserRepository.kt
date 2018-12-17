package br.com.kassianoresende.picpay.repository

import br.com.kassianoresende.picpay.model.User
import io.reactivex.Observable

interface UserRepository {

    fun getUsers():Observable<List<User>>

}