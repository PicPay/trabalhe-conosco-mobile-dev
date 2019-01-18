package rodolfogusson.testepicpay.payment.viewmodel.register

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import rodolfogusson.testepicpay.BR


class CreditCardRegisterViewModel : BaseObservable() {

    var cardNumberText: String = ""

    @Bindable
    fun getCardNumber(): String {
        return cardNumberText
    }

    fun setCardNumber(value: String) {
        // Avoids infinite loops.
        if (cardNumberText != value) {
            cardNumberText = value

            // Notify observers of a new value.
            notifyPropertyChanged(BR.cardNumber)
        }
    }

}
