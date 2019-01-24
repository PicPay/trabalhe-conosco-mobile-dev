package rodolfogusson.testepicpay.sendmoney.view.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_payment.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.network.ServiceProvider
import rodolfogusson.testepicpay.core.network.request
import rodolfogusson.testepicpay.core.ui.asExpiryString
import rodolfogusson.testepicpay.core.ui.customize
import rodolfogusson.testepicpay.core.ui.removeWhitespaces
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendmoney.model.payment.Transaction
import rodolfogusson.testepicpay.sendmoney.view.register.CardRegisterActivity
import java.time.LocalDate

class PaymentActivity : AppCompatActivity() {

    private lateinit var contact: Contact
    private lateinit var creditCard: CreditCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        contact = intent.getParcelableExtra(Contact.key)
        creditCard = intent.getParcelableExtra(CreditCard.key)

        setupLayout()
        test()
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

    fun test() {
        val t = Transaction(
            creditCard.number.removeWhitespaces(),
            123,
            15.96.toBigDecimal(),
            creditCard.expiryDate.asExpiryString(),
            1001)
        val call = ServiceProvider.sendMoneyService().sendTransaction(t)
        val ld = request(call)
    }
}
