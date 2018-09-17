package com.picpay.david.davidrockpicpay.features.sendMoney

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.google.gson.Gson
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.entities.CreditCard
import com.picpay.david.davidrockpicpay.models.TransactionResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_receipt.*
import java.text.SimpleDateFormat
import java.util.*

class ReceiptActivity : AppCompatActivity() {

    var transaction: TransactionResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        buildView()

    }

    private fun buildView() {

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar) // Setting/replace toolbar as the ActionBar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            // back button pressed
            finish()
        }

        if (intent.hasExtra("receipt")) {
            transaction = Gson().fromJson(intent.getStringExtra("receipt"), TransactionResponse::class.java)
            tvId.text = transaction!!.Transaction!!.Id.toString()
            tvValorValue.text = transaction!!.Transaction!!.Value.toString()
            tvCartaoValue.text = "**** ***** **** " + CreditCard().getDefaultCard()!!.CardNumber!!.takeLast(4)

            var date = Date(transaction!!.Transaction!!.Timestamp * 1000)
            val writeFormat = SimpleDateFormat("dd/MM/yy HH:mm", Locale.US)
            tvDataValue.text = writeFormat.format(date)
            tvTransactionId.text = transaction!!.Transaction!!.Id.toString()
            tvValorValue.text = transaction!!.Transaction!!.Value.toString()

            tvName.text = transaction!!.Transaction!!.DestinationUser!!.Name
            tvUserName.text = transaction!!.Transaction!!.DestinationUser!!.Username
            tvId.text = transaction!!.Transaction!!.DestinationUser!!.Id.toString()
            Picasso.get().load(transaction!!.Transaction!!.DestinationUser!!.Img).into(userImage)

        } else {

        }
    }
}
