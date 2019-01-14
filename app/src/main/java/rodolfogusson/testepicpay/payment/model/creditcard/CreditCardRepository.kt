package rodolfogusson.testepicpay.payment.model.creditcard

import android.arch.lifecycle.LiveData
import android.content.Context
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rodolfogusson.testepicpay.core.data.CreditCardDatabase

class CreditCardRepository(context: Context) {

    private val creditCardDao: CreditCardDao
    private var creditCards: LiveData<List<CreditCard>>

    init {
        val database = CreditCardDatabase.getInstance(context)
        creditCardDao = database.creditCardDao()
        creditCards = creditCardDao.getAllCreditCards()
    }

    fun getCreditCards(completion: (LiveData<List<CreditCard>>) -> Unit) {
        completion(creditCards)
    }

    fun insert(card: CreditCard) {
        GlobalScope.launch {
            creditCardDao.insert(card)
        }
    }
}