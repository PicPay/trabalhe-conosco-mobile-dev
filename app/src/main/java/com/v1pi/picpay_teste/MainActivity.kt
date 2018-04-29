package com.v1pi.picpay_teste

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import com.v1pi.picpay_teste.Adapter.UserListAdapter
import com.v1pi.picpay_teste.Controllers.MainActivityController
import com.v1pi.picpay_teste.Domains.User

class MainActivity : AppCompatActivity() {
    private lateinit var controller : MainActivityController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controller = MainActivityController(this)

    }

    fun onClickRefresh(view : View) {
        controller.fillUserCards()
    }

    fun updateRecyclerView(recycler : RecyclerView) : (List<User>) -> Unit {
        return {listUser : List<User> ->
            recycler.adapter = UserListAdapter(listUser, this)
        }

    }
}
