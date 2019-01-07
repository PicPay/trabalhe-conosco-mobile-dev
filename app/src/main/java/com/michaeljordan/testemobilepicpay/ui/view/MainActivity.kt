package com.michaeljordan.testemobilepicpay.ui.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import br.com.concrete.canarinho.formatador.Formatador
import com.michaeljordan.testemobilepicpay.R
import com.michaeljordan.testemobilepicpay.databinding.ActivityMainBinding
import com.michaeljordan.testemobilepicpay.model.Card
import com.michaeljordan.testemobilepicpay.model.Contact
import com.michaeljordan.testemobilepicpay.model.TransactionResponse
import com.michaeljordan.testemobilepicpay.ui.adapter.ContactAdapter
import com.michaeljordan.testemobilepicpay.util.Constants
import com.michaeljordan.testemobilepicpay.util.DateUtils
import com.michaeljordan.testemobilepicpay.viewmodel.CardViewModel
import com.michaeljordan.testemobilepicpay.viewmodel.ContactViewModel
import com.squareup.picasso.Picasso
import java.sql.Timestamp
import java.util.*


class MainActivity : AppCompatActivity(), ContactAdapter.ContactAdapterOnClickListener {
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ContactAdapter
    private lateinit var cardViewModel: CardViewModel
    private var card: Card? = null
    private var cardInfo: String = ""
    private var transactionInfo: TransactionResponse? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        cardViewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)

        binding.contentMain.rvContacts.layoutManager = LinearLayoutManager(this)
        adapter = ContactAdapter(applicationContext, this)
        binding.contentMain.rvContacts.adapter = adapter

        subscribe()
        contactViewModel.getContacts()

        transactionInfo = intent.getParcelableExtra(Constants.TRANSACTION_PARAM)

        if (transactionInfo != null) {
            cardInfo = intent.getStringExtra(Constants.CARD_INFO)
            Toast.makeText(this, transactionInfo?.transaction?.status.toString(), Toast.LENGTH_SHORT).show()
            bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetReceipt.clBottomSheetReceipt)
            bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(p0: View, p1: Float) {}
                override fun onStateChanged(p0: View, p1: Int) {}
            })

            fillReceipt()
        }
    }

    private fun setupSearchView() {
        val searchView = binding.contentMain.svContacts
        setSearchViewColorInactive(searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)

                if (newText != null && !newText.isEmpty()) {
                    setSearchViewColorActive(searchView)
                    searchView.background = ContextCompat.getDrawable(baseContext, R.drawable.bg_rounded_gray_border)
                } else {
                    setSearchViewColorInactive(searchView)
                    searchView.background = ContextCompat.getDrawable(baseContext, R.drawable.bg_rounded_gray)
                }

                return false
            }

        })
    }

    private fun setSearchViewColorInactive(searchView: SearchView) {
        val searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text) as EditText
        searchEditText.setTextColor(ContextCompat.getColor(this, R.color.gray_ACB1BD))
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.gray_ACB1BD))
        val searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon) as ImageView
        searchIcon.setImageResource(R.drawable.ic_search_inactive)
    }

    private fun setSearchViewColorActive(searchView: SearchView) {
        val searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text) as EditText
        searchEditText.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        searchEditText.setHintTextColor(ContextCompat.getColor(this, android.R.color.white))
        val searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon) as ImageView
        val searchClear = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn) as ImageView
        searchClear.setImageResource(R.drawable.ic_clear_active)
        searchIcon.setImageResource(R.drawable.ic_search_active)
    }

    private fun subscribe() {
        contactViewModel.getContactsObservable().observe(this, Observer {
            if (it != null) {
                binding.progressBar.visibility = View.GONE
                binding.contentMain.rvContacts.visibility = View.VISIBLE
                adapter.setData(it)
                setupSearchView()
            }
        })

        getCard()
    }

    private fun getCard() {
        cardViewModel.getCard().observe(this, Observer { card = it })
    }

    override fun onClick(contact: Contact) {
        if (card == null) {
            openCardPriming(contact)
        } else {
            openPayment(contact)
        }

    }

    private fun fillReceipt() {
        val contact = transactionInfo?.transaction?.contact
        val date = getDate(System.currentTimeMillis())

        binding.bottomSheetReceipt.tvUsernameReceipt.text = contact?.username
        binding.bottomSheetReceipt.tvDateReceipt.text = formatDate(date)
        binding.bottomSheetReceipt.tvTransactionReceipt.text =
                baseContext.getString(R.string.transaction_title, transactionInfo?.transaction?.id.toString())
        binding.bottomSheetReceipt.tvCardInfoReceipt.text = cardInfo
        binding.bottomSheetReceipt.tvCardValueReceipt.text =
                Formatador.VALOR_COM_SIMBOLO.formata(transactionInfo?.transaction?.value.toString())
        binding.bottomSheetReceipt.tvTotalValueReceipt.text =
                Formatador.VALOR_COM_SIMBOLO.formata(transactionInfo?.transaction?.value.toString())
        Picasso.with(baseContext)
            .load(contact?.image)
            .into(binding.bottomSheetReceipt.ivContactPayment)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
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

    private fun getDate(timestamp: Long): Date {
        val stamp = Timestamp(timestamp)
        return Date(stamp.time)
    }

    private fun formatDate(date: Date): String {
        val dateStr = DateUtils.toSimpleString(date)
        val timeStr = DateUtils.toSimpleStringTime(date)

        return baseContext.getString(R.string.date_formated, dateStr, timeStr)
    }
}
