package rodolfogusson.testepicpay.payment.viewmodel.register

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText


class CardRegisterViewModel(
    val cardNumberId: Int,
    val cardholderNameId: Int,
    val expiryDateId: Int,
    val cvvId: Int
) : ViewModel() {

    val cardNumber = MutableLiveData<String>()

    fun onFocusChange(view: View, hasFocus: Boolean) {
        if (view !is TextInputEditText || hasFocus) return
        print(cardNumber.value)
//        when (view.id) {
//            cardNumberId -> validateCardNumber(view)
//            cardholderNameId -> validateCardholderName(view)
//            expiryDateId -> validateExpiryDate(view)
//            cvvId -> validateCvv(view)
//        }
    }

    //cardNumber.value = "OLA"
}