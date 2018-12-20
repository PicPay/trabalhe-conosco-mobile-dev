package br.com.kassianoresende.picpay.ui.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.kassianoresende.picpay.R
import br.com.kassianoresende.picpay.ui.viewmodel.NewCardViewModel
import br.com.kassianoresende.picpay.ui.viewstate.NewCreditCardState
import br.com.kassianoresende.picpay.ui.viewstate.NewCreditCardState.*
import kotlinx.android.synthetic.main.activity_new_credit_card.*
import kotlinx.android.synthetic.main.content_new_credit_card.*

class NewCreditCardActivity : AppCompatActivity() {


    val viewmodel by lazy {
        ViewModelProviders.of(this).get(NewCardViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_credit_card)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewmodel.viewState.observe(this, Observer(::updateUI))

        btSaveCard.setOnClickListener {

            val cardNumber = etCardNumber.text.toString()
            val name = etUserName.text.toString()
            val dueDate = etDueDate.text.toString()
            val cvv = etCvv.text.toString().toInt()

            viewmodel.saveCard(cardNumber, name, dueDate, cvv)
        }

    }


    fun updateUI(viewstate: NewCreditCardState?){

        when(viewstate){
            is Sucess-> startActivity(Intent(this, CreditCardListActivity::class.java))
            is EmptyCardNumber-> etCardNumber.error = getString(R.string.empty_field)
            is EmptyName -> etUserName.error = getString(R.string.empty_field)
            is EmptyDueDate-> etDueDate.error = getString(R.string.empty_field)
            is EmptyCvv-> etCvv.error = getString(R.string.empty_field)
            is GenericError-> Toast.makeText(this, getString(R.string.generic_error),Toast.LENGTH_SHORT).show()
        }
    }

}
