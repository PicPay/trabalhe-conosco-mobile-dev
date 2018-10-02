package com.example.igor.picpayandroidx.View

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.igor.androidxtest.Contact
import com.example.igor.androidxtest.RequestService
import com.example.igor.picpayandroidx.*
import com.example.igor.picpayandroidx.Model.Card
import com.example.igor.picpayandroidx.Model.TransactionRequest
import com.example.igor.picpayandroidx.local.CardViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class MainActivity : AppCompatActivity(), OnContactClick {

    var mCardViewModel: CardViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);

        loading_contacts_list.visibility = View.VISIBLE

        mCardViewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)

        val onContactClick: OnContactClick = this
        val context: Context = this

        rv_users.layoutManager = LinearLayoutManager(this)

        if(AppStatus.getInstance(this).isOnline()){

            var requestService: RequestService = RetrofitClient().getClient("http://careers.picpay.com/tests/mobdev/")!!.create(RequestService::class.java)

            var call: Call<List<Contact>> = requestService.getContacts()
            call.enqueue(object : Callback<List<Contact>> {


                override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
                    Log.e("Tag onfailure", "retrofit on failure", t)
                }

                override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {

                    loading_contacts_list.visibility = View.GONE

                    if (response.isSuccessful) {

                        rv_users.adapter = ContactsAdapter(response.body(), onContactClick, context)

                    }

                }
            })

        } else {

            loading_contacts_list.visibility = View.GONE
            tv_no_internet_main.visibility = View.VISIBLE

        }

        tv_no_internet_main.setOnClickListener {
            recreate()
        }
    }

    override fun onClick(contact: Contact) {

        var transactionRequest = TransactionRequest("", 0, 0f, "", contact.id)

        mCardViewModel!!.checkDbAndStartActivity(this, transactionRequest)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.getItemId()

        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}