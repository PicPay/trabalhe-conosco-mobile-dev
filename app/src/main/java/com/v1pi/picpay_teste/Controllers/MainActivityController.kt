package com.v1pi.picpay_teste.Controllers

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.v1pi.picpay_teste.Adapter.UserListAdapter
import com.v1pi.picpay_teste.MainActivity
import com.v1pi.picpay_teste.R
import com.v1pi.picpay_teste.Utils.InternetCheck
import com.v1pi.picpay_teste.Utils.RetrofitManager
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivityController(private val activity: MainActivity) {
    private val recyclerView : RecyclerView = activity.user_list_rv
    private val retrofit = RetrofitManager()

    init {
        val llm = LinearLayoutManager(activity)
        llm.orientation = LinearLayoutManager.VERTICAL

        recyclerView.adapter = UserListAdapter(emptyList(), activity)

        recyclerView.layoutManager = llm

        fillUserCards()

    }

    fun fillUserCards() {
        InternetCheck(object : Consumer<Boolean> {
            override fun accept(t: Boolean) {
                if(t) {
                    activity.img_refresh.visibility = View.GONE
                    retrofit.responseUsers(activity.updateRecyclerView(recyclerView))
                } else {
                    Toast.makeText(activity, activity.getString(R.string.no_connection), Toast.LENGTH_LONG).show()
                    activity.img_refresh.visibility = View.VISIBLE
                }
            }
        })
    }
}