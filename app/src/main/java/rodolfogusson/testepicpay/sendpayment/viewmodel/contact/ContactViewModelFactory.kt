package rodolfogusson.testepicpay.sendpayment.viewmodel.contact

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rodolfogusson.testepicpay.sendpayment.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendpayment.model.payment.Transaction

class ContactViewModelFactory(
    private val application: Application,
    private val transaction: Transaction?,
    private val cardUsedInTransaction: CreditCard?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactListViewModel(application, transaction, cardUsedInTransaction) as T
    }
}