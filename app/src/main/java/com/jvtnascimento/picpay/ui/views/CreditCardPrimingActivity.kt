package com.jvtnascimento.picpay.ui.views

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import com.jvtnascimento.picpay.R
import com.jvtnascimento.picpay.models.CreditCard

class CreditCardPrimingActivity : AppCompatActivity() {

    @BindView(R.id.bottomButton) lateinit var bottomButton: Button

    private val CREDIT_CARD_RESULT: Int = 1

    private var result = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card_priming)

        ButterKnife.bind(this)

        this.configureComponents()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CREDIT_CARD_RESULT) {
                if (data != null) {
                    val creditCard = data.getSerializableExtra("creditCard") as CreditCard
                    this.result.putExtra("creditCard", creditCard)
                    setResult(Activity.RESULT_OK, result)
                    finish()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun configureComponents() {
        (this as AppCompatActivity).supportActionBar!!.title = ""
        (this as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (this as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        (this as AppCompatActivity).supportActionBar!!.elevation = 0f

        this.bottomButton.setOnClickListener {
            this.onBottomButtonTap()
        }
    }

    private fun onBottomButtonTap() {
        val intent = Intent(this, CreditCardRegisterActivity::class.java)
        startActivityForResult(intent, CREDIT_CARD_RESULT)
    }
}
