package br.com.kassianoresende.picpay.usecase

import br.com.kassianoresende.picpay.model.CreditCard
import br.com.kassianoresende.picpay.repository.CreditCardRepository
import javax.inject.Inject

class SaveCreditCardUseCase @Inject constructor(val respository: CreditCardRepository) {

    fun saveCreditCard(card:CreditCard){
        respository.saveCard(card)
    }
}