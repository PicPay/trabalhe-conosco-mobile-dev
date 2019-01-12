package rodolfogusson.testepicpay.contacts.view
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import rodolfogusson.testepicpay.contacts.model.contact.Contact
import rodolfogusson.testepicpay.contacts.view.ContactsAdapter.ContactHolder
import rodolfogusson.testepicpay.databinding.ContactItemBinding

class ContactsAdapter(private val contacts: List<Contact>) : Adapter<ContactHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContactItemBinding.inflate(inflater)
        return ContactHolder(binding)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactHolder, position: Int) = holder.bind(contacts[position])

    class ContactHolder(private val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contact){
            binding.item = item
            binding.executePendingBindings()
        }
    }
}