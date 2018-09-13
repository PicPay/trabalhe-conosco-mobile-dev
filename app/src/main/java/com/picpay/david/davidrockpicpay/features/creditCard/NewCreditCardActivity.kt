package com.picpay.david.davidrockpicpay.features.creditCard

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ArrayAdapter
import com.picpay.david.davidrockpicpay.R
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

            presenter.addCreditCard(edCardHolder.text.toString(), edCardNumber.text.toString(), edCardCsc.text.toString(), edCardValidity.text.toString(), edCep.text.toString())
        }
    }

    override fun showErrorDialog(message: String?) {
        UiUtil.Dialogs.dialogAlertAction(this, message, null, false, true)
    }

    override fun showSuccessDialog(message: String) {
        UiUtil.Dialogs.dialogAlertAction(this, message,
                DialogInterface.OnClickListener { _, _ ->
                    run {
                        finish()
                    }
                }, false, true)
    }


}
