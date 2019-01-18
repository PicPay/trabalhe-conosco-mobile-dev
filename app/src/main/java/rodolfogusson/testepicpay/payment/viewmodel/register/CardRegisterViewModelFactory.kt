package rodolfogusson.testepicpay.payment.viewmodel.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CardRegisterViewModelFactory(
    val cardNumberId: Int,
    val cardholderNameId: Int,
    val expiryDateId: Int,
    val cvvId: Int
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CardRegisterViewModel(cardNumberId, cardholderNameId, expiryDateId, cvvId) as T
    }
}