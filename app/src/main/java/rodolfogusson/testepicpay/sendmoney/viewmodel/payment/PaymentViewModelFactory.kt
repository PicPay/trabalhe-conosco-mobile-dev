package rodolfogusson.testepicpay.sendmoney.viewmodel.payment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard

class PaymentViewModelFactory(
    private val application: Application,
    private val creditCard: CreditCard,
    private val contact: Contact
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PaymentViewModel(application, creditCard, contact) as T
    }
}