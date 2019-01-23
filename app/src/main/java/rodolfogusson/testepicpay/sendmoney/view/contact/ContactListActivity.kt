package rodolfogusson.testepicpay.sendmoney.view.contact

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
import rodolfogusson.testepicpay.sendmoney.viewmodel.contact.ContactListViewModel
import rodolfogusson.testepicpay.core.ui.showErrorDialog
import rodolfogusson.testepicpay.databinding.ActivityContactListBinding
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendmoney.view.payment.PaymentActivity
import rodolfogusson.testepicpay.sendmoney.view.priming.CardPrimingActivity

class ContactListActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactListViewModel
    private var registeredCard: CreditCard? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        viewModel = ViewModelProviders.of(this)
            .get(ContactListViewModel::class.java)

        ActivityContactListBinding.inflate(layoutInflater)
            .let {
                it.setLifecycleOwner(this@ContactListActivity)
                it.viewModel = viewModel
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
        viewModel.contacts.observe(this, Observer {
            progressBar.visibility = GONE
            it?.error?.let { error ->
                showErrorDialog(error.localizedMessage, this)
                return@Observer
            }
            it?.data?.let { list ->
                ContactAdapter(list, ::onContactClicked)
                    .apply {
                    recyclerView.adapter = this
                    notifyDataSetChanged()
                    searchView.search = ::filterBy
                }
            }
        })

        viewModel.lastRegisteredCard.observe(this, Observer {
            registeredCard = it
        })
    }

    private fun onContactClicked(contact: Contact) {
        if (registeredCard == null) {
            navigateTo(CardPrimingActivity::class.java, contact)
        } else {
            navigateTo(PaymentActivity::class.java, contact, registeredCard)
        }
    }

    private fun navigateTo(nextActivity: Class<out AppCompatActivity>,
                           contact: Contact,
                           creditCard: CreditCard? = null) {
        Intent(this, nextActivity).apply {
            putExtra(Contact.key, contact)
            creditCard?.let { putExtra(CreditCard.key, creditCard) }
            startActivity(this)
        }
    }
}
