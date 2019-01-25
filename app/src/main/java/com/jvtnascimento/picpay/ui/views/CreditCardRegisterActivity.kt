package com.jvtnascimento.picpay.ui.views

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import com.jvtnascimento.picpay.R
import com.jvtnascimento.picpay.application.BaseApplication
import com.jvtnascimento.picpay.models.CreditCard
import com.jvtnascimento.picpay.presenter.CreditCardPresenter
import com.jvtnascimento.picpay.ui.contracts.CreditCardViewContractInterface
import com.jvtnascimento.picpay.ui.utils.MaskWatcher
import javax.inject.Inject

class CreditCardRegisterActivity : AppCompatActivity(), TextWatcher, CreditCardViewContractInterface {

    @BindView(R.id.creditCardNumber) lateinit var creditCardNumber: EditText
    @BindView(R.id.creditCardName) lateinit var creditCardName: EditText
    @BindView(R.id.creditCardExpirationDate) lateinit var creditCardExpirationDate: EditText
    @BindView(R.id.creditCardCvv) lateinit var creditCardCvv: EditText
    @BindView(R.id.bottomButton) lateinit var bottomButton: Button

    @Inject lateinit var presenter: CreditCardPresenter

    private var creditCard: CreditCard? = null
    private var result = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card_register)

        ButterKnife.bind(this)

        if (this.intent.hasExtra("creditCard")) {
            val extra = this.intent.getSerializableExtra("creditCard")

            if (extra != null) {
                this.creditCard = this.intent.getSerializableExtra("creditCard") as CreditCard
                this.configureView()
            }
        }

        this.configureComponents()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(editable: Editable?) {
        if (this.creditCardNumber.text.toString() != "" && this.creditCardName.text.toString() != ""
            && this.creditCardExpirationDate.text.toString() != "" && this.creditCardCvv.text.toString() != "")
            this.bottomButton.visibility = View.VISIBLE
        else {
            this.bottomButton.visibility = View.GONE
        }
    }

    override fun returnSuccess(creditCard: CreditCard) {
        this.result.putExtra("creditCard", creditCard)
        setResult(Activity.RESULT_OK, result)
        finish()
    }

    private fun configureComponents() {
        (application as BaseApplication).component.inject(this)
        this.presenter.attach(this)

        (this as AppCompatActivity).supportActionBar!!.title = ""
        (this as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (this as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        (this as AppCompatActivity).supportActionBar!!.elevation = 0f

        this.creditCardNumber.addTextChangedListener(this)
        this.creditCardName.addTextChangedListener(this)
        this.creditCardExpirationDate.addTextChangedListener(this)
        this.creditCardCvv.addTextChangedListener(this)

        this.creditCardNumber.addTextChangedListener(MaskWatcher("####-####-####-####"))
        this.creditCardExpirationDate.addTextChangedListener(MaskWatcher("##/##"))
        this.creditCardCvv.addTextChangedListener(MaskWatcher("###"))

        this.bottomButton.setOnClickListener {
            this.onBottomButtonTap()
        }
    }

    private fun configureView() {
        if (this.creditCard != null) {
            this.creditCardNumber.setText(this.creditCard!!.number)
            this.creditCardName.setText(this.creditCard!!.name)
            this.creditCardExpirationDate.setText(this.creditCard!!.expirationDate)
            this.creditCardCvv.setText(this.creditCard!!.cvv.toString())
        }
    }

    private fun onBottomButtonTap(){
        if (creditCard == null)
            this.creditCard = CreditCard()

        this.creditCard!!.number = this.creditCardNumber.text.toString()
        this.creditCard!!.name = this.creditCardName.text.toString()
        this.creditCard!!.expirationDate = this.creditCardExpirationDate.text.toString()
        this.creditCard!!.cvv = this.creditCardCvv.text.toString().toInt()

        if (this.creditCard!!.id == 0) {
            this.presenter.create(this.creditCard!!)
        } else {
            this.presenter.update(this.creditCard!!)
        }

    }
}
