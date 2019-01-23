package rodolfogusson.testepicpay.sendmoney.view.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_payment.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.ui.customize
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendmoney.view.register.CardRegisterActivity

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
        editButton.setOnClickListener {
            Intent(this, CardRegisterActivity::class.java).apply {
                putExtra(Contact.key, contact)
                putExtra(CreditCard.key, creditCard)
                startActivity(this)
            }
        }
    }
}
