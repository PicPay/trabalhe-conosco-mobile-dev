package com.v1pi.picpay_teste

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.v1pi.picpay_teste.Adapter.UserListAdapter
import com.v1pi.picpay_teste.Domains.User
import com.v1pi.picpay_teste.Utils.RetrofitManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var recyclerView : RecyclerView? = null
    private val retrofit = RetrofitManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = user_list_rv
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL

        recyclerView?.let {
            it.adapter = UserListAdapter(emptyList(), this)
            it.layoutManager = llm
            retrofit.requestUsers(::UpdateRecyclerView)
        }
    }

    fun UpdateRecyclerView(listUser : List<User>) {
        recyclerView?.adapter = UserListAdapter(listUser, this)
    }
}
