package com.michaeljordan.testemobilepicpay.ui.view

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.michaeljordan.testemobilepicpay.R
import com.michaeljordan.testemobilepicpay.databinding.ActivityCardPrimingBinding
import com.michaeljordan.testemobilepicpay.model.Contact
import com.michaeljordan.testemobilepicpay.util.Constants

class CardPrimingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardPrimingBinding
    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_priming)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_card_priming)

        contact = intent.getParcelableExtra(Constants.CONTACT_PARAM)

        binding.btOpenCardRegister.setOnClickListener {
            openCardRegister()
        }

        binding.toolbarCardPriming.ibBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun openCardRegister() {
        val intent = Intent(this, CardRegisterActivity::class.java)
        intent.putExtra(Constants.CONTACT_PARAM, contact)
        startActivity(intent)
    }

}