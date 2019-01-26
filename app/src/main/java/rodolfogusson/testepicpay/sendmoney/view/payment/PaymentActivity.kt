package rodolfogusson.testepicpay.sendmoney.view.payment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_payment.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.network.Resource
import rodolfogusson.testepicpay.core.utils.customize
import rodolfogusson.testepicpay.core.utils.hideKeyboard
import rodolfogusson.testepicpay.core.utils.showErrorDialog
import rodolfogusson.testepicpay.databinding.ActivityPaymentBinding
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendmoney.model.payment.PaymentResponse
import rodolfogusson.testepicpay.sendmoney.model.payment.Transaction
import rodolfogusson.testepicpay.sendmoney.view.contact.ContactListActivity
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

        pinPaymentValueCaret()

        editButton.setOnClickListener {
            Intent(this, CardRegisterActivity::class.java).apply {
                putExtra(Contact.key, contact)
                putExtra(CreditCard.key, creditCard)
                startActivity(this)
            }
        }

        payButton.setOnClickListener {
            payButton.hideKeyboard()
            paymentValue.clearFocus()
            paymentProgressBar.visibility = View.VISIBLE
            observeTransaction(viewModel.makePayment())
        }
    }

    private fun pinPaymentValueCaret() {
        // Ensures that paymentValue EditText's caret will always be fixed on the right side
        paymentValue.requestFocus()
        paymentValue.setSelection(paymentValue.text.length)
        paymentValue.setOnClickListener { v ->
            val editText = v as? EditText
            editText?.setSelection(editText.text.length)
        }
    }

    private fun observeTransaction(data: LiveData<Resource<PaymentResponse>>) {
        data.observe(this, Observer {
            paymentProgressBar.visibility = View.GONE
            it?.error?.let { error ->
                showErrorDialog(error.localizedMessage, this)
                return@Observer
            }
            it?.data?.let { response ->
                if (response.transaction.success) {
                    Intent(this, ContactListActivity::class.java).apply {
                        putExtra(Transaction.key, response.transaction)
                        putExtra(CreditCard.key, creditCard)
                        startActivity(this)
                    }
                } else {
                    showErrorDialog(response.transaction.status, this)
                }
            }
        })
    }
}
