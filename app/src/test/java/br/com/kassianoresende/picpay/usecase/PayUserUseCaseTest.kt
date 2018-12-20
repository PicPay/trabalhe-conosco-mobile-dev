package br.com.kassianoresende.picpay.usecase

import br.com.kassianoresende.picpay.model.PayUserTransaction
import br.com.kassianoresende.picpay.model.TransactionResponse
import br.com.kassianoresende.picpay.repository.PayUserRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import org.junit.Test

class PayUserUseCaseTest {


    val transaction = PayUserTransaction(
        "1111111111111111",
        768,
        79.9,
        "01/18",
        1001
    )

    val response = TransactionResponse("")

    @Test
    fun test_pay_user_usecase_completed(){

        val repository = mock<PayUserRepository>{
            on { payUser(transaction) } doReturn Observable.just(response)
        }

        val useCase = PayUserUseCase(repository)

        useCase.payUser(transaction)
            .test()
            .assertComplete()
    }

    @Test
    fun test_pay_user_usecase_error() {

        val response = Throwable("Error response")

        val repository = mock<PayUserRepository>{
            on { payUser(transaction) } doReturn Observable.error(response)
        }

        val useCase = PayUserUseCase(repository)

        useCase.payUser(transaction)
            .test()
            .assertError(response)

    }

    @Test
    fun test_pay_user_usecase_response() {

        val repository = mock<PayUserRepository>{
            on { payUser(transaction) } doReturn Observable.just(response)
        }

        val useCase = PayUserUseCase(repository)

        useCase.payUser(transaction)
            .test()
            .assertValue(response)

    }
}