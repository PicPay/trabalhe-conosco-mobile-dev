package br.com.kassianoresende.picpay.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.kassianoresende.picpay.R
import br.com.kassianoresende.picpay.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var userList: List<User>

    var itemClick: (User)->Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_user, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return if (::userList.isInitialized) {
            userList.size
        } else {
            0
        }
    }

    fun updateUserList(userList: List<User>){
        this.userList = userList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(userList[position].img)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.ivImg)

        holder.tvUserName.text = userList[position].username
        holder.tvFullName.text = userList[position].name

        holder.view.setOnClickListener{
            itemClick(userList[position])
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view =  itemView
        val ivImg = itemView.findViewById<ImageView>(R.id.ivImg)
        val tvUserName =  itemView.findViewById<TextView>(R.id.tvUserName)
        val tvFullName = itemView.findViewById<TextView>(R.id.tvfullName)
    }
}
