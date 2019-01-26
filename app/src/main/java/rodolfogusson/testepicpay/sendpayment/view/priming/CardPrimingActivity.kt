package rodolfogusson.testepicpay.sendpayment.view.priming

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_card_priming.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.utils.customize
import rodolfogusson.testepicpay.sendpayment.model.contact.Contact
import rodolfogusson.testepicpay.sendpayment.view.register.CardRegisterActivity

class CardPrimingActivity : AppCompatActivity() {

    var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_priming)
        contact = intent.getParcelableExtra(Contact.key)
        setupLayout()
    }

    private fun setupLayout() {
        supportActionBar?.customize()
        button.setOnClickListener(::registerCard)
    }

    private fun registerCard(v: View) {
        Intent(this, CardRegisterActivity::class.java).apply {
            putExtra(Contact.key, contact)
            startActivity(this)
        }
    }
}
