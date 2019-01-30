package br.com.picpay.picpay.ui.contact

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.widget.EditText
import android.widget.ImageView
import br.com.picpay.picpay.R
import br.com.picpay.picpay.adapter.ItemListener
import br.com.picpay.picpay.adapter.UserAdapter
import br.com.picpay.picpay.base.BaseActivity
import br.com.picpay.picpay.model.Transaction
import br.com.picpay.picpay.model.User
import br.com.picpay.picpay.utils.Constants
import br.com.picpay.picpay.utils.Constants.Companion.RECEIPT
import br.com.picpay.picpay.utils.Constants.Companion.SAVE_CARD
import br.com.picpay.picpay.utils.Constants.Companion.SHARED_USER
import br.com.picpay.picpay.utils.ReceiptSwipeDialog
import kotlinx.android.synthetic.main.activity_contacts.*

class ContactActivity : BaseActivity<ContactViewModel>() {

    private lateinit var adapter: UserAdapter
    private lateinit var selectedUser: User
    private lateinit var preferences: SharedPreferences
    private var transaction: Transaction? = null

    private val itemListener = object : ItemListener<User> {
        override fun onClick(item: User) {
            selectedUser = item
            if (preferences.contains(Constants.SAVED_CARD)){
                viewModel?.setActivityTransaction(this@ContactActivity, item)
            } else viewModel?.setActivityPriming(this@ContactActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        setSupportActionBar(coordinator_toolbar)
        supportActionBar?.title = ""

        val bundle = intent?.extras
        bundle?.let {
            transaction = it.getParcelable(RECEIPT)!!
        }

        if (transaction != null){
            ReceiptSwipeDialog.newInstance(transaction!!)
                .show(supportFragmentManager, "Receipt")
        }

        adapter = UserAdapter(this, itemListener)
        preferences = getSharedPreferences(SHARED_USER, Context.MODE_PRIVATE)
        contact_list.layoutManager = LinearLayoutManager(this)
        contact_list.setHasFixedSize(true)
        contact_list.adapter = adapter

        viewModel?.errorMessage?.observe(this, Observer { error->
            setErrorResult(error)
        })

        viewModel?.loadingVisibility?.observe(this, Observer { visibility ->
            if (visibility != null) showLoading(visibility)
        })

        viewModel?.resultUsers?.observe(this, Observer { response ->
            if (response != null) adapter.insertData(response)
        })

        searchListener()
    }

    private fun searchListener() {
        val editText = contact_search.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
        editText.setHintTextColor(ContextCompat.getColor(this, R.color.search_text))
        editText.setTextColor(ContextCompat.getColor(this, R.color.color_white))
        val icon = contact_search.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_mag_icon)

        contact_search.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel?.sortList(newText)
                    if (adapter.itemCount == 0) setErrorResult(getString(R.string.error_not_found))
                    if (!newText.isNullOrEmpty()){
                        contact_search.background = ContextCompat.getDrawable(this@ContactActivity, R.drawable.shape_search)
                        icon.setImageDrawable(ContextCompat.getDrawable(this@ContactActivity, R.drawable.ic_search_white))
                    } else {
                        contact_search.background = ContextCompat.getDrawable(this@ContactActivity, R.drawable.shape_search_disable)
                        icon.setImageDrawable(ContextCompat.getDrawable(this@ContactActivity, R.drawable.ic_search_24dp))
                    }
                }
                return false
            }
        })
    }

    private fun showLoading(visibility: Int) {
        contact_loading_screen.visibility = visibility
    }

    private fun setErrorResult(error: String?) {
        if (error != null) Snackbar.make(coordinator_contacts, error, Snackbar.LENGTH_LONG).show()
    }

    override fun containerViewModel(): ContactViewModel? {
        return ViewModelProviders
            .of(this)
            .get(ContactViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            SAVE_CARD -> {
                if(resultCode == Activity.RESULT_OK) {
                    viewModel?.setActivityTransaction(this, selectedUser)
                }
            }
        }
    }
}
