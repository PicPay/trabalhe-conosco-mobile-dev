package br.com.kassianoresende.picpay.usecase

import br.com.kassianoresende.picpay.model.CreditCard
import br.com.kassianoresende.picpay.repository.CreditCardRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import org.junit.Test


class SaveCreditCardUseCaseTest {

    val creditCardMock =  CreditCard(1,
        "1111111111111111","Mastercard",
        "Kassiano Resende","01/18", 768)

    val response = 1L

    @Test
    fun test_savecard_usecase_completed(){

        val repository = mock<CreditCardRepository>{
            on { saveCard(creditCardMock) } doReturn  Observable.just(response)
        }

        val useCase = SaveCreditCardUseCase(repository)

        useCase.saveCreditCard(creditCardMock)
            .test()
            .assertComplete()
    }

    @Test
    fun test_savecard_usecase_error() {

        val errorResponse = Throwable("Error response")

        val repository = mock<CreditCardRepository>{
            on { saveCard(creditCardMock) } doReturn Observable.error(errorResponse)
        }

        val useCase = SaveCreditCardUseCase(repository)

        useCase.saveCreditCard(creditCardMock)
            .test()
            .assertError(errorResponse)
    }

    @Test
    fun test_savecard_usecase_response() {

        val repository = mock<CreditCardRepository>{
            on { saveCard(creditCardMock) } doReturn Observable.just(response)
        }

        val useCase = SaveCreditCardUseCase(repository)

        useCase.saveCreditCard(creditCardMock)
            .test()
            .assertValue(response)

    }
}