package com.picpay.david.davidrockpicpay.features.usersList

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.features.base.BaseActivity
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
//        adapter = UsersAdapter(this, ArrayList(users), object : UsersAdapter.OnItemClickListener {
//            override fun onItemClick(item: User) {
//
//                Toast.makeText(baseContext, "FOi : " + item.Name, Toast.LENGTH_LONG).show()
////                if (UiUtil.isOnlineOrMessage(context)) {
////                    presenter.associarVeiculo(item.Placa!!.toString())
////                }
//            }
//        })

//        adapter = RecyclerUsersAdapter(ArrayList(users), View.OnClickListener {
//            Toast.makeText(this, "VAIIIII CACETEEEEE ", Toast.LENGTH_LONG).show()
//        })
//
        adapter = RecyclerUsersAdapter(ArrayList(users), object : RecyclerUsersAdapter.OnItemClickListener {
            override fun onItemClick(item: User) {
                Toast.makeText(baseContext, "VAI " + item.Name, Toast.LENGTH_LONG).show()
            }
        })
//        recycler.setAdapter(ContentAdapter(items, object:ContentAdapter.OnItemClickListener() {
//            fun onItemClick(item:ContentItem) {
//                Toast.makeText(getContext(), "Item Clicked", Toast.LENGTH_LONG).show()
//            }
//        }))
        recyclerViewUsers.adapter = adapter
        UiUtil.Layout.decorateRecyclerView(this, recyclerViewUsers)
    }

}
