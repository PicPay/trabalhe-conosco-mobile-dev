package com.picpay.david.davidrockpicpay.features.usersList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_card.view.*

class UsersAdapter(private val context: Context, private val users: ArrayList<User>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private lateinit var items: List<User>
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(item: User)
    }

    fun ContentAdapter(items: List<User>, listener: OnItemClickListener) {
        this.items = items
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position], listener)
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
        val id = itemView.tvId
        val name = itemView.tvName
        val userName = itemView.tvUserName
        var img = itemView.userImage

        fun bind(item: User, listener: OnItemClickListener) {
            Picasso.get().load(item.Img).into(img)
            id.text = item.Id.toString()
            name.text = item.Name
            userName.text = item.UserName

            itemView.setOnClickListener {
                listener.onItemClick(item)
            }

        }
    }


}