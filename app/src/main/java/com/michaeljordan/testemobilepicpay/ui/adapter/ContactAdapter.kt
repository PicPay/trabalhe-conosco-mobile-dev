package com.michaeljordan.testemobilepicpay.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.michaeljordan.testemobilepicpay.databinding.ContactListItemBinding
import com.michaeljordan.testemobilepicpay.model.Contact
import com.michaeljordan.testemobilepicpay.ui.ContactFilter
import com.squareup.picasso.Picasso

class ContactAdapter(val context: Context, val listener: ContactAdapterOnClickListener) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(), Filterable {
    private var filter: ContactFilter? = null
    private var items: List<Contact> = ArrayList()
    private var itemsFiltered: ArrayList<Contact> = ArrayList()

    interface ContactAdapterOnClickListener {
        fun onClick(contact: Contact)
    }

    override fun getFilter(): Filter {
        if (filter == null) {
            filter = ContactFilter(itemsFiltered, this)
        }
        return filter as ContactFilter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContactListItemBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) = holder.bind(items[position])

    fun setData(items: List<Contact>) {
        this.items = items
        itemsFiltered = items as ArrayList<Contact>
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(private val binding: ContactListItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item: Contact) {
            binding.tvUsername.text = item.username
            binding.tvName.text = item.name

            Picasso.with(context)
                .load(item.image)
                //.error(R.drawable.ic_no_poster)
                .into(binding.ivContact)
        }

        override fun onClick(v: View?) {
            listener.onClick(items[adapterPosition])
        }

    }

}