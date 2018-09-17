package com.picpay.david.davidrockpicpay.features.creditCard

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ArrayAdapter
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.entities.CreditCard
import com.picpay.david.davidrockpicpay.extensions.MaskEditUtil
import com.picpay.david.davidrockpicpay.features.base.BaseActivity
import com.picpay.david.davidrockpicpay.util.UiUtil
import kotlinx.android.synthetic.main.activity_new_credit_card.*


class NewCreditCardActivity : BaseActivity(), NewCreditCardMvpView {

    private var presenter = NewCreditCardPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_credit_card)
        presenter.attachView(this)

        configView()
        setEditMasks()

    }

    fun configView() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar) // Setting/replace toolbar as the ActionBar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            // back button pressed
            finish()
        }

        val items = arrayOf("Visa", "MasterCard", "Amex", "Diners", "Elo", "Bradescard")
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items)
        spinner.setAdapter<ArrayAdapter<String>?>(arrayAdapter)

        btnCreateNewCreditCard.setOnClickListener {
            if(validateFields())
            presenter.addCreditCard(edCardHolder.text.toString(), edCardNumber.text.toString(), edCardCsc.text.toString(), edCardValidity.text.toString(), edCep.text.toString())
        }



    }

    private fun validateFields(): Boolean {
        UiUtil.Validates.textViewEmpty(edCardHolder, getString(R.string.newcard_cardholder))
        UiUtil.Validates.textViewEmpty(edCardNumber, getString(R.string.newcard_cardnumber))
        UiUtil.Validates.textViewEmpty(edCardCsc, getString(R.string.newcard_csc))
        UiUtil.Validates.textViewEmpty(edCep, getString(R.string.newcard_cep))
        UiUtil.Validates.textViewEmpty(edCardValidity, getString(R.string.newcard_cardvalidity))
        UiUtil.Validates.textViewMinLength(edCardCsc, 3, getString(R.string.newcard_csc))
        UiUtil.Validates.textViewMinLength(edCardNumber, 3, getString(R.string.newcard_cardnumber))

        return (edCardHolder.error.isNullOrEmpty()
                && edCardNumber.error.isNullOrEmpty()
                && edCardCsc.error.isNullOrEmpty()
                && edCardValidity.error.isNullOrEmpty()
                && edCep.error.isNullOrEmpty())
    }

    override fun showErrorDialog(message: String?) {
        UiUtil.Dialogs.dialogAlertAction(this, message, null, false, true)
    }

    override fun showSuccessDialog(message: String) {
        UiUtil.Dialogs.dialogAlertAction(this, message,
                DialogInterface.OnClickListener { _, _ ->
                    run {
                        Handler().postDelayed({
                            finish()
                        }, 1000)

                    }
                }, false, true)
    }

    fun setEditMasks(){
        edCep.addTextChangedListener(MaskEditUtil.mask(edCep, MaskEditUtil.FORMAT_CEP))
        edCardValidity.addTextChangedListener(MaskEditUtil.mask(edCardValidity, MaskEditUtil.FORMAT_DATE))
        edCardCsc.addTextChangedListener(MaskEditUtil.mask(edCardCsc, MaskEditUtil.FORMAT_CC_CSC))
        edCardNumber.addTextChangedListener(MaskEditUtil.mask(edCardNumber, MaskEditUtil.FORMAT_CREDIT_CARD))
    }


}
