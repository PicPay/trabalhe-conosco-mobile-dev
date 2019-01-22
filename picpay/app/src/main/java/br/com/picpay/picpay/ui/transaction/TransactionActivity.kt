package br.com.picpay.picpay.ui.transaction

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import br.com.picpay.picpay.R
import br.com.picpay.picpay.base.BaseActivity
import br.com.picpay.picpay.db.CreditCard
import br.com.picpay.picpay.model.User
import br.com.picpay.picpay.utils.Constants.Companion.USER
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity: BaseActivity<TransactionViewModel>() {

    private var value: Float = 0F
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

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

        viewModel?.loadingVisibility?.observe(this, Observer { visibility ->
            if (visibility != null) showLoading(visibility)
        })
    }

    private fun listenButtons() {
        transaction_edit.setOnClickListener {
            viewModel?.setActivityRegister(this)
        }

        transaction_pay_button.setOnClickListener {
            viewModel?.startTransaction(value, user.id)
        }
    }

    private fun behaviorField() {
        transaction_value.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                //Logic to organize the value field
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(transaction_value.text.isNotEmpty()) {
                    transaction_currency.setTextColor(ContextCompat.getColor(this@TransactionActivity, R.color.colorAccent))
                    transaction_value.setTextColor(ContextCompat.getColor(this@TransactionActivity, R.color.colorAccent))
                    transaction_pay_button.isEnabled = true
                } else {
                    transaction_currency.setTextColor(ContextCompat.getColor(this@TransactionActivity, R.color.color_white))
                    transaction_value.setTextColor(ContextCompat.getColor(this@TransactionActivity, R.color.color_white))
                    transaction_pay_button.isEnabled = false
                }
            }
        })
    }

    private fun setUserData() {
        transaction_username.text = user.username
        Glide.with(this)
            .load(user.img)
            .into(transaction_image)
    }

    private fun setCreditCardInfo(cardNumber: String) {
        val company = setCardCompany(cardNumber)
        val lastNumbers = cardNumber.substring(12)
        transaction_card.text = getString(R.string.transaction_card, company, lastNumbers)
    }

    private fun setCardCompany(cardNumber: String): String {
        //logic to set the card company
        return "Mastercard"
    }

    private fun showLoading(visibility: Int) {
        transaction_loading_screen.visibility = visibility
    }

    private fun setErrorResult(error: String?) {
        if (error != null) Snackbar.make(constraint_transaction, error, Snackbar.LENGTH_LONG).show()
        else Snackbar.make(constraint_transaction, getString(R.string.default_response), Snackbar.LENGTH_LONG).show()
    }

    override fun containerViewModel(): TransactionViewModel? {
        return ViewModelProviders
            .of(this)
            .get(TransactionViewModel::class.java)
    }
}