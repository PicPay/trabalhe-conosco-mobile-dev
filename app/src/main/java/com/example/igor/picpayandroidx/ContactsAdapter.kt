package com.example.igor.picpayandroidx

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.igor.androidxtest.Contact
//import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_list_item.view.*
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso

class ContactsAdapter(val contactsList: List<Contact>?, val onContactClick: OnContactClick, val context: Context) : RecyclerView.Adapter<UserHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return contactsList!!.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val transformation = RoundedTransformationBuilder()
                .cornerRadiusDp(30f)
                .oval(false)
                .build()

        Picasso.with(context)
                .load(contactsList!!.get(position).img)
                .placeholder(R.drawable.ic_person_outline_black_24dp)
                .error(R.drawable.ic_person_outline_black_24dp)
                .fit()
                .transform(transformation)
                .into(holder.ivContact)

        holder.tvContact.text = contactsList.get(position).name

        holder.clContact.setOnClickListener {
            onContactClick.onClick(contactsList.get(position))
        }
    }

}

class UserHolder (view: View): RecyclerView.ViewHolder(view) {
    val tvContact = view.tv_contacts_list_item
    val ivContact = view.iv_contacts_list_item
    val clContact = view.cl_contacts_list_item
}

interface OnContactClick {
    fun onClick(contact: Contact)
}
