package rodolfogusson.testepicpay.sendmoney.viewmodel.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard

class CardRegisterViewModelFactory(private val application: Application, private val creditCard: CreditCard?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CardRegisterViewModel(application, creditCard) as T
    }
}