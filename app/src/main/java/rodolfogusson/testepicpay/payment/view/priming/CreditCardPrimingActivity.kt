package rodolfogusson.testepicpay.payment.view.priming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.payment.model.contact.Contact

class CreditCardPrimingActivity : AppCompatActivity() {

    var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card_priming)
        contact = intent.getParcelableExtra(Contact.key)
        setupLayout()
    }

    private fun setupLayout() {
        supportActionBar?.let {
            with(it){
                title = ""
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.green_back_arrow)
            }
        }
    }
}
