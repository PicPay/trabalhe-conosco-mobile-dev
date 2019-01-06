package com.michaeljordan.testemobilepicpay.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.michaeljordan.testemobilepicpay.model.Card
import com.michaeljordan.testemobilepicpay.repository.CardRepository

class CardViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CardRepository.getInstance(application)

    fun getCard() : LiveData<Card?> {
        return repository.getCard()
    }

    fun saveCard(card: Card) {
        repository.save(card)
    }
}