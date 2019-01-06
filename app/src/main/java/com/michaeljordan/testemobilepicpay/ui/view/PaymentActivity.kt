package com.michaeljordan.testemobilepicpay.ui.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.michaeljordan.testemobilepicpay.R
import com.michaeljordan.testemobilepicpay.databinding.ActivityPaymentBinding
import com.michaeljordan.testemobilepicpay.model.Card
import com.michaeljordan.testemobilepicpay.model.Contact
import com.michaeljordan.testemobilepicpay.model.TransactionRequest
import com.michaeljordan.testemobilepicpay.util.Constants
import com.michaeljordan.testemobilepicpay.viewmodel.CardViewModel
import com.michaeljordan.testemobilepicpay.viewmodel.TransactionViewModel
import com.squareup.picasso.Picasso

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var cardViewModel: CardViewModel
    private lateinit var contact: Contact
    private var card: Card? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel::class.java)
        cardViewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)
        contact = intent.getParcelableExtra(Constants.CONTACT_PARAM)

        setupView()
        subscribe()
    }

    private fun subscribe() {
        transactionViewModel.getTransactionObservable().observe(this, Observer {
            if (it != null) {
                if (it.transaction.success) {
                    Toast.makeText(this, "sucesso", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "erro", Toast.LENGTH_SHORT).show()
                }
            }
        })

        cardViewModel.getCard().observe(this, Observer {
            if (it != null) {
                card = it
                binding.btPayPayment.isEnabled = true
                binding.tvCardInfoPayment.text = "Mastercard " + it.number.subSequence(12, 16) + " • "
            }
        })
    }

    private fun setupView() {
        binding.tvUsernamePayment.text = contact.username
        Picasso.with(baseContext)
            .load(contact.image)
            //.error(R.drawable.ic_no_poster)
            .into(binding.ivContactPayment)

        binding.btPayPayment.setOnClickListener { pay() }

        binding.btCardInfoEdit.setOnClickListener { openCardRegister() }
    }

    private fun openCardRegister() {
        val intent = Intent(this, CardRegisterActivity::class.java)
        startActivity(intent)
    }

    private fun pay() {
        val value = binding.edValuePayment.text.toString().toDouble()
        val transactionRequest = TransactionRequest(card!!.number, card!!.cvv, card!!.expiryDate, value, contact.id)
        transactionViewModel.doTransaction(transactionRequest)
    }
}