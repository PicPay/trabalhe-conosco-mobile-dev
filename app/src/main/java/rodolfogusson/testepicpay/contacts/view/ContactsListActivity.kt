package rodolfogusson.testepicpay.contacts.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_contact_list.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.contacts.model.contact.Contact
import rodolfogusson.testepicpay.contacts.viewmodel.ContactsListViewModel
import rodolfogusson.testepicpay.core.ui.showErrorDialog
import rodolfogusson.testepicpay.databinding.ActivityContactListBinding

class ContactsListActivity : AppCompatActivity() {

    private lateinit var contactsListViewModel: ContactsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        contactsListViewModel = ViewModelProviders.of(this)
            .get(ContactsListViewModel::class.java)

        ActivityContactListBinding.inflate(layoutInflater)
            .apply {
                setLifecycleOwner(this@ContactsListActivity)
                viewModel = contactsListViewModel
            }
        setupLayout()
        registerObserver()
        contactsListViewModel.getUsers()
    }

    private fun setupLayout() {
        supportActionBar?.hide()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled = false
    }

    private fun registerObserver() {
        contactsListViewModel.users.observe(this, Observer {
            it?.error?.let { error ->
                showErrorDialog(error.localizedMessage, this)
                return@Observer
            }
            it?.data?.let { list ->
                val adapter = ContactsAdapter(list, ::onContactClicked)
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun onContactClicked(contact: Contact) {
    }
}
