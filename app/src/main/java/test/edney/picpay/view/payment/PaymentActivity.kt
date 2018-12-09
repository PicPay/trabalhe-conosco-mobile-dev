package test.edney.picpay.view.payment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil
import test.edney.picpay.R
import test.edney.picpay.databinding.ActivityPaymentBinding
import test.edney.picpay.view.home.HomeActivity

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        ui()
    }

    private fun ui(){
        binding.ui = object : PaymentUI{
            override fun actionPay() {
                val intent = Intent(this@PaymentActivity, HomeActivity::class.java)

                finish()
                intent.putExtra("receipt_name", "Edney.Oliveira")
                startActivity(intent)
            }
        }
    }
}
