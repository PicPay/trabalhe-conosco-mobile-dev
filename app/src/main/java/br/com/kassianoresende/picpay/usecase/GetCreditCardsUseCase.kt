package br.com.kassianoresende.picpay.usecase

import br.com.kassianoresende.picpay.repository.CreditCardRepository
import javax.inject.Inject

class GetCreditCardsUseCase @Inject  constructor(val repository: CreditCardRepository) {

    fun getCreditCards() =
            repository.getCards()

}