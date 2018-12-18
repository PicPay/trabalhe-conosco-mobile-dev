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
import br.com.kassianoresende.picpay.ui.viewmodel.MainViewModel
import br.com.kassianoresende.picpay.ui.viewstate.ListUsersState
import br.com.kassianoresende.picpay.util.CkeckoutUserPrefs
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val viewmodel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private var errorSnackbar: Snackbar? = null

    val userPrefs by lazy {
        CkeckoutUserPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        userPrefs.clear()

        rvUserList.apply {
            adapter = viewmodel.userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }

        viewmodel.viewstate.observe(this, Observer(::updateUI))

        viewmodel.loadUsers()
        viewmodel.searchUser(etSearch)

        viewmodel.userAdapter.itemClick = {
            user->

            userPrefs.apply {
                userId = user.id
                userName = user.username
                userImage = user.img
            }

            val intent = Intent(this, CreditCardListActivity::class.java)
            startActivity(intent)
        }

    }

    fun updateUI(viewstate: ListUsersState?){

        when(viewstate){
            is ListUsersState.LoadError-> {
                errorSnackbar = Snackbar.make(rootView , getString(R.string.user_error), Snackbar.LENGTH_INDEFINITE).apply {
                    setAction(R.string.retry, viewmodel.errorClickListener)
                    show()
                }
            }

            is ListUsersState.StartLoading -> {
                progressBar.visibility = View.VISIBLE
                rvUserList.visibility = View.GONE
            }

            is ListUsersState.FinishLoading -> {
                progressBar.visibility = View.GONE
                rvUserList.visibility = View.VISIBLE
            }

            is ListUsersState.Sucess -> errorSnackbar?.dismiss()
        }

    }

}
