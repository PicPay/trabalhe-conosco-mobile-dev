package rodolfogusson.testepicpay.payment.view.register

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.ui.customize
import rodolfogusson.testepicpay.databinding.ActivityCreditCardRegisterBinding
import rodolfogusson.testepicpay.payment.model.contact.Contact
import rodolfogusson.testepicpay.payment.viewmodel.register.CreditCardRegisterViewModel

class CreditCardRegisterActivity : AppCompatActivity() {

    var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityCreditCardRegisterBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_credit_card_register)
        binding.viewModel = CreditCardRegisterViewModel()

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
