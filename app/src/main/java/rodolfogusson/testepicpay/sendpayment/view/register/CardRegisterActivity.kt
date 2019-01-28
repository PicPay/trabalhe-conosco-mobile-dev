package rodolfogusson.testepicpay.sendpayment.view.register

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_card_register.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.utils.customize
import rodolfogusson.testepicpay.core.utils.mask
import rodolfogusson.testepicpay.databinding.ActivityCardRegisterBinding
import rodolfogusson.testepicpay.sendpayment.model.contact.Contact
import rodolfogusson.testepicpay.sendpayment.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendpayment.view.payment.PaymentActivity
import rodolfogusson.testepicpay.sendpayment.viewmodel.register.CardRegisterViewModel
import rodolfogusson.testepicpay.sendpayment.viewmodel.register.CardRegisterViewModelFactory

class CardRegisterActivity : AppCompatActivity() {

    private lateinit var contact: Contact
    private lateinit var viewModel: CardRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contact = intent.getParcelableExtra(Contact.key)
        val creditCard: CreditCard? = intent.getParcelableExtra(CreditCard.key)

        val binding: ActivityCardRegisterBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_card_register)
        binding.setLifecycleOwner(this)

        viewModel = ViewModelProviders
            .of(this, CardRegisterViewModelFactory(application, creditCard))
            .get(CardRegisterViewModel::class.java)

        binding.viewModel = viewModel

        setupLayout()
        registerObservers()
    }

    private fun setupLayout() {
        supportActionBar?.customize()
        cardNumber.mask("[0000] [0000] [0000] [0000]")
        expiryDate.mask("[00]/[00]")
        cvv.mask("[000]")
        saveButton.setOnClickListener { viewModel.saveCreditCard() }
    }

    private fun registerObservers() {
        observeField(viewModel.cardNumberField)
        observeField(viewModel.cardholderNameField)
        observeField(viewModel.expiryDateField)
        observeField(viewModel.cvvField)
        viewModel.saveButtonVisible.observe(this, Observer { visible ->
            if (visible) scrollToTheBottom()

        })
        viewModel.savedCreditCard.observe(this, Observer { creditCard ->
            Intent(this, PaymentActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                putExtra(Contact.key, contact)
                putExtra(CreditCard.key, creditCard)
                startActivity(this)
                finish()
            }
        })
    }

    private fun scrollToTheBottom() {
        scrollview.postDelayed({
            scrollview.scrollTo(0, scrollview.bottom)
        }, 100)
    }

    private fun observeField(field: MutableLiveData<String>) {
        field.observe(this, Observer {
            viewModel.validate(field)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
