package br.com.picpay.picpay.ui.contact

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import br.com.picpay.picpay.R
import br.com.picpay.picpay.adapter.UserAdapter
import br.com.picpay.picpay.base.BaseActivity
import kotlinx.android.synthetic.main.activity_contacts.*

class ContactActivity : BaseActivity<ContactViewModel>() {

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        setSupportActionBar(coordinator_toolbar)
        supportActionBar?.title = ""

        adapter = UserAdapter(this)

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
    }

    private fun showLoading(visibility: Int) {
        loading_screen.visibility = visibility
    }

    private fun setErrorResult(error: String?) {
        if (error != null) Snackbar.make(coordinator_contacts, error, Snackbar.LENGTH_LONG).show()
    }

    override fun containerViewModel(): ContactViewModel? {
        return ViewModelProviders
            .of(this)
            .get(ContactViewModel::class.java)
    }
}
