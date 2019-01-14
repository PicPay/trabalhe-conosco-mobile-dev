package rodolfogusson.testepicpay.payment.view.contacts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_contact_list.*
import kotlinx.android.synthetic.main.contact_list_header.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.payment.model.contact.Contact
import rodolfogusson.testepicpay.payment.viewmodel.contacts.ContactsListViewModel
import rodolfogusson.testepicpay.core.ui.showErrorDialog
import rodolfogusson.testepicpay.databinding.ActivityContactListBinding

class ContactsListActivity : AppCompatActivity() {

    private lateinit var contactsListViewModel: ContactsListViewModel
    private lateinit var adapter : ContactsAdapter

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
    }

    private fun setupLayout() {
        supportActionBar?.hide()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled = false
    }

    private fun registerObserver() {
        contactsListViewModel.contacts.observe(this, Observer {
            it?.error?.let { error ->
                showErrorDialog(error.localizedMessage, this)
                return@Observer
            }
            it?.data?.let { list ->
                adapter = ContactsAdapter(list, ::onContactClicked)
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                searchView.search = adapter::filterBy
            }
        })
    }

    private fun onContactClicked(contact: Contact) {
    }
}
