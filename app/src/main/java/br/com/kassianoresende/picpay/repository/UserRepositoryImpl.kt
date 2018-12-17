package br.com.kassianoresende.picpay.repository

import br.com.kassianoresende.picpay.model.User
import br.com.kassianoresende.picpay.service.ApiService
import io.reactivex.Observable
import javax.inject.Inject

class UserRepositoryImpl  @Inject constructor(private val apiService: ApiService) : UserRepository {

    override fun getUsers(): Observable<List<User>> =
        apiService.getUsers()

}