package br.com.vdsantana.picpaytest.creditcard.choose

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.vdsantana.picpaytest.BaseActivity
import br.com.vdsantana.picpaytest.R
import br.com.vdsantana.picpaytest.creditcard.CreditCard
import br.com.vdsantana.picpaytest.creditcard.add.AddCreditCardActivity
import br.com.vdsantana.picpaytest.creditcard.di.CreditCardModule
import br.com.vdsantana.picpaytest.creditcard.di.DaggerCreditCardComponent
import br.com.vdsantana.picpaytest.utils.security.SecStore
import kotlinx.android.synthetic.main.activity_choose_credit_card.*
import net.idik.lib.slimadapter.SlimAdapter
import javax.inject.Inject

class ChooseCreditCardActivity : BaseActivity() {

    @Inject
    lateinit var mSecStore: SecStore

    private lateinit var mSlimAdapter: SlimAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_credit_card)
        initializeUI()
    }

    private fun initializeUI() {
        creditCardList.layoutManager = LinearLayoutManager(this)
        mSlimAdapter = SlimAdapter.create()
                .register<CreditCard>(R.layout.item_credit_card) { creditCard, injector ->
                    injector.text(R.id.cardNumber, getString(R.string.final_card, creditCard.cardNumber.substring(creditCard.cardNumber.length.minus(4))))
                    injector.text(R.id.expirationDate, creditCard.expiryDate)
                    injector.clicked(R.id.mainLayout, {
                        val intentResult = Intent()
                        intentResult.putExtra("selected_card", creditCard)
                        setResult(Activity.RESULT_OK, intentResult)
                        finish()
                    })
                }.attachTo(creditCardList)

        loadCreditCards()
    }

    private fun loadCreditCards() {
        val creditCards = mSecStore.retrieveCreditCards()
        mSlimAdapter.updateData(creditCards)
    }

    fun clickAddCreditCard(v: View?) {
        startActivity(Intent(this, AddCreditCardActivity::class.java))
    }

    override fun onError() {

    }

    override fun onActivityInject() {
        DaggerCreditCardComponent.builder().appComponent(getAppcomponent())
                .creditCardModule(CreditCardModule())
                .build()
                .inject(this)
    }

    override fun onResume() {
        super.onResume()
        loadCreditCards()
    }
}
