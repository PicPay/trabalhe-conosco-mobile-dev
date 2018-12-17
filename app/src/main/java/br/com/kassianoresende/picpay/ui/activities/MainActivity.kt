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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val viewmodel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        rvUserList.apply {
            adapter = viewmodel.userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }

        viewmodel.loading.observe(this, Observer(::updateLoading))
        viewmodel.errorMessage.observe(this, Observer(::showError))

        viewmodel.loadUsers()
        viewmodel.searchUser(etSearch)

        viewmodel.userAdapter.itemClick = View.OnClickListener {
            startActivity(Intent(this, CreditCardActivity::class.java))
        }

    }

    fun updateLoading(loading:Boolean?){

        if(loading!=null && loading){
            progressBar.visibility = View.VISIBLE
            rvUserList.visibility = View.GONE
        }else{
            progressBar.visibility = View.GONE
            rvUserList.visibility = View.VISIBLE
        }
    }

    fun showError(errorMessage:Int?){

        if(errorMessage!=null) {

            errorSnackbar = Snackbar.make(rootView , errorMessage, Snackbar.LENGTH_INDEFINITE).apply {
                setAction(R.string.retry, viewmodel.errorClickListener)
                show()
            }

        }else{
            errorSnackbar?.dismiss()
        }
    }

}
