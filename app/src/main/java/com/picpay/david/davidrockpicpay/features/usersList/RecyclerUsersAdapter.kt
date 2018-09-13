package com.picpay.david.davidrockpicpay.features.usersList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_card.view.*


//class RecyclerUsersAdapter(items: ArrayList<User>, val listener: (Int) -> Unit) : RecyclerView.Adapter<RecyclerUsersAdapter.ViewHolder>() {

class RecyclerUsersAdapter(val items: ArrayList<User>, val listener: OnItemClickListener): RecyclerView.Adapter<RecyclerUsersAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], position, listener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userid = itemView.tvId
        val uname = itemView.tvName
        val userName = itemView.tvUserName
        var img = itemView.userImage
        var imgMoney = itemView.imgMoney
        var imgChevron = itemView.imgChevron

        fun bind(item: User, pos: Int, listener: OnItemClickListener) {

            img.setOnClickListener { listener.onItemClick(item) }
            imgMoney.setOnClickListener { listener.onItemClick(item) }
            imgChevron.setOnClickListener { listener.onItemClick(item) }
//            img.setOnClickListener(listener)

            Picasso.get().load(item.Img).into(img)
            userid.text = item.Id.toString()
            uname.text = item.Name
            userName.text = item.UserName
        }
    }
}
