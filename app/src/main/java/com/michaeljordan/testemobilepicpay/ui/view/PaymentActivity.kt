package com.michaeljordan.testemobilepicpay.ui.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher
import br.com.concrete.canarinho.watcher.ValorMonetarioWatcher
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
                    val i = Intent(this, MainActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    i.putExtra(Constants.TRANSACTION_PARAM, it)
                    i.putExtra(Constants.CARD_INFO, binding.tvCardInfoPayment.text.toString().replace("•", ""))
                    startActivity(i)
                } else {
                    Toast.makeText(
                        this,
                        baseContext.getString(R.string.error, it.transaction.status),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        cardViewModel.getCard().observe(this, Observer {
            if (it != null) {
                card = it
                binding.tvCardInfoPayment.text =
                        baseContext.getString(R.string.mastercard_title, it.number.subSequence(12, 16))
                binding.edValuePayment.setSelection(binding.edValuePayment.text.toString().length)

                binding.edValuePayment.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        validateValue()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            }
        })
    }

    private fun validateValue() {
        binding.btPayPayment.isEnabled = binding.edValuePayment.text.toString() != Constants.DEFAULT_VALUE
    }

    private fun setupView() {
        binding.tvUsernamePayment.text = contact.username
        Picasso.with(baseContext)
            .load(contact.image)
            .into(binding.ivContactPayment)

        binding.btPayPayment.setOnClickListener { pay() }

        binding.btCardInfoEdit.setOnClickListener { openCardRegister() }

        binding.toolbarPayment.ibBack.setOnClickListener {
            onBackPressed()
        }

        binding.edValuePayment.addTextChangedListener(ValorMonetarioWatcher.Builder().build())
    }

    private fun openCardRegister() {
        val intent = Intent(this, CardRegisterActivity::class.java)
        startActivity(intent)
    }

    private fun pay() {
        val valueConverted = binding.edValuePayment.text.toString().replace(".", "").replace(",", ".")
        val value = valueConverted.toDouble()
        val transactionRequest = TransactionRequest(card!!.number, card!!.cvv, card!!.expiryDate, value, contact.id)
        transactionViewModel.doTransaction(transactionRequest)
    }
}