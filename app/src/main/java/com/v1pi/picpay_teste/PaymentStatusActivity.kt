package com.v1pi.picpay_teste

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.v1pi.picpay_teste.Controllers.PaymentStatusController
import com.v1pi.picpay_teste.Domains.Transaction
import com.v1pi.picpay_teste.Utils.DownloadImageTask
import kotlinx.android.synthetic.main.activity_payment_status.*
import kotlinx.android.synthetic.main.include_user.view.*
import java.text.SimpleDateFormat
import java.util.*

class PaymentStatusActivity : AppCompatActivity() {
    lateinit var controller : PaymentStatusController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_status)

        controller = PaymentStatusController(this)
    }

    fun bindDataTransactionToView(transaction : Transaction) {
        val user = transaction.destination_user

        txt_transaction.text = transaction.id.toString()

        transaction.timestamp?.let {
            txt_date.text = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US).format(Date(it *1000))
        }

        txt_card.text = getString(R.string.card_text, transaction.card_number.substring(transaction.card_number.length-4))

        txt_value.text = getString(R.string.value_text, transaction.value.toString())

        rl_data_status.txt_id.text = getString(R.string.id, user.id.toString())
        rl_data_status.txt_name.text = user.name
        rl_data_status.txt_username.text = user.username
        DownloadImageTask(rl_data_status.user_image).execute(user.img)
    }

    fun backToMain(view : View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    fun payAgain(view : View) {
        finish()
    }

}