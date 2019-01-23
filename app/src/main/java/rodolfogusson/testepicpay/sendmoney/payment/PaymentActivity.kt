package rodolfogusson.testepicpay.sendmoney.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.ui.customize
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard

class PaymentActivity : AppCompatActivity() {

    private lateinit var contact: Contact
    private lateinit var creditCard: CreditCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        contact = intent.getParcelableExtra(Contact.key)
        creditCard = intent.getParcelableExtra(CreditCard.key)

        setupLayout()
    }

    private fun setupLayout() {
        supportActionBar?.customize()
    }
}
