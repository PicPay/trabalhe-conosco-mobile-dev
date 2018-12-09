package test.edney.picpay.view.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import test.edney.picpay.R
import test.edney.picpay.custom.ReceiptDialog
import test.edney.picpay.databinding.ActivityMainBinding
import test.edney.picpay.model.UserModel
import test.edney.picpay.view.card.CardActivity
import test.edney.picpay.viewmodel.HomeVM


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: HomeVM
    private lateinit var mUserAdapter: UserAdapter

    private val receiptDelay = 400L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //setSupportActionBar(binding.toolbar)
        viewmodel()

        binding.etSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                mUserAdapter.getFilter().filter(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        showReceipt()
        configureListOfUser()
    }

    private fun viewmodel(){
        viewmodel = ViewModelProviders.of(this).get(HomeVM::class.java)

        viewmodel.userResponse.observe(this, Observer {
            if(it != null){
                mUserAdapter.postUsers(it)
                stopLoading()
            }
        })
    }

    private fun showReceipt(){
        val args = intent.getStringExtra("receipt_name")

        if (args != null) {
            Handler().postDelayed({ ReceiptDialog().show(supportFragmentManager, "receipt_dialog") }, receiptDelay)
        }
    }

    private fun configureListOfUser(){
        mUserAdapter = UserAdapter(object : UserAdapterListener{
            override fun onItemClick(user: UserModel) {
                startActivity(Intent(this@HomeActivity, CardActivity::class.java))
            }
        })
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = mUserAdapter
        viewmodel.requestUsers()
    }

    private fun stopLoading(){
        binding.progress.visibility = GONE
        binding.rvUser.visibility = VISIBLE
    }
}
