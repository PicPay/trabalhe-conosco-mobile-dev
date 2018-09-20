package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserCards
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.repository.UserCardsRepository

class UserCardsViewModel constructor(application: Application) : AndroidViewModel(application) {

    private var mUserCardsRepository: UserCardsRepository? = null
    private var mCards: LiveData<List<UserCards>>? = null

    init {
        mUserCardsRepository = UserCardsRepository(application)
        mCards = mUserCardsRepository!!.getCards()
    }

    fun getCards() : LiveData<List<UserCards>> {
        return mCards!!
    }

    fun insert(cards: UserCards) {
        mUserCardsRepository!!.insert(cards)
    }

}