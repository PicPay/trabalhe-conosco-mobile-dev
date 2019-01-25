package br.com.picpay.picpay.ui.transaction

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import br.com.picpay.picpay.base.BaseActivity
import br.com.picpay.picpay.model.User
import br.com.picpay.picpay.utils.Constants.Companion.USER
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_transaction.*
import java.text.NumberFormat
import java.util.*



class TransactionActivity: BaseActivity<TransactionViewModel>() {

    private var value: String = ""
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(br.com.picpay.picpay.R.layout.activity_transaction)
        supportActionBar?.elevation = 0F
        val bundle = intent?.extras
        bundle?.let {
            user = it.getParcelable(USER)!!
        }

        setUserData()
        listenLiveData()
        listenButtons()
        behaviorField()
    }

    override fun onResume() {
        super.onResume()
        viewModel?.loadCreditCard()
    }

    private fun listenLiveData() {
        viewModel?.cardNumber?.observe(this, Observer { cardNumber ->
            if (cardNumber != null) setCreditCardInfo(cardNumber)
        })

        viewModel?.responseTransaction?.observe(this, Observer { response ->
            if (response != null) viewModel?.setActivityContact(this)
            else setErrorResult(null)
        })

        viewModel?.error?.observe(this, Observer { error->
            setErrorResult(error)
        })
    }

    private fun listenButtons() {
        transaction_edit.setOnClickListener {
            viewModel?.setActivityRegister(this)
        }

        transaction_pay_button.setOnClickListener {
            var temp = value.replace(".", "")
            temp = value.replace(",",".")
            viewModel?.startTransaction(temp.toFloat(), user.id)
        }
    }

    private fun behaviorField() {
        transaction_value.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != value) {
                    transaction_value.removeTextChangedListener(this)

                    val cleanString = s.toString().replace("[R$,.]".toRegex(), "")

                    val parsed = java.lang.Double.parseDouble(cleanString)
                    val formatted = NumberFormat
                        .getCurrencyInstance(Locale("pt", "BR"))
                        .format(parsed / 100)

                    value = formatted.replace("[R$]".toRegex(), "")
                    transaction_value.setText(value)
                    transaction_value.setSelection(value.length)
                    transaction_value.addTextChangedListener(this)

                    if(value != "0,00") {
                        transaction_currency.setTextColor(ContextCompat.getColor(this@TransactionActivity, br.com.picpay.picpay.R.color.colorAccent))
                        transaction_value.setTextColor(ContextCompat.getColor(this@TransactionActivity, br.com.picpay.picpay.R.color.colorAccent))
                        transaction_pay_button.isEnabled = true
                    } else {
                        transaction_currency.setTextColor(ContextCompat.getColor(this@TransactionActivity, br.com.picpay.picpay.R.color.color_white))
                        transaction_value.setTextColor(ContextCompat.getColor(this@TransactionActivity, br.com.picpay.picpay.R.color.color_white))
                        transaction_pay_button.isEnabled = false
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setUserData() {
        transaction_username.text = user.username
        Glide.with(this)
            .load(user.img)
            .into(transaction_image)
    }

    private fun setCreditCardInfo(cardNumber: String) {
        val company = viewModel?.setCardCompany(cardNumber)
        val lastNumbers = cardNumber.substring(12)
        transaction_card.text = getString(br.com.picpay.picpay.R.string.transaction_card, company, lastNumbers)
    }

    private fun setErrorResult(error: String?) {
        if (error != null) Snackbar.make(constraint_transaction, error, Snackbar.LENGTH_LONG).show()
        else Snackbar.make(constraint_transaction, getString(br.com.picpay.picpay.R.string.default_response), Snackbar.LENGTH_LONG).show()
    }

    override fun containerViewModel(): TransactionViewModel? {
        return ViewModelProviders
            .of(this)
            .get(TransactionViewModel::class.java)
    }
}