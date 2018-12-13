package test.edney.picpay.view.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import org.json.JSONObject
import test.edney.picpay.R
import test.edney.picpay.databinding.ActivityPaymentBinding
import test.edney.picpay.model.UserModel
import test.edney.picpay.util.MyLog
import test.edney.picpay.view.card.CardActivity
import test.edney.picpay.view.home.HomeActivity
import test.edney.picpay.viewmodel.PaymentVM

class PaymentActivity : AppCompatActivity() {

    private val log = MyLog(PaymentActivity::class.java.simpleName)
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var viewmodel: PaymentVM
    private var userJson: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        loadArguments()
        viewmodel()
        ui()

        //hideKeyboard()
    }

    private fun viewmodel(){
        viewmodel = ViewModelProviders.of(this).get(PaymentVM::class.java)

        viewmodel.cardSave.observe(this, Observer {
            if(it != null){
                binding.tvCardNumber.text = it.number
            }
        })

        viewmodel.paymentResponse.observe(this, Observer {
            if(it != null){
                val intent = Intent(this@PaymentActivity, HomeActivity::class.java)
                val gson = Gson()

                intent.putExtra("transaction", gson.toJson(it))
                startActivity(intent)
            }
            else
                Log.d("Payment", "falha")
        })
    }

    private fun ui(){
        binding.ui = object : PaymentUI{
            override fun actionBack() {
                finish()
            }
            override fun actionEditCard() {
                val intent = Intent(this@PaymentActivity, CardActivity::class.java)

                if(userJson != null){
                    intent.putExtra("fragment_type", "register")
                    intent.putExtra("user", userJson)
                    startActivity(intent)
                }
            }

            override fun actionPay() {
                val userJsonO = JSONObject(userJson)

                if(userJsonO.has("id") && !userJsonO.isNull("id")){
                    val userId: Int = userJsonO.getInt("id")
                    viewmodel.requestPayment(binding.tvValue.text.toString().toDouble(), userId)
                }
                else
                    Log.d("Payment", "falha JSON => "+userJsonO.toString())
            }
        }
    }

    private fun loadArguments(){
        userJson = intent.getStringExtra("user")

        if(userJson != null){
            val gson = Gson()
            val userM = gson.fromJson(userJson, UserModel::class.java)

            if(userM != null)
                binding.user = userM
        }
    }

    private fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}
