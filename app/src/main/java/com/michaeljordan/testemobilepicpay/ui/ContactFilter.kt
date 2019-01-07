package com.michaeljordan.testemobilepicpay.ui

import android.widget.Filter
import com.michaeljordan.testemobilepicpay.model.Contact
import com.michaeljordan.testemobilepicpay.ui.adapter.ContactAdapter
import java.util.ArrayList

class ContactFilter(private var contactList: ArrayList<Contact>, private val adapter: ContactAdapter) : Filter() {

    override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
        var query = constraint
        val results = Filter.FilterResults()

        if (query != null && query.isNotEmpty()) {
            query = query.toString().toLowerCase()

            val filteredList = ArrayList<Contact>()

            for (item in contactList) {
                if (item.name.toLowerCase().contains(query)) {
                    filteredList.add(item)
                }
            }

//            for (item in contactList) {
//                if (item.username.toLowerCase().contains(query)) {
//                    filteredList.add(item)
//                }
//            }

            results.count = filteredList.size
            results.values = filteredList
        } else {
            results.count = contactList.size
            results.values = contactList
        }

        return results
    }

    override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
        adapter.setData(results.values as ArrayList<Contact>)
    }
}