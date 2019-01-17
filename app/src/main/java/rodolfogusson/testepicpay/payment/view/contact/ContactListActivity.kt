package rodolfogusson.testepicpay.payment.view.contact

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_contact_list.*
import kotlinx.android.synthetic.main.contact_list_header.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.payment.viewmodel.contact.ContactListViewModel
import rodolfogusson.testepicpay.core.ui.showErrorDialog
import rodolfogusson.testepicpay.databinding.ActivityContactListBinding
import rodolfogusson.testepicpay.payment.model.contact.Contact
import rodolfogusson.testepicpay.payment.model.creditcard.CreditCard
import rodolfogusson.testepicpay.payment.view.priming.CreditCardPrimingActivity
import rodolfogusson.testepicpay.payment.view.register.CreditCardRegisterActivity

class ContactListActivity : AppCompatActivity() {

    private lateinit var contactListViewModel: ContactListViewModel
    private lateinit var adapter: ContactAdapter
    private var registeredCard: CreditCard? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        contactListViewModel = ViewModelProviders.of(this)
            .get(ContactListViewModel::class.java)

        ActivityContactListBinding.inflate(layoutInflater)
            .apply {
                setLifecycleOwner(this@ContactListActivity)
                viewModel = contactListViewModel
            }
        setupLayout()
        registerObservers()
    }

    private fun setupLayout() {
        supportActionBar?.hide()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled = false
    }

    private fun registerObservers() {
        contactListViewModel.contacts.observe(this, Observer {
            it?.error?.let { error ->
                showErrorDialog(error.localizedMessage, this)
                return@Observer
            }
            it?.data?.let { list ->
                adapter = ContactAdapter(list, ::onContactClicked)
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                searchView.search = adapter::filterBy
                progressBar.visibility = GONE
            }
        })

        contactListViewModel.registeredCard.observe(this, Observer {
            registeredCard = it
        })
    }

    private fun onContactClicked(contact: Contact) {
        if (registeredCard == null) {
            navigateTo(CreditCardPrimingActivity::class.java, contact)
        } else {
            navigateTo(CreditCardRegisterActivity::class.java, contact)
        }
    }

    private fun navigateTo(nextActivity: Class<out AppCompatActivity>, contact: Contact) {
        Intent(this, nextActivity).apply {
            putExtra(Contact.key, contact)
            startActivity(this)
        }
    }
}
