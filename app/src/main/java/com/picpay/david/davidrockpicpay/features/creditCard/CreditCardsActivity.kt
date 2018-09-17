package com.picpay.david.davidrockpicpay.features.creditCard

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.Toast
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.entities.CreditCard
import com.picpay.david.davidrockpicpay.extensions.RecyclerItemTouchHelper
import com.picpay.david.davidrockpicpay.features.base.BaseActivity
import com.picpay.david.davidrockpicpay.util.UiUtil
import kotlinx.android.synthetic.main.activity_credit_cards.*
import org.greenrobot.eventbus.EventBus
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager



class CreditCardsActivity : BaseActivity(), CreditCardsMvpView, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        UiUtil.Messages.message(applicationContext, "Cartão Removido!")
        adapter.removeAt(viewHolder.adapterPosition)
    }

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

        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewCards.layoutManager = mLayoutManager
        recyclerViewCards.itemAnimator = DefaultItemAnimator()
        recyclerViewCards.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        adapter = RecyclerCardsAdapter(arrCards, object : RecyclerCardsAdapter.OnItemClickListener {
            override fun onItemClick(item: CreditCard) {

                CreditCard().setDefaultCard(item)

                arrCards.clear()
                arrCards.addAll(CreditCard().getAll())
                adapter.notifyDataSetChanged()

                recyclerViewCards.adapter = adapter

                //Enviar evento de mudança de cartão padrão
                EventBus.getDefault().post(item)
            }
        })

        recyclerViewCards.adapter = adapter

        val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)

        // attaching the touch helper to recycler view
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewCards)

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

    override fun onPostResume() {
        super.onPostResume()
        getAllCards()
    }
}
