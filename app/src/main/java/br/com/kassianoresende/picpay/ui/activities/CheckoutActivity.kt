package br.com.kassianoresende.picpay.ui.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import br.com.kassianoresende.picpay.R
import br.com.kassianoresende.picpay.model.PayUserTransaction
import br.com.kassianoresende.picpay.model.TransactionResponse
import br.com.kassianoresende.picpay.ui.viewmodel.CheckoutViewModel
import br.com.kassianoresende.picpay.ui.viewstate.PayUserState
import br.com.kassianoresende.picpay.util.CkeckoutUserPrefs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.content_checkout.*
import java.text.NumberFormat

class CheckoutActivity : AppCompatActivity() {

    companion object {
        const val CREDIT_CARD_ID = "CREDIT_CARD_ID"
        const val CREDIT_CARD_NUMBER = "CREDIT_CARD_NUMBER"
        const val CREDIT_CARD_CVV = "CREDIT_CARD_CVV"
        const val CREDIT_CARD_DUE_DATE = "CREDIT_CARD_DUE_DATE"
        const val CREDIT_CARD_FLAG = "CREDIT_CARD_FLAG"
    }

    lateinit var cardNumber:String
    lateinit var cardFlag :String
    var cvv :Int = 0
    lateinit var dueDate :String



    val userPrefs by lazy {
        CkeckoutUserPrefs(this)
    }


    val viewmodel by lazy {
        ViewModelProviders.of(this).get(CheckoutViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getIntentValues()
        loadUI()

        btPayUser.setOnClickListener {


            val transaction = PayUserTransaction(
                    cardNumber,
                    cvv,
                    if(etValue.text.toString() !="") etValue.text.toString().toDouble() else 0.0,
                    dueDate,
                    userPrefs.userId
                )

            viewmodel.payUsers(transaction)
        }

        viewmodel.viewstate.observe(this, Observer(::updateUI))

        viewmodel.transactionResponse.observe(this, Observer(::updateFinishUI))

    }

    private fun getIntentValues() {
        cardNumber  = intent.getStringExtra(CREDIT_CARD_NUMBER)
        cardFlag  = intent.getStringExtra(CREDIT_CARD_FLAG)
        cvv  = intent.getIntExtra(CREDIT_CARD_CVV,0)
        dueDate = intent.getStringExtra(CREDIT_CARD_DUE_DATE)
    }

    fun loadUI(){

        Glide.with(this)
            .load(userPrefs.userImage)
            .apply(RequestOptions.circleCropTransform())
            .into(ivUserImg)

        tvUserName.text = userPrefs.userName
        tvCardFlag.text = cardFlag
        tvCardNumber.text = cardNumber.takeLast(4)

        etValue.requestFocus()
    }

    fun updateUI(viewstate: PayUserState? ){

        when(viewstate){
            is PayUserState.Unauthorized -> {
                Toast.makeText(this, getString(R.string.pay_unauthorized), Toast.LENGTH_SHORT).show()
            }
            is PayUserState.Approved -> {

                Toast.makeText(this, getString(R.string.pay_approved), Toast.LENGTH_SHORT).show()
                mainContainer.visibility = View.GONE
                finishContainer.visibility = View.VISIBLE
                titleFinish.visibility = View.VISIBLE

            }
            is PayUserState.PayError -> {
                Toast.makeText(this, getString(R.string.pay_user_error), Toast.LENGTH_SHORT).show()
            }
            is PayUserState.StartLoading -> {
                println("Start loading")
            }
            is PayUserState.FinishLoading -> {
                print("Finish loading")
            }
        }
    }


    fun updateFinishUI(response: TransactionResponse?){

        if(response!= null){

            tvDate.text = response.transaction.timestamp.toString()
            tvCardNumberFlag.text = "$cardFlag ${cardNumber.takeLast(4)}"
            tvTransasctionNumber.text = "${response.transaction.id}"

            NumberFormat.getCurrencyInstance().apply {
                tvValue.text = format(response.transaction.value)
                tvTotalValue.text =  format(response.transaction.value)
            }


        }
    }

}
