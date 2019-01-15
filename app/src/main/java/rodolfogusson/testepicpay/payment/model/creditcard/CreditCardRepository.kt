package rodolfogusson.testepicpay.payment.model.creditcard

import androidx.lifecycle.LiveData
import android.content.Context
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rodolfogusson.testepicpay.core.data.CreditCardDatabase

class CreditCardRepository private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: CreditCardRepository? = null

        fun getInstance(context: Context) {
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CreditCardRepository(context).also { INSTANCE = it }
            }
        }
    }

    private val creditCardDao: CreditCardDao
    private var creditCards: LiveData<List<CreditCard>>

    init {
        val database = CreditCardDatabase.getInstance(context)
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