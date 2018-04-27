package com.v1pi.picpay_teste

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import com.v1pi.picpay_teste.Controllers.CreateCreditCardController
import com.v1pi.picpay_teste.Domains.CreditCard
import kotlinx.android.synthetic.main.activity_create_credit_card.*

class CreateCreditCardActivity : AppCompatActivity() {
    lateinit var controller : CreateCreditCardController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_credit_card)

        val arrayAdapter = ArrayAdapter.createFromResource(this, R.array.credit_card_flags, R.layout.spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner_credit_card_flag.adapter = arrayAdapter

        controller = CreateCreditCardController(this)
    }

    fun register(view : View) {
        // Não vi necessidade de adicionar os campos diferentes desses no modelo do Cartão de Crédito já que não são utilizados
        val creditCard = CreditCard(0, edit_number.text.toString(), edit_cvv.text.toString().toInt(), edit_expiry_date.text.toString())
        controller.insertCreditCard(creditCard)
    }

}