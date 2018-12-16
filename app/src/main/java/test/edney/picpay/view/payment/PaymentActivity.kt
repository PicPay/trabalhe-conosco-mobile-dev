package test.edney.picpay.view.payment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
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

class PaymentActivity : AppCompatActivity() {

      private val log = MyLog(PaymentActivity::class.java.simpleName)
      private lateinit var binding: ActivityPaymentBinding
      private lateinit var viewmodel: PaymentVM
      private val hasValue = MutableLiveData<Boolean>()
      private var userJson: String? = null
      private val loading = MutableLiveData<Boolean>()
      private val requestDelay = 1000L

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
            loadArguments()
            viewmodel()
            ui()

            observeValue()
            observeLoading()
      }

      private fun viewmodel() {
            viewmodel = ViewModelProviders.of(this).get(PaymentVM::class.java)

            viewmodel.cardSave.observe(this, Observer {
                  if (it?.number != null){
                        val cardNumber = it.number

                        if(cardNumber != null) {
                              val toShow = "Master ${cardNumber.substring(0, 4)} •"
                              binding.tvCardNumber.text = toShow
                        }
                  }
            })

            viewmodel.paymentResponse.observe(this, Observer {
                  if (it != null) {
                        val intent = Intent(this@PaymentActivity, HomeActivity::class.java)
                        val gson = Gson()

                        binding.progress.visibility = View.GONE
                        intent.putExtra("transaction", gson.toJson(it))
                        startActivity(intent)
                  } else {
                        loading.value = false
                        Log.d("Payment", "falha")
                  }
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

                                    loading.value = true
                                    Handler().postDelayed({
                                          viewmodel.requestPayment(getPaymentValue(), userId)
                                    }, requestDelay)
                              } else
                                    Log.d("Payment", "falha JSON => " + userJsonO.toString())
                        }
                  }
            }
      }

      private fun observeLoading(){
            loading.value = false
            loading.observe(this, Observer {
                  if(it != null){
                        if(it) {
                              binding.progress.visibility = View.VISIBLE
                              binding.btPay.visibility = View.GONE
                        }
                        else{
                              binding.btPay.visibility = View.VISIBLE
                              binding.progress.visibility = View.GONE
                        }
                  }
            })
      }

      private fun observeValue() {
            binding.edValue.addTextChangedListener(object : TextWatcher{
                  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                  override fun afterTextChanged(s: Editable?) {
                        val text = s?.toString()

                        if(text != null ){
                              val noPeriod = text.replace(".","")
                              val noComma = noPeriod.replace(",", "")
                              val clean = noComma.toInt().toString()
                              var formated = if(clean.length == 1) "0,0" else if(clean.length == 2) "0" else ""

                              hasValue.value = noComma.toInt() > 0
                              binding.edValue.removeTextChangedListener(this)
                              for (i in 0 until clean.length){
                                    if((clean.length - 2) == i)
                                          formated += ","

                                    formated += clean[i]

                                    if((clean.length - 6) == i)
                                          formated += "."
                              }

                              binding.edValue.setText(formated)
                              binding.edValue.setSelection(formated.length)
                              binding.edValue.addTextChangedListener(this)
                        }
                        else
                              hasValue.value = false
                  }
            })
            hasValue.value = false
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
            val noPeriod = text.replace(".", "")
            val replaced = noPeriod.replace(",", ".")

            return replaced.toDouble()
      }

      private fun findColor(id: Int): Int { return ContextCompat.getColor(this, id) }
}