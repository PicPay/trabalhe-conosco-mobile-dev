package br.com.kassianoresende.picpay.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.kassianoresende.picpay.R
import kotlinx.android.synthetic.main.activity_credit_card.*

class CreditCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

}
