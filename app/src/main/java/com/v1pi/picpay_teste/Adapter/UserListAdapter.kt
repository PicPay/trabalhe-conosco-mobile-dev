package com.v1pi.picpay_teste.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.v1pi.picpay_teste.Domains.User
import com.v1pi.picpay_teste.Listeners.RecyclerViewListener
import com.v1pi.picpay_teste.R
import com.v1pi.picpay_teste.Utils.DownloadImageTask
import kotlinx.android.synthetic.main.user_item.view.*

class UserListAdapter(private val users : List<User>,
                      private val context: Context) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val user = users.get(position)
        holder?.bindView(user)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(itemView : View) :
            RecyclerView.ViewHolder(itemView) {

        fun bindView(user : User) {
            val id = itemView.txt_id
            val name = itemView.txt_name
            val img = itemView.user_image
            val username = itemView.txt_username

            id.text = itemView.context.getString(R.string.id, user.id.toString())
            name.text = user.name
            if(img.drawable == null)
                DownloadImageTask(img).execute(user.img)

            username.text = user.username

            itemView.setOnClickListener(RecyclerViewListener(user.img))
        }
    }

}
