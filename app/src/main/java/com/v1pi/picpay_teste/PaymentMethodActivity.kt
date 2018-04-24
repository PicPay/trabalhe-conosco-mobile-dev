package com.v1pi.picpay_teste

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.v1pi.picpay_teste.Controllers.PaymentMethodController
import android.view.MenuItem
import com.v1pi.picpay_teste.Domains.CreditCard
import com.v1pi.picpay_teste.Domains.User
import com.v1pi.picpay_teste.Utils.DownloadImageTask
import kotlinx.android.synthetic.main.activity_payment_method.*


class PaymentMethodActivity : AppCompatActivity() {
    lateinit var controller : PaymentMethodController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        controller = PaymentMethodController(this)
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

    fun bindDataUserToView(user : User) {
        txt_id.text = getString(R.string.id, user.id.toString())
        txt_name.text = user.name
        txt_username.text = user.username
        DownloadImageTask(user_image).execute(user.img)
    }

    fun changeCreditCardFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_credit_card, fragment)

        transaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
            data?.let {
                val creditCard = CreditCard(it.getIntExtra("uid", 0), it.getStringExtra("number"), it.getIntExtra("cvv", 0), it.getStringExtra("expiry_date"))
                changeCreditCardFragment(controller.createwithCreditCardFragmentWithBundle(creditCard))
            }
        }

    }

}