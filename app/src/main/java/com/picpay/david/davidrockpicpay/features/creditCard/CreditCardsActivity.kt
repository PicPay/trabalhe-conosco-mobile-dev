package com.picpay.david.davidrockpicpay.features.creditCard

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.entities.CreditCard
import com.picpay.david.davidrockpicpay.features.base.BaseActivity
import com.picpay.david.davidrockpicpay.util.UiUtil
import kotlinx.android.synthetic.main.activity_credit_cards.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CreditCardsActivity : BaseActivity(), CreditCardsMvpView {

    private var presenter = CreditCardsPresenter()
    private lateinit var recyclerViewCards: RecyclerView
    private lateinit var adapter: RecyclerCardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_cards)

        presenter.attachView(this)

        buildView()
        getAllCards()

    }

    private fun getAllCards() {
        presenter.getAllCards()
    }

    override fun fillList(cards: List<CreditCard>) {

        var arrCards = ArrayList(cards)

        adapter = RecyclerCardsAdapter(arrCards, object : RecyclerCardsAdapter.OnItemClickListener {
            override fun onItemClick(item: CreditCard) {
                showMessage("Cartão com final " + item.CardNumber.toString().takeLast(4) + " selecionado")
                CreditCard().setDefaultCard(item)

                arrCards.clear()
                arrCards.addAll(CreditCard().getAll())
                adapter.notifyDataSetChanged()

                recyclerViewCards.adapter = adapter

            }
        })

        recyclerViewCards.adapter = adapter
        UiUtil.Layout.decorateRecyclerView(this, recyclerViewCards)
    }

    fun buildView(){
        recyclerViewCards = findViewById(R.id.rv_cards)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar) // Setting/replace toolbar as the ActionBar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            // back button pressed
            finish()
        }

        btnNewCard.setOnClickListener {
            var i = Intent(baseContext, NewCreditCardActivity::class.java)
            startActivity(i)
        }

        btnSelectCard.setOnClickListener {
            finish()
        }
    }


}
