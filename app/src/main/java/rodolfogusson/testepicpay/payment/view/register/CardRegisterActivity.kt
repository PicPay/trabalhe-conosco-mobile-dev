package rodolfogusson.testepicpay.payment.view.register

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_card_register.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.ui.customize
import rodolfogusson.testepicpay.databinding.ActivityCardRegisterBinding
import rodolfogusson.testepicpay.payment.model.contact.Contact
import rodolfogusson.testepicpay.payment.viewmodel.register.CardRegisterViewModel
import rodolfogusson.testepicpay.payment.viewmodel.register.CardRegisterViewModelFactory

class CardRegisterActivity : AppCompatActivity() {

    var contact: Contact? = null
    lateinit var viewModel: CardRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityCardRegisterBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_card_register)
        binding.setLifecycleOwner(this)

        viewModel = ViewModelProviders
            .of(this, CardRegisterViewModelFactory(cardNumber.id, cardholderName.id, expiryDate.id, cvv.id))
            .get(CardRegisterViewModel::class.java)

        binding.viewModel = viewModel

        contact = intent.getParcelableExtra(Contact.key)
        setupLayout()
    }

    private fun setupLayout() {
        supportActionBar?.customize()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
