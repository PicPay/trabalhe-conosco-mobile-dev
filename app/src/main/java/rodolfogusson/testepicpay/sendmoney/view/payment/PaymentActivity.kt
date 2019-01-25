package rodolfogusson.testepicpay.sendmoney.view.payment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_payment.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.network.ServiceProvider
import rodolfogusson.testepicpay.core.network.request
import rodolfogusson.testepicpay.core.utils.asExpiryString
import rodolfogusson.testepicpay.core.utils.customize
import rodolfogusson.testepicpay.core.utils.removeWhitespaces
import rodolfogusson.testepicpay.databinding.ActivityPaymentBinding
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendmoney.model.payment.Transaction
import rodolfogusson.testepicpay.sendmoney.view.register.CardRegisterActivity
import rodolfogusson.testepicpay.sendmoney.viewmodel.payment.PaymentViewModel
import rodolfogusson.testepicpay.sendmoney.viewmodel.payment.PaymentViewModelFactory

class PaymentActivity : AppCompatActivity() {

    private lateinit var contact: Contact
    private lateinit var creditCard: CreditCard
    private lateinit var viewModel: PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contact = intent.getParcelableExtra(Contact.key)
        creditCard = intent.getParcelableExtra(CreditCard.key)

        val binding: ActivityPaymentBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_payment)
        binding.setLifecycleOwner(this)

        viewModel = ViewModelProviders
            .of(this, PaymentViewModelFactory(application, creditCard, contact))
            .get(PaymentViewModel::class.java)

        binding.viewModel = viewModel

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
        // Ensures that paymentValue EditText's caret will always be on the right side
        paymentValue.setSelection(paymentValue.text.length)
        paymentValue.setOnClickListener { v->
            val editText = v as? EditText
            editText?.setSelection(editText.text.length)
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
