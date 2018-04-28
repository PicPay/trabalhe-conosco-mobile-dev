package com.v1pi.picpay_teste

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.v1pi.picpay_teste.Controllers.PaymentMethodController
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.v1pi.picpay_teste.Domains.CreditCard
import com.v1pi.picpay_teste.Domains.Transaction
import com.v1pi.picpay_teste.Domains.User
import com.v1pi.picpay_teste.Utils.DownloadImageTask
import kotlinx.android.synthetic.main.activity_payment_method.*
import kotlinx.android.synthetic.main.include_user.view.*


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
        rl_dados.txt_id.text = getString(R.string.id, user.id.toString())
        rl_dados.txt_name.text = user.name
        rl_dados.txt_username.text = user.username
        DownloadImageTask(rl_dados.user_image).execute(user.img)
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
                controller.selectedCreditCard = CreditCard(it.getIntExtra("uid", 0), it.getStringExtra("number"), it.getIntExtra("cvv", 0), it.getStringExtra("expiry_date"))
                changeCreditCardFragment(controller.createWithCreditCardFragmentWithBundle())
            }
        }
    }

    fun pay(view : View) {
        if(edit_value.text.isEmpty() || edit_value.text.toString().toFloatOrNull() == null) {
            Toast.makeText(this, getString(R.string.erroneous_message_to_pay), Toast.LENGTH_SHORT).show()
            return
        }
        val cc = controller.selectedCreditCard
        val u = controller.user
        val value = edit_value.text.toString().toFloat()

        cc?.let {
            val transaction = Transaction(cc.number, cc.cvv, value, cc.expiry_date, u)
            controller.requestToPay(transaction)
        }
    }

}