package rodolfogusson.testepicpay.sendmoney.model.creditcard

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
    private val lastRegisteredCard: LiveData<CreditCard>

            init {
                val database = ApplicationDatabase.getInstance(context)
                creditCardDao = database.creditCardDao()
                creditCards = creditCardDao.getAllCreditCards()
                lastRegisteredCard = creditCardDao.getLastCardInserted()
            }

    /**
     * For the sake of simplicity, this app only uses the last card registered by the user.
     */
    fun getLastRegisteredCreditCard() = creditCardDao.getLastCardInserted()

    fun insert(card: CreditCard) {
        GlobalScope.launch {
            creditCardDao.insert(card)
        }
    }
}