package com.picpay.david.davidrockpicpay.features.UsersList

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.features.base.BaseActivity
import com.picpay.david.davidrockpicpay.models.User

class ListUsersActivity : BaseActivity(), ListUsersMvpView {

    override fun getAllUsers() {
        super.showMessage("Ok TOAST!")
    }

    override fun fillList(users: List<User>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_users)

        getAllUsers()
    }
}
