package br.com.kassianoresende.picpay.usecase

import br.com.kassianoresende.picpay.model.User
import br.com.kassianoresende.picpay.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    fun getUsers(): Observable<List<User>> =
        userRepository.getUsers()



}