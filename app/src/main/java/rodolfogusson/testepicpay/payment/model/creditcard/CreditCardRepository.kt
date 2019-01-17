package rodolfogusson.testepicpay.payment.model.creditcard

import androidx.lifecycle.LiveData
import android.content.Context
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

    fun getCreditCards(): LiveData<List<CreditCard>> = creditCards

    fun insert(card: CreditCard) {
        GlobalScope.launch {
            creditCardDao.insert(card)
        }
    }
}