package br.com.kassianoresende.picpay.usecase

import br.com.kassianoresende.picpay.model.User
import br.com.kassianoresende.picpay.repository.UserRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import org.junit.Test

class GetUsersUseCaseTest {

    val usersMock =  listOf(
        User(1001,"Eduardo Santos","https://randomuser.me/api/portraits/men/9.jpg","@eduardo.santos" ),
        User(1002,"Marina Coelho","https://randomuser.me/api/portraits/women/37.jpg","@marina.coelho" ),
        User(1003,"Márcia da Silva","https://randomuser.me/api/portraits/women/57.jpg","@marcia.silva" )
    )


    @Test
    fun test_get_users_usecase_completed() {

        val repository = mock<UserRepository>{
            on { getUsers() } doReturn Observable.just(usersMock)
        }

        val useCase = GetUsersUseCase(repository)

        useCase.getUsers()
            .test()
            .assertComplete()
    }

    @Test
    fun test_get_users_usecase_error() {

        val response = Throwable("Error response")

        val repository = mock<UserRepository>{
            on { getUsers() } doReturn Observable.error(response)
        }

        val useCase = GetUsersUseCase(repository)

        useCase.getUsers()
            .test()
            .assertError(response)

    }

    @Test
    fun test_get_users_usecase_response() {


        val repository = mock<UserRepository>{
            on { getUsers() } doReturn Observable.just(usersMock)
        }

        val useCase = GetUsersUseCase(repository)

        useCase.getUsers()
            .test()
            .assertValue(usersMock)

    }
}