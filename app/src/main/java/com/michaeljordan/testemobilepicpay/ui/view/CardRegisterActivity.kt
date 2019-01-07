package com.michaeljordan.testemobilepicpay.ui.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher
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

        binding.toolbarCardRegister.ibBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupView() {
        cardViewModel.getCard().observe(this, Observer {
            if (it != null) {
                binding.card = it
                binding.executePendingBindings()
            }
        })

        binding.btSaveCardRegister.setOnClickListener { saveCard() }

        addTextWatchers()
    }

    private fun saveCard() {
        val card = Card(
            Constants.USER_ID,
            binding.edNumberCardRegister.text.toString().replace(" ", ""),
            binding.edHolderCardRegister.text.toString(),
            binding.edExpiryDateCardRegister.text.toString().replace("/", ""),
            binding.edCvvCardRegister.text.toString()
        )

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

    private fun addTextWatchers() {
        binding.edNumberCardRegister.addTextChangedListener(
            MascaraNumericaTextWatcher.Builder().paraMascara(Constants.CARD_NUMBER_MASK).build()
        )
        binding.edNumberCardRegister.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                showSaveButton(isValidFields())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.edHolderCardRegister.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                showSaveButton(isValidFields())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.edExpiryDateCardRegister.addTextChangedListener(
            MascaraNumericaTextWatcher.Builder().paraMascara(Constants.EXPIRE_DATE_MASK).build()
        )
        binding.edExpiryDateCardRegister.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                showSaveButton(isValidFields())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.edCvvCardRegister.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                showSaveButton(isValidFields())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun isValidFields(): Boolean {
        var countErrors = 0

        if (binding.edNumberCardRegister.text.toString().isEmpty()) {
            countErrors++
        }

        if (binding.edHolderCardRegister.text.toString().isEmpty()) {
            countErrors++
        }

        if (binding.edExpiryDateCardRegister.text.toString().isEmpty()) {
            countErrors++
        }

        if (binding.edCvvCardRegister.text.toString().isEmpty()) {
            countErrors++
        }

        if (binding.edNumberCardRegister.text.toString().length != 19) {
            countErrors++
        }

        if (binding.edExpiryDateCardRegister.text.toString().length != 5) {
            countErrors++
        }

        if (binding.edCvvCardRegister.text.toString().length != 3) {
            countErrors++
        }

        return countErrors == 0
    }

    private fun showSaveButton(flag: Boolean) {
        if (flag) {
            binding.btSaveCardRegister.visibility = View.VISIBLE
        } else {
            binding.btSaveCardRegister.visibility = View.GONE
        }
    }
}