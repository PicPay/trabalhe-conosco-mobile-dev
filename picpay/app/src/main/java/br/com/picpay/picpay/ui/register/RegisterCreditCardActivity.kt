package br.com.picpay.picpay.ui.register

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import br.com.picpay.picpay.R
import br.com.picpay.picpay.base.BaseActivity
import br.com.picpay.picpay.model.CreditCard
import kotlinx.android.synthetic.main.activity_register_creditcard.*
import br.com.picpay.picpay.utils.CreditCardFormatTextWatcher


class RegisterCreditCardActivity: BaseActivity<RegisterCreditCardViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_creditcard)

        viewModel?.loadingVisibility?.observe(this, Observer { visibility->
            if (visibility != null) {
                register_save_button.visibility = visibility
            }
        })
        viewModel?.error?.observe(this, Observer { error->
            if (error != null) {
                checkError(error)
            }
        })

        behaviorFields()
        listenButton()
    }

    private fun behaviorFields() {
        register_card_number.addTextChangedListener(CreditCardFormatTextWatcher(register_card_number))
        register_card_number.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s != null){
                    viewModel?.validationNum(s.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        register_cardholder_name.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s != null){
                    viewModel?.validationName(s.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        register_card_expiration.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s != null){
                    viewModel?.validationExp(s.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        register_card_cvv.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s != null){
                    viewModel?.validationCvv(s.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun checkError(validation: Boolean) {
        var focusView: View? = null
        var cancel = false

        if (!validation) {
            register_card_expiration.error = getString(R.string.register_error_date)
            focusView = register_card_expiration
            cancel = true
        }

        if (cancel){
            focusView?.requestFocus()
        }
    }

    private fun listenButton() {
        register_save_button.setOnClickListener {
            val creditCard = CreditCard()
            creditCard.cardNumber = register_card_number.text.toString().trim().toInt()
            creditCard.cardholderName = register_cardholder_name.text.toString()
            creditCard.expiryDate = register_card_expiration.text.toString()
            creditCard.cvv = register_card_cvv.text.toString().toInt()

            viewModel?.saveCreditCard(creditCard)
        }
    }

    override fun containerViewModel(): RegisterCreditCardViewModel? {
        return ViewModelProviders
            .of(this)
            .get(RegisterCreditCardViewModel::class.java)
    }

    override fun onBackPressed() {
        viewModel?.setContactActivity(this)
    }
}