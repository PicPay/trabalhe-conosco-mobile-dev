package rodolfogusson.testepicpay.sendmoney.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.ui.customize

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        setupLayout()
    }

    private fun setupLayout() {
        supportActionBar?.customize()
    }
}
