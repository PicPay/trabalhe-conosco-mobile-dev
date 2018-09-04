package com.picpay.david.davidrockpicpay.features.UsersList

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.features.base.BaseActivity
import com.picpay.david.davidrockpicpay.models.User

class ListUsersActivity : BaseActivity(), ListUsersMvpView {

    private val presenter = ListUsersPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_users)
        presenter.attachView(this)

        getAllUsers()

    }

    override fun getAllUsers() {
        presenter.getAllUsers()
    }

    override fun fillList(users: List<User>) {
        //codigo pra preencher o recyclerview
    }

}
