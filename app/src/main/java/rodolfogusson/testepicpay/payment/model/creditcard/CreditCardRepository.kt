package rodolfogusson.testepicpay.payment.model.creditcard

import androidx.lifecycle.LiveData
import android.content.Context
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rodolfogusson.testepicpay.core.data.CreditCardDatabase
import rodolfogusson.testepicpay.core.logic.SingletonHolder

class CreditCardRepository private constructor(context: Context) {

    companion object : SingletonHolder<CreditCardRepository, Context>(::CreditCardRepository)

    private val creditCardDao: CreditCardDao
    private var creditCards: LiveData<List<CreditCard>>

    init {
        val database = CreditCardDatabase.getInstance(context)
        creditCardDao = database.creditCardDao()
        creditCards = creditCardDao.getAllCreditCards()
        println(creditCards.value)
    }

    fun getCreditCards(): LiveData<List<CreditCard>> = creditCards

    fun insert(card: CreditCard) {
        GlobalScope.launch {
            creditCardDao.insert(card)
        }
    }
}