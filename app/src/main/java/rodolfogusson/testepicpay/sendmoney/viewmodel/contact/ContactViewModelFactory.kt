package rodolfogusson.testepicpay.sendmoney.viewmodel.contact

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rodolfogusson.testepicpay.sendmoney.model.payment.Transaction

class ContactViewModelFactory(
    private val application: Application,
    private val transaction: Transaction?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactListViewModel(application, transaction) as T
    }
}