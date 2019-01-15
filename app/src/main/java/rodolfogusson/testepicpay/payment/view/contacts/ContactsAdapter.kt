package rodolfogusson.testepicpay.payment.view.contacts
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import rodolfogusson.testepicpay.payment.model.contact.Contact
import rodolfogusson.testepicpay.payment.view.contacts.ContactsAdapter.ContactHolder
import rodolfogusson.testepicpay.databinding.ContactItemBinding

class ContactsAdapter(private val contacts: List<Contact>,
                      private val clickListener: (Contact) -> Unit) : Adapter<ContactHolder>() {

    private var filteredContacts = contacts

    fun filterBy(text: String) {
        filteredContacts = contacts.filter {
            it.name.contains(text, true)
                    || it.username.contains(text, true)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContactItemBinding.inflate(inflater)
        return ContactHolder(binding)
    }

    override fun getItemCount(): Int = filteredContacts.size

    override fun onBindViewHolder(holder: ContactHolder, position: Int) =
        holder.bind(filteredContacts[position], clickListener)

    class ContactHolder(private val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contact, clickListener: (Contact) -> Unit) {
            binding.item = item
            binding.root.setOnClickListener { clickListener(item) }
            binding.executePendingBindings()
        }
    }
}