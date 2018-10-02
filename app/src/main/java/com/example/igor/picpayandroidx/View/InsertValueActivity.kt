package com.example.igor.picpayandroidx.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.igor.androidxtest.RequestService
import com.example.igor.picpayandroidx.Model.Card
import com.example.igor.picpayandroidx.Model.TransactionRequest
import com.example.igor.picpayandroidx.R
import com.example.igor.picpayandroidx.RetrofitClient
import com.example.igor.picpayandroidx.local.CardViewModel
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_insert_value.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.SharedPreferences
import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.MenuItem
import com.example.igor.picpayandroidx.AppStatus
import kotlinx.android.synthetic.main.activity_insert_card.*
import kotlinx.android.synthetic.main.activity_main.*


class InsertValueActivity : AppCompatActivity() {

    var mCardViewModel: CardViewModel? = null
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var cardsList: List<Card>? = null
    var placeHolderAdapter: ArrayAdapter<String>? = null
    var transactionRequest: TransactionRequest? = null
    var isCardJustAdded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_value)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);

        mCardViewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)

        sharedPreferences = getSharedPreferences(getString(R.string.pref_key), Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()

        transactionRequest = intent.getParcelableExtra(getString(R.string.transaction_request_parcelable))
        isCardJustAdded = intent.getBooleanExtra(getString(R.string.is_card_just_added), false)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(dapterView: AdapterView<*>, view: View?, i: Int, l: Long) {

                var itemAtActualPosition = spinner.getItemAtPosition(i)

                if (itemAtActualPosition != getString(R.string.no_card_registered)) {

                    var card = itemAtActualPosition as Card

                    transactionRequest?.card_number = card.number
                    transactionRequest?.cvv = card.cvv
                    transactionRequest?.expiry_date = card.expdate
                }

                if (isCardJustAdded) {
                   spinner.setSelection(spinner.count - 1)
                    isCardJustAdded = false
                }
                //Saving Preferences
                editor!!.putInt(getString(R.string.pref_text), i)
                editor!!.apply()

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }
        }

        var adapter: ArrayAdapter<Card>?


        var mCardViewModel: CardViewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)
        mCardViewModel!!.allCards.observe(this, object : Observer<List<Card>> {

            override fun onChanged(listOfCards: List<Card>?) {

                cardsList = listOfCards

                if (cardsList!!.isNotEmpty()) {

                    adapter = ArrayAdapter(applicationContext, R.layout.spinner_item, R.id.tv_spinner_item, listOfCards)
                    spinner!!.setAdapter(adapter)
                    adapter!!.notifyDataSetChanged()

                    sharedPreferences = getSharedPreferences(getString(R.string.pref_key), Context.MODE_PRIVATE)

                    val result = sharedPreferences!!.getInt(getString(R.string.pref_text), -1)

                    if (result != -1) {
                        spinner.setSelection(result)
                    }

                } else {

                    btn_delete.visibility = View.GONE

                    var placeHolder = getString(R.string.no_card_registered)

                    val placeHolderList = listOf(placeHolder)
                    placeHolderAdapter = ArrayAdapter(applicationContext, R.layout.spinner_item, R.id.tv_spinner_item, placeHolderList)
                    spinner.adapter = placeHolderAdapter

                }

            }

        })

        btn_value_ok.setOnClickListener {
            insertValue()
        }

        et_value.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.getAction() === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Perform action on key press
                    insertValue()
                    return true
                }
                return false
            }
        })

        tv_no_internet_insert_value.setOnClickListener {
            performPostRequest(transactionRequest)
        }
    }

    private fun insertValue() {
        var strValue: String = et_value.text.toString()

        if (spinner.getItemAtPosition(0) == getString(R.string.no_card_registered)) {

            val toast = Toast.makeText(this, "Cadastre um cartão primeiro.", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()

        } else {

            if (strValue.isNotEmpty() && !strValue.contentEquals(getString(R.string.empty_value)) ) {

                strValue = strValue.replace(".", "")

                AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Realizar transação")
                        .setMessage("Deseja realizar a transação no valor de $strValue?")
                        .setPositiveButton("Sim") { dialog, which ->

                            loading_post_request.visibility = View.VISIBLE

                            var mValue = strValue
                                    .replace(",", ".")
                                    .replace("R$", "")

                            if (transactionRequest!!.card_number == "") {

                                var card = spinner.selectedItem as Card

                                transactionRequest!!.card_number = card.number
                                transactionRequest!!.cvv = card.cvv
                                transactionRequest!!.expiry_date = card.expdate
                            }

                            transactionRequest!!.value = mValue.toFloat()

                            //Realizar transação
                            performPostRequest(transactionRequest);

                        }
                        .setNegativeButton("Não", null)
                        .show()

            } else {

                Toast.makeText(this, getString(R.string.Informe_valor_da_transacao), Toast.LENGTH_SHORT)
                        .show()

            }

        }
    }

    private fun performPostRequest(transactionRequest: TransactionRequest?) {

        if(AppStatus.getInstance(this).isOnline()){

            var requestService: RequestService = RetrofitClient().getClient(getString(R.string.picpay_base_url))!!.create(RequestService::class.java)

            var call: Call<JsonObject> = requestService.postTransaction(transactionRequest)
            call.enqueue(object : Callback<JsonObject> {

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    println("onFailure post request" + t.message)
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                    loading_post_request.visibility = View.GONE

                    if (response.isSuccessful) {

                        val transaction: JsonObject = response.body()!!.getAsJsonObject(getString(R.string.json_object_transaction))
                        val success: Boolean = transaction.get("success").asBoolean

                        if (success) {

                            val destinationUser: JsonObject = transaction.getAsJsonObject(getString(R.string.json_object_destination_user))

                            var successIntent = Intent(applicationContext, SuccessActivity::class.java)
                            successIntent.putExtra(getString(R.string.success_intent_img_key), destinationUser.get(getString(R.string.detination_user_parameter_img)).asString)
                            successIntent.putExtra(getString(R.string.success_intent_name_key), destinationUser.get(getString(R.string.detination_user_parameter_name)).asString)
                            successIntent.putExtra(getString(R.string.success_intent_value_key), transaction.get(getString(R.string.detination_user_parameter_value)).asFloat)

                            startActivity(successIntent)
                            finish()

                        } else {

                            AlertDialog.Builder(this@InsertValueActivity)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Transação negada.")
                                    .setMessage("Número de cartão inválido.")
                                    .setPositiveButton("Ok") { dialog, which ->

                                        if(ly_no_internet_connection.visibility == View.VISIBLE){
                                            recreate()
                                        }
                                    }
                                    .show()
                        }

                    } else {
                        print("probable error on object sent to server on post request.\n")
                    }

                }
            })

        } else {
            btn_value_ok.visibility = View.GONE
            ly_no_internet_connection.visibility = View.VISIBLE

        }



    }

    fun addNewCard(view: View) {
        val intent = Intent(this@InsertValueActivity, InsertCardActivity::class.java)

        intent.putExtra(getString(R.string.destination_user_id_key), transactionRequest!!.destination_user_id)
        startActivity(intent)
        finish()
    }

    fun deleteCard(view: View) {

        var card: Card = spinner.selectedItem as Card

        AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Deletar cartão")
                .setMessage("Deseja deletar o cartão de número \n ${card.number} ?")
                .setPositiveButton("Sim") { dialog, which ->

                    mCardViewModel!!.deleteCardById(card.id)

                    editor!!.remove(getString(R.string.pref_text))
                    editor!!.commit()

                }
                .setNegativeButton("Não", null)
                .show()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.getItemId()

        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}

