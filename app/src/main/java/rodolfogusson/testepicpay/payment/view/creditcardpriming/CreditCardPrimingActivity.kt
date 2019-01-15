package rodolfogusson.testepicpay.payment.view.creditcardpriming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rodolfogusson.testepicpay.R

class CreditCardPrimingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card_priming)
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
