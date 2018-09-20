package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.InflateException
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_money.*
import kotlinx.android.synthetic.main.activity_input_money.view.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter.AdapterMyAllCards
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserCards
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserData
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.viewmodel.UserCardsViewModel
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.viewmodel.UserDataViewModel

class AddMoneyActivity : AppCompatActivity() {

    private var mUserCardsViewModel: UserCardsViewModel? = null
    private var mUserDataViewModel: UserDataViewModel? = null
    private var mRecyclerMeusCartoes: RecyclerView? = null
    private var mAdapterMyAllCards: AdapterMyAllCards? = null
    private var mListMyCards: ArrayList<UserCards> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_money)

        mAdapterMyAllCards = AdapterMyAllCards(this, mListMyCards)

        mRecyclerMeusCartoes = recyclerMeusCartoes

        mUserCardsViewModel = ViewModelProviders.of(this).get(UserCardsViewModel::class.java)
        mUserCardsViewModel!!.getCards().observe(this, Observer<List<UserCards>> {
            it!!.forEach {
                mListMyCards.add(it)
            }
        })

        mUserDataViewModel = ViewModelProviders.of(this).get(UserDataViewModel::class.java)

        configureRecycler()

        swipe()
    }

    private fun configureRecycler() {
        mRecyclerMeusCartoes!!.layoutManager = LinearLayoutManager(this)
        mRecyclerMeusCartoes!!.setHasFixedSize(true)
        mRecyclerMeusCartoes!!.adapter = mAdapterMyAllCards
    }

    private fun swipe() {
        val itemTouch: ItemTouchHelper.Callback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                val dragFlags = ItemTouchHelper.ACTION_STATE_IDLE
                val swipeFlags = ItemTouchHelper.START ; ItemTouchHelper.END

                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                inputValor()
            }

        }

        ItemTouchHelper(itemTouch).attachToRecyclerView(mRecyclerMeusCartoes)
    }

    private fun inputValor() {
        var view: View? = null

        try {
            view = LayoutInflater.from(this).inflate(R.layout.activity_input_money, null)
        } catch (e: InflateException) {
            e.printStackTrace()
        }

        val editMoney = view!!.editMoney
        val buttonCancel = view.buttonCancel
        val buttonConfirmar = view.buttonConfirmar

        val dialog = Dialog(this)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.create()
        dialog.show()

        buttonCancel.setOnClickListener {
            dialog.dismiss()
            configureRecycler()
        }

        buttonConfirmar.setOnClickListener {
            if(editMoney.text.toString()!="") {
                val value: Double = editMoney.text.toString().toDouble()
                val userData: UserData = UserData(1, value)
                mUserDataViewModel!!.insert(userData)
                dialog.dismiss()
                this@AddMoneyActivity.finish()
            } else
                Toast.makeText(applicationContext, "Digite um valor", Toast.LENGTH_SHORT).show()
        }
    }

}
