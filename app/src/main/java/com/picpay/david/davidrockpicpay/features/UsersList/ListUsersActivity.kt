package com.picpay.david.davidrockpicpay.features.UsersList

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.features.base.BaseActivity
import com.picpay.david.davidrockpicpay.models.User
import com.picpay.david.davidrockpicpay.util.UiUtil

class ListUsersActivity : BaseActivity(), ListUsersMvpView {

    private val presenter = ListUsersPresenter()
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var adapter: UsersAdapter


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
        adapter = UsersAdapter(this, ArrayList(users))
        recyclerViewUsers.adapter = adapter
        UiUtil.Layout.decorateRecyclerView(this, recyclerViewUsers)
    }

}
