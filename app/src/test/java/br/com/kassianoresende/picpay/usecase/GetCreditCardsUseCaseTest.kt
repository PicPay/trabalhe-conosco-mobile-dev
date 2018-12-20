package br.com.kassianoresende.picpay.usecase

import br.com.kassianoresende.picpay.model.CreditCard
import br.com.kassianoresende.picpay.repository.CreditCardRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import org.junit.Test


class GetCreditCardsUseCaseTest {

    val creditCardMock =  CreditCard(1,
        "1111111111111111","Mastercard",
        "Kassiano Resende","01/18", 768)

    @Test
    fun test_get_credit_cards_usecase_completed() {

        val repository = mock<CreditCardRepository>{
            on { getCards() } doReturn Observable.just(listOf(creditCardMock))
        }

        val useCase = GetCreditCardsUseCase(repository)

        useCase.getCreditCards()
            .test()
            .assertComplete()
    }

    @Test
    fun test_get_credit_cards_usecase_error() {

        val response = Throwable("Error response")

        val repository = mock<CreditCardRepository>{
            on { getCards() } doReturn Observable.error(response)
        }

        val useCase = GetCreditCardsUseCase(repository)

        useCase.getCreditCards()
            .test()
            .assertError(response)

    }

    @Test
    fun test_get_category_usecase_response() {

        val response = listOf(creditCardMock)

        val repository = mock<CreditCardRepository>{
            on { getCards() } doReturn Observable.just(response)
        }

        val useCase = GetCreditCardsUseCase(repository)

        useCase.getCreditCards()
            .test()
            .assertValue(response)

    }

}