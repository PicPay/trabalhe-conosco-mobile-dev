package test.edney.picpay.view.payment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import org.json.JSONObject
import test.edney.picpay.App
import test.edney.picpay.R
import test.edney.picpay.databinding.ActivityPaymentBinding
import test.edney.picpay.model.UserModel
import test.edney.picpay.util.AppUtil
import test.edney.picpay.util.ExtrasName
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
      private val mGson = Gson()

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

                        binding.progress.visibility = View.GONE
                        intent.putExtra(ExtrasName.transaction, mGson.toJson(it))
                        intent.putExtra(
                              ExtrasName.card_number,
                              viewmodel.cardSave.value?.number.toString()
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                  } else {
                        loading.value = false
                        log.showD("paymentResponse", "falha")
                  }
            })
      }

      private fun ui() {
            binding.ui = object : PaymentUI {
                  override fun actionBack() {
                        finish()
                  }

                  override fun actionEditCard() {
                        if (userJson != null) {
                              val intent = Intent(this@PaymentActivity, CardActivity::class.java).apply {
                                    putExtra(ExtrasName.user, userJson)
                              }
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
                                          viewmodel.requestPayment(AppUtil.getPaymentValue(binding.edValue.text.toString()), userId)
                                    }, requestDelay)
                              } else
                                    log.showD("actionPay", "falha JSON => " + userJsonO.toString())
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

                        binding.edValue.removeTextChangedListener(this)
                        if(text != null ){
                              val formated = AppUtil.formatCurrency(text)

                              hasValue.value = AppUtil.formartCurrencyNumber(text) > 0
                              binding.edValue.setText(formated)
                              binding.edValue.setSelection(formated.length)
                        }
                        else
                              hasValue.value = false

                        binding.edValue.addTextChangedListener(this)
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
            userJson = intent.getStringExtra(ExtrasName.user)

            if (userJson != null) {
                  val userM = mGson.fromJson(userJson, UserModel::class.java)

                  if (userM != null)
                        binding.user = userM
            }
      }

      private fun findColor(id: Int): Int { return ContextCompat.getColor(this, id) }
}