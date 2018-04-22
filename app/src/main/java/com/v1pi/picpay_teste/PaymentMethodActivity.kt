package com.v1pi.picpay_teste

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.v1pi.picpay_teste.Controllers.PaymentMethodController
import android.support.v4.app.NavUtils
import android.util.Log
import android.view.MenuItem


class PaymentMethodActivity : AppCompatActivity() {
    val controller = PaymentMethodController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        controller.init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}