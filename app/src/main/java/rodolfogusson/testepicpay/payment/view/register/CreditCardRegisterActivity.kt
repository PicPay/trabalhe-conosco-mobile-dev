package rodolfogusson.testepicpay.payment.view.register

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.ui.customize
import rodolfogusson.testepicpay.payment.model.contact.Contact

class CreditCardRegisterActivity : AppCompatActivity() {

    var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card_register)
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
