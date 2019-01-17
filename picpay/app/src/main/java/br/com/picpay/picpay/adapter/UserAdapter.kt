package br.com.picpay.picpay.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.picpay.picpay.R
import br.com.picpay.picpay.model.User
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_contact_list.view.*

class UserAdapter (var context: Context,
                   var itemListener: ItemListener<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var list = ArrayList<User>(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val noteView = inflater.inflate(R.layout.item_contact_list, parent, false)
        return UserViewHolder(noteView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = list[position]
        holder.fillData(item, context)
        holder.itemView.setOnClickListener {
            itemListener.onClick(item)
        }
    }

    fun insertData(data: List<User>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(noteview: View): RecyclerView.ViewHolder(noteview){
        private val img = noteview.findViewById<CircleImageView>(R.id.item_contact_image)
        private val username = noteview.findViewById<TextView>(R.id.item_contact_username)
        private val name = noteview.findViewById<TextView>(R.id.item_contact_name)

        fun fillData(user: User, context: Context){
            username.text = user.username
            name.text = user.name

            Glide.with(context)
                .load(user.img)
                .into(img)
        }
    }
}