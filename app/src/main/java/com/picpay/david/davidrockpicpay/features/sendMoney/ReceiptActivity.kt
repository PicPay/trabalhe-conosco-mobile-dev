package com.picpay.david.davidrockpicpay.features.sendMoney

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.entities.CreditCard
import com.picpay.david.davidrockpicpay.models.TransactionResponse
import kotlinx.android.synthetic.main.activity_receipt.*
import java.text.SimpleDateFormat
import java.util.*

class ReceiptActivity : AppCompatActivity() {

    var transaction : TransactionResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        buildView()

    }

    private fun buildView(){
        if (!intent.hasExtra("receipt")) {
            transaction = Gson().fromJson(intent.getStringExtra("receipt"), TransactionResponse::class.java)
            tvId.text = transaction!!.Transaction!!.Id.toString()
            tvValorValue.text = transaction!!.Transaction!!.Value.toString()
            tvCartaoValue.text = CreditCard().getDefaultCard()!!.CardNumber

            val sdf = SimpleDateFormat("dd/MM/yy HH:mm", Locale.GERMAN)
            tvDataValue.text = sdf.parse(Date(transaction!!.Transaction!!.Timestamp).toLocaleString()).toString()

        }
        else{

        }
    }
}
