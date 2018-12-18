package br.com.kassianoresende.picpay.ui.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.kassianoresende.picpay.R
import br.com.kassianoresende.picpay.ui.viewmodel.CardListViewModel
import br.com.kassianoresende.picpay.ui.viewstate.CardListState
import kotlinx.android.synthetic.main.activity_credit_card_list.*
import kotlinx.android.synthetic.main.content_credit_card_list.*

class CreditCardListActivity : AppCompatActivity() {

    val viewmodel by lazy {
        ViewModelProviders.of(this).get(CardListViewModel::class.java)
    }

    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card_list)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener {
            startActivity(Intent(this, NewCreditCardActivity::class.java))
        }


        rvCardList.apply {
            adapter = viewmodel.cardAdapter
            layoutManager = LinearLayoutManager(this@CreditCardListActivity, LinearLayoutManager.VERTICAL, false)
        }


        viewmodel.loadCards()

        viewmodel.viewstate.observe(this, Observer(::updateUI))

        viewmodel.cardAdapter.itemClick = {

            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra(CheckoutActivity.CREDIT_CARD_ID, it.id)
            intent.putExtra(CheckoutActivity.CREDIT_CARD_NUMBER, it.cardNumber)
            intent.putExtra(CheckoutActivity.CREDIT_CARD_DUE_DATE, it.dueDate)
            intent.putExtra(CheckoutActivity.CREDIT_CARD_CVV, it.cvv)
            intent.putExtra(CheckoutActivity.CREDIT_CARD_FLAG, it.flag)

            startActivity(intent)
        }

    }

    fun updateUI(viewstate:CardListState?){

        when(viewstate){
            is CardListState.NoCardsFound-> startActivity(Intent(this, CreditCardActivity::class.java))
            is CardListState.Sucess -> errorSnackbar?.dismiss()
            is CardListState.LoadError-> {

                errorSnackbar = Snackbar.make(rootView , getString(R.string.loading_cards_error),
                    Snackbar.LENGTH_INDEFINITE).apply {
                    setAction(R.string.retry, viewmodel.errorClickListener)
                    show()
                }
            }
            is CardListState.StartLoading-> {
                progressBar.visibility = View.VISIBLE
                rvCardList.visibility= View.GONE
            }
            is CardListState.FinishLoading-> {
                progressBar.visibility = View.GONE
                rvCardList.visibility= View.VISIBLE
            }
        }

    }
}
