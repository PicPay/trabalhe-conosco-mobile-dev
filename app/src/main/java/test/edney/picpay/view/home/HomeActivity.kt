package test.edney.picpay.view.home

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import test.edney.picpay.R
import test.edney.picpay.databinding.ActivityMainBinding
import test.edney.picpay.model.UserModel
import test.edney.picpay.view.card.CardActivity
import test.edney.picpay.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: HomeViewModel
    private lateinit var mUserAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //setSupportActionBar(binding.toolbar)
        viewmodel()

        configureListOfUser()
    }

    private fun viewmodel(){
        viewmodel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        viewmodel.userResponse.observe(this, Observer {
            if(it != null){
                mUserAdapter.postUsers(it)
                stopLoading()
            }
        })
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
