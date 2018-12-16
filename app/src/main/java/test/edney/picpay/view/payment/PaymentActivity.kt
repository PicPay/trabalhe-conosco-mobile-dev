package test.edney.picpay.view.payment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
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
import test.edney.picpay.viewmodel.PaymentVM;
import java.lang.StringBuilder

class PaymentActivity : AppCompatActivity() {

      private val log = MyLog(PaymentActivity::class.java.simpleName)
      private lateinit var binding: ActivityPaymentBinding
      private lateinit var viewmodel: PaymentVM
      private val hasValue = MutableLiveData<Boolean>()
      private var userJson: String? = null
      private var auxValue = mutableListOf<Int>()
      private var valueCount = 0

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
            loadArguments()
            viewmodel()
            ui()

            observeValue()
      }

      private fun viewmodel() {
            viewmodel = ViewModelProviders.of(this).get(PaymentVM::class.java)

            viewmodel.cardSave.observe(this, Observer {
                  if (it != null) {
                        binding.tvCardNumber.text = it.number
                  }
            })

            viewmodel.paymentResponse.observe(this, Observer {
                  if (it != null) {
                        val intent = Intent(this@PaymentActivity, HomeActivity::class.java)
                        val gson = Gson()

                        intent.putExtra("transaction", gson.toJson(it))
                        startActivity(intent)
                  } else
                        Log.d("Payment", "falha")
            })
      }

      private fun ui() {
            binding.ui = object : PaymentUI {
                  override fun actionBack() {
                        finish()
                  }

                  override fun actionEditCard() {
                        val intent = Intent(this@PaymentActivity, CardActivity::class.java)

                        if (userJson != null) {
                              intent.putExtra("fragment_type", "register")
                              intent.putExtra("user", userJson)
                              startActivity(intent)
                        }
                  }

                  override fun actionPay() {
                        val canGO = hasValue.value

                        if (canGO != null && canGO) {
                              val userJsonO = JSONObject(userJson)

                              if (userJsonO.has("id") && !userJsonO.isNull("id")) {
                                    val userId: Int = userJsonO.getInt("id")
                                    viewmodel.requestPayment(getPaymentValue(), userId)
                              } else
                                    Log.d("Payment", "falha JSON => " + userJsonO.toString())
                        }
                  }
            }
      }

      private fun observeValue() {
            hasValue.value = false
            binding.edValue.setOnKeyListener { v, keyCode, event ->
                  var result = false
                  // 0.000.000,00
                  log.showD("observeValue", "keyCode", keyCode)
                  if(keyCode in 7..16 && event.action == KeyEvent.ACTION_DOWN){
                        val number = keyCode - 7
                        val formated = StringBuilder("0,00")

                        log.showD("observeValue", "number", number)
                        result = true
                        if(auxValue.size <= 4){
                              var count = formated.length - 1

                              auxValue.add(number)
                              for(i in 0 until  auxValue.size){
                                    log.showD("observe","i", i)
                                    if(count != 2) {
                                          formated.setCharAt(count, number.toString()[0])
                                          count--
                                    }
                                    else {
                                          count--
                                          formated.setCharAt(count, number.toString()[0])
                                    }
                              }
                        }

                        log.showD("observeValue", "formated",  formated)
                        binding.edValue.setText(formated)
                  }
                  else if(keyCode == KeyEvent.KEYCODE_DEL){
                        result = true
                  }
                  true
            }
            hasValue.observe(this, Observer {
                  if (it != null) {
                        if (it) {
                              binding.tvCurrency.setTextColor(findColor(R.color.colorAccent))
                              binding.edValue.setTextColor(findColor(R.color.colorAccent))
                              binding.btPay.setBackgroundResource(R.drawable.primary_button)
                        } else {
                              binding.tvCurrency.setTextColor(findColor(R.color.colorText))
                              binding.edValue.setTextColor(findColor(R.color.colorText))
                              binding.btPay.setBackgroundResource(R.drawable.novalue_button)
                        }
                  }
            })
      }

      private fun loadArguments() {
            userJson = intent.getStringExtra("user")

            if (userJson != null) {
                  val gson = Gson()
                  val userM = gson.fromJson(userJson, UserModel::class.java)

                  if (userM != null)
                        binding.user = userM
            }
      }

      private fun getPaymentValue(): Double{
            val text = binding.edValue.text.toString()
            val replaced = text.replace(",", ".")

            return replaced.toDouble()
      }

      private fun findColor(id: Int): Int {
            return ContextCompat.getColor(this, id);
      }
}
