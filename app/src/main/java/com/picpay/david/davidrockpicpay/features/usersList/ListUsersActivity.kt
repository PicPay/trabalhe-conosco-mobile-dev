package com.picpay.david.davidrockpicpay.features.usersList

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.google.gson.Gson
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.features.base.BaseActivity
import com.picpay.david.davidrockpicpay.features.sendMoney.SendMoneyActivity
import com.picpay.david.davidrockpicpay.models.User
import com.picpay.david.davidrockpicpay.util.UiUtil

class ListUsersActivity : BaseActivity(), ListUsersMvpView {

    private val presenter = ListUsersPresenter()
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var adapter: RecyclerUsersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_users)
        presenter.attachView(this)
        recyclerViewUsers = findViewById(R.id.rv_users)

        getAllUsers()

    }

    private fun getAllUsers() {
        presenter.getAllUsers()
    }

    override fun fillList(users: List<User>) {

        adapter = RecyclerUsersAdapter(ArrayList(users), object : RecyclerUsersAdapter.OnItemClickListener {
            override fun onItemClick(item: User) {
                showMessage("Pagando " + item.Name)

                var user = Gson().toJson(item)
                var i = Intent(baseContext, SendMoneyActivity::class.java)
                i.putExtra("user", user)
                startActivity(i)
            }
        })

        recyclerViewUsers.adapter = adapter
        UiUtil.Layout.decorateRecyclerView(this, recyclerViewUsers)
    }

}
