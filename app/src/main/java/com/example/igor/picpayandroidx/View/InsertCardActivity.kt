package com.example.igor.picpayandroidx.View

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.igor.picpayandroidx.Model.Card
import com.example.igor.picpayandroidx.Model.TransactionRequest
import com.example.igor.picpayandroidx.R
import com.example.igor.picpayandroidx.local.CardViewModel
import kotlinx.android.synthetic.main.activity_insert_card.*
import kotlinx.android.synthetic.main.activity_insert_value.*

class InsertCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_card)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);

        et_cvv.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.getAction() === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Perform action on key press
                    insertCard()
                    return true
                }
                return false
            }
        })

    }

    fun onOkClick(view: View) {

        insertCard()

    }

    private fun insertCard() {
        if(et_card_number.text.isBlank()) {

            Toast.makeText(this, "Informe o número do cartão.", Toast.LENGTH_SHORT)
                    .show()

        } else if (et_card_number.text.length < 16){

            Toast.makeText(this, "Número do cartão deve conter 16 digitos.", Toast.LENGTH_SHORT)
                    .show()

        } else if (et_cvv.text.isBlank()){

            Toast.makeText(this, "Informe o código de segurança.", Toast.LENGTH_SHORT)
                    .show()

        } else if (et_cvv.text.length < 3){

            Toast.makeText(this, "Código de segurança deve conter 3 digitos.", Toast.LENGTH_SHORT)
                    .show()

        } else {

            var card = Card()
            card.number = et_card_number.text.toString()
            card.cvv = et_cvv.text.toString().toInt()
            card.expdate = "${s_month.selectedItem}/${s_year.selectedItem.toString().subSequence(2,4)}"

            val mCardViewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)
            mCardViewModel!!.insert(card)

//                var isCalledForAddingNewCard= intent.getBooleanExtra(InsertValueActivity.CALLING_FOR_RESULT, false)

            val cardIntent = Intent(this, InsertValueActivity::class.java)

            val destinationUserId: String = applicationContext.getString(R.string.destination_user_id_key)

            val transactionRequest = TransactionRequest(
                    card.number,
                    card.cvv!!,
                    0f,
                    card.expdate!!,
                    intent.extras.getInt(destinationUserId))


            cardIntent.putExtra(getString(R.string.transaction_request_parcelable), transactionRequest)
            cardIntent.putExtra(getString(R.string.is_card_just_added), true)
            startActivity(cardIntent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.getItemId()

        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
