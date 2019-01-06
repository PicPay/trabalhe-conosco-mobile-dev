package com.michaeljordan.testemobilepicpay.ui.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.michaeljordan.testemobilepicpay.R
import com.michaeljordan.testemobilepicpay.databinding.ActivityMainBinding
import com.michaeljordan.testemobilepicpay.model.Card
import com.michaeljordan.testemobilepicpay.model.Contact
import com.michaeljordan.testemobilepicpay.ui.adapter.ContactAdapter
import com.michaeljordan.testemobilepicpay.util.Constants
import com.michaeljordan.testemobilepicpay.viewmodel.CardViewModel
import com.michaeljordan.testemobilepicpay.viewmodel.ContactViewModel

class MainActivity : AppCompatActivity(), ContactAdapter.ContactAdapterOnClickListener {
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ContactAdapter
    private lateinit var cardViewModel: CardViewModel
    private var card: Card? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        cardViewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)

        binding.rvContacts.layoutManager = LinearLayoutManager(this)
        adapter = ContactAdapter(applicationContext, this)
        binding.rvContacts.adapter = adapter

        subscribe()
        contactViewModel.getContacts()
    }

    private fun subscribe() {
        contactViewModel.getContactsObservable().observe(this, Observer {
            if (it != null) {
                adapter.setData(it)
            }
        })

        cardViewModel.getCard().observe(this, Observer { card = it })
    }

    override fun onClick(contact: Contact) {
        if (card == null) {
            openCardPriming(contact)
        } else {
            openPayment(contact)
        }

    }

    private fun openCardPriming(contact: Contact) {
        val intent = Intent(this, CardPrimingActivity::class.java)
        intent.putExtra(Constants.CONTACT_PARAM, contact)
        startActivity(intent)
    }

    private fun openPayment(contact: Contact) {
        val intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra(Constants.CONTACT_PARAM, contact)
        startActivity(intent)
    }
}
