package com.v1pi.picpay_teste.Controllers

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.v1pi.picpay_teste.Adapter.UserListAdapter
import com.v1pi.picpay_teste.MainActivity
import com.v1pi.picpay_teste.Utils.RetrofitManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityController(activity: MainActivity) {
    private val recyclerView : RecyclerView = activity.user_list_rv
    private val retrofit = RetrofitManager()

    init {
        val llm = LinearLayoutManager(activity)
        llm.orientation = LinearLayoutManager.VERTICAL

        recyclerView.adapter = UserListAdapter(emptyList(), activity)

        recyclerView.layoutManager = llm

        retrofit.requestUsers(activity.updateRecyclerView(recyclerView))

    }
}