package rodolfogusson.testepicpay.payment.viewmodel.register

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.android.material.textfield.TextInputEditText
import rodolfogusson.testepicpay.BR


class CreditCardRegisterViewModel(
    val cardNumberId: Int,
    val cardholderNameId: Int,
    val expiryDateId: Int,
    val cvvId: Int
) : BaseObservable() {

    @Bindable
    var cardNumber: String = ""
    set(value) {
        if (cardNumber != value) {
            field = value
            notifyPropertyChanged(BR.cardNumber)
        }
    }

    fun onFocusChange(view: View, hasFocus: Boolean) {
        if (view !is TextInputEditText || hasFocus) return
        when (view.id) {
//            cardNumberId -> validateCardNumber(view)
//            cardholderNameId -> validateCardholderName(view)
//            expiryDateId -> validateExpiryDate(view)
//            cvvId -> validateCvv(view)
        }
    }
}