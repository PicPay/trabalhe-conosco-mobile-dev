package com.michaeljordan.testemobilepicpay.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michaeljordan.testemobilepicpay.databinding.ContactListItemBinding
import com.michaeljordan.testemobilepicpay.model.Contact
import com.squareup.picasso.Picasso

class ContactAdapter(val context: Context, val listener: ContactAdapterOnClickListener) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var items: List<Contact> = ArrayList()
    private var itemsFiltered: List<Contact> = ArrayList()

    interface ContactAdapterOnClickListener {
        fun onClick(contact: Contact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContactListItemBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) = holder.bind(items[position])

    fun setData(movies: List<Contact>) {
        items = movies
        itemsFiltered = movies
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(private val binding: ContactListItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item: Contact) {
            binding.tvUsername.text = item?.username
            binding.tvName.text = item?.name

            Picasso.with(context)
                .load(item?.image)
                //.error(R.drawable.ic_no_poster)
                .into(binding.ivContact)
        }

        override fun onClick(v: View?) {
            listener.onClick(items[adapterPosition])
        }

    }

}