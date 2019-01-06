package com.michaeljordan.testemobilepicpay.ui.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.michaeljordan.testemobilepicpay.R
import com.michaeljordan.testemobilepicpay.databinding.ActivityCardRegisterBinding
import com.michaeljordan.testemobilepicpay.model.Card
import com.michaeljordan.testemobilepicpay.model.Contact
import com.michaeljordan.testemobilepicpay.util.Constants
import com.michaeljordan.testemobilepicpay.viewmodel.CardViewModel

class CardRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardRegisterBinding
    private lateinit var cardViewModel: CardViewModel
    private var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_register)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_card_register)
        cardViewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)

        contact = intent.getParcelableExtra(Constants.CONTACT_PARAM)

        setupView()
    }

    private fun setupView() {
        cardViewModel.getCard().observe(this, Observer {
            if (it != null) {
                binding.card = it
                binding.executePendingBindings()
            }
        })

        binding.btSaveCardRegister.setOnClickListener { saveCard() }
    }

    private fun saveCard() {
        val card = Card(Constants.USER_ID,
            binding.edNumberCardRegister.text.toString(),
            binding.edHolderCardRegister.text.toString(),
            binding.edExpiryDateCardRegister.text.toString(),
            binding.edCvvCardRegister.text.toString())

        cardViewModel.saveCard(card)

        if (contact != null) {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra(Constants.CONTACT_PARAM, contact)
            intent.putExtra(Constants.CARD_PARAM, card)
            startActivity(intent)
        } else {
            finish()
        }
    }
}