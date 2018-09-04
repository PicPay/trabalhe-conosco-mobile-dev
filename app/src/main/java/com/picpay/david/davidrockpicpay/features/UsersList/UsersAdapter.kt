package com.picpay.david.davidrockpicpay.features.UsersList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.models.User
import java.text.DecimalFormat

class UsersAdapter(private val context: Context, private val users: ArrayList<User>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bind(users[position])
    }


    override fun getItemCount(): Int {
        return users.count()
    }


    fun swap(list: List<User>) {
        users.clear()
        users.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flag = itemView.us
        val nota = itemView.nota
        var labelNotaRecebida = itemView.

        fun bind(item: User) {
            val notaRecebida = "${item.UserName}/${item.Id} - "
            labelNotaRecebida.text = notaRecebida
            nota.text = DecimalFormat("##.##").format(item.Name)
        }
    }



}