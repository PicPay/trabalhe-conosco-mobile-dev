package com.picpay.david.davidrockpicpay.features.usersList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_card.view.*

class UsersAdapter(
        private val context: Context,
        private val users: ArrayList<User>,
        private val listener: OnItemClickListener) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private val usersQuery: ArrayList<User> = ArrayList()

    interface OnItemClickListener {
        fun onItemClick(item: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val veiculo = users[position]
        holder.bind(veiculo, this.listener, context)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id = itemView.tvId
        val name = itemView.tvName
        val userName = itemView.tvUserName
        var img = itemView.userImage

        fun bind(item: User, listener: OnItemClickListener, context: Context) {
            img.setOnClickListener {
                Log.d("PIC", "vai cara")
                Toast.makeText(context, "Name " + name, Toast.LENGTH_LONG).show()
            }
            Picasso.get().load(item.Img).into(img)
            id.text = item.Id.toString()
            name.text = item.Name
            userName.text = item.UserName
        }
    }

    fun swap(list: ArrayList<User>) {
        users.clear()
        users.addAll(list)
        usersQuery.clear()
        usersQuery.addAll(list)
        notifyDataSetChanged()
    }

    fun queryPlaca(newQuery: String) {
        val queryFilter = usersQuery.filter { s -> s.Name!!.toUpperCase().contains(newQuery.toUpperCase()) }
        users.clear()
        users.addAll(queryFilter as ArrayList<User>)
        notifyDataSetChanged()
    }


}