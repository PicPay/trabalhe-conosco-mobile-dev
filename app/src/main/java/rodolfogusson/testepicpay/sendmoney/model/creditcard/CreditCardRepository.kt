package rodolfogusson.testepicpay.sendmoney.model.creditcard

import androidx.lifecycle.LiveData
import android.content.Context
import androidx.lifecycle.Transformations
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rodolfogusson.testepicpay.core.data.ApplicationDatabase
import rodolfogusson.testepicpay.core.logic.SingletonHolder

class CreditCardRepository private constructor(context: Context) {

    companion object : SingletonHolder<CreditCardRepository, Context>(::CreditCardRepository)

    private val creditCardDao: CreditCardDao
    private val creditCards: LiveData<List<CreditCard>>

    init {
        val database = ApplicationDatabase.getInstance(context)
        creditCardDao = database.creditCardDao()
        creditCards = creditCardDao.getAllCreditCards()
    }

    /**
     * For the sake of simplicity, this app only uses the last card registered by the user.
     */
    fun lastRegisteredCreditCard() : LiveData<CreditCard> =
        Transformations.map(creditCards) { if(it.isNotEmpty()) it.last() else null }

    fun insert(card: CreditCard) {
        GlobalScope.launch {
            creditCardDao.insert(card)
        }
    }
}