package br.com.kassianoresende.picpay.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.kassianoresende.picpay.R
import br.com.kassianoresende.picpay.util.CkeckoutUserPrefs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.content_checkout.*

class CheckoutActivity : AppCompatActivity() {

    companion object {
        const val CREDIT_CARD_ID = "CREDIT_CARD_ID"
        const val CREDIT_CARD_NUMBER = "CREDIT_CARD_NUMBER"
        const val CREDIT_CARD_CVV = "CREDIT_CARD_CVV"
        const val CREDIT_CARD_DUE_DATE = "CREDIT_CARD_DUE_DATE"
        const val CREDIT_CARD_FLAG = "CREDIT_CARD_FLAG"
    }


    val userPrefs by lazy {
        CkeckoutUserPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        updateUI(intent)


        btPayUser.setOnClickListener {

        }
    }


    fun updateUI(intent: Intent){

        val cardNumber  = intent.getStringExtra(CREDIT_CARD_NUMBER)
        val cardFlag  = intent.getStringExtra(CREDIT_CARD_FLAG)

        Glide.with(this)
            .load(userPrefs.userImage)
            .apply(RequestOptions.circleCropTransform())
            .into(ivUserImg)

        tvUserName.text = userPrefs.userName
        tvCardFlag.text = cardFlag
        tvCardNumber.text = cardNumber

    }

}
