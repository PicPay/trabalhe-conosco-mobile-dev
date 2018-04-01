package br.com.vdsantana.picpaytest.creditcard.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.vdsantana.picpaytest.BaseActivity
import br.com.vdsantana.picpaytest.R
import br.com.vdsantana.picpaytest.creditcard.CreditCard
import br.com.vdsantana.picpaytest.creditcard.di.CreditCardModule
import br.com.vdsantana.picpaytest.creditcard.di.DaggerCreditCardComponent
import br.com.vdsantana.picpaytest.utils.masks.CreditCardFormatTextWatcher
import br.com.vdsantana.picpaytest.utils.security.SecStore
import kotlinx.android.synthetic.main.activity_add_credit_card.*
import javax.inject.Inject


class AddCreditCardActivity : BaseActivity() {

    @Inject
    lateinit var mSecStore: SecStore

    private var creditCardFormatter: CreditCardFormatTextWatcher? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_credit_card)
        initializeUI()
    }

    private fun initializeUI() {
        creditCardFormatter = CreditCardFormatTextWatcher(creditCardNumber)
        creditCardNumber.addTextChangedListener(creditCardFormatter)
    }

    fun clickAddCreditCard(v: View?) {
        val creditCard = CreditCard()
        if (creditCardNumber.text.isEmpty() || creditCardNumber.text.length < 16) {
            creditCardNumber.error = "Formato de cartão inválido"
            return
        } else
            creditCard.cardNumber = creditCardNumber.text.toString().replace(" ", "")

        if (creditCardExpiryDate.text.isEmpty() || creditCardExpiryDate.text.length < 5) {
            creditCardExpiryDate.error = "Formato de validade incorreto"
            return
        } else
            creditCard.expiryDate = creditCardExpiryDate.text.toString()

        mSecStore.saveCreditCard(creditCard)
        Toast.makeText(this, "Cartão de crédito adicionado com sucesso...", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onError() {

    }

    override fun onActivityInject() {
        DaggerCreditCardComponent.builder().appComponent(getAppcomponent())
                .creditCardModule(CreditCardModule())
                .build()
                .inject(this)
    }
}
