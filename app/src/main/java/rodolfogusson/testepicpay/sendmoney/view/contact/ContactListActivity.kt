package rodolfogusson.testepicpay.sendmoney.view.contact

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_contact_list.*
import kotlinx.android.synthetic.main.contact_list_header.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.sendmoney.viewmodel.contact.ContactListViewModel
import rodolfogusson.testepicpay.core.utils.showErrorDialog
import rodolfogusson.testepicpay.databinding.ActivityContactListBinding
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendmoney.model.payment.Transaction
import rodolfogusson.testepicpay.sendmoney.view.payment.PaymentActivity
import rodolfogusson.testepicpay.sendmoney.view.priming.CardPrimingActivity
import rodolfogusson.testepicpay.sendmoney.viewmodel.contact.ContactViewModelFactory

class ContactListActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactListViewModel

    private var registeredCard: CreditCard? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transaction: Transaction? = intent.getParcelableExtra(Transaction.key)

        val binding: ActivityContactListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_contact_list)
        binding.setLifecycleOwner(this)

        viewModel = ViewModelProviders
            .of(this, ContactViewModelFactory(application, transaction))
            .get(ContactListViewModel::class.java)

        binding.viewModel = viewModel

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
        setupSlidingPanel()
    }

    private fun setupSlidingPanel() {
        slidingPanelLayout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
        slidingPanelLayout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {}

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {
                if (previousState == SlidingUpPanelLayout.PanelState.DRAGGING
                    && newState == SlidingUpPanelLayout.PanelState.COLLAPSED
                ) {
                    slidingPanelLayout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                }
            }
        })
    }

    private fun registerObservers() {
        observeContacts()
        observeLastRegisteredCard()
        observeTransactionCompleted()
    }


    private fun observeContacts() {
        viewModel.contacts.observe(this, Observer {
            contactsProgressBar.visibility = GONE
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
    }

    private fun observeLastRegisteredCard() {
        viewModel.lastRegisteredCard.observe(this, Observer {
            registeredCard = it
        })
    }

    private fun observeTransactionCompleted() {
        viewModel.transactionCompleted.observe(this, Observer {
            showTransactionReceipt()
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

    private fun showTransactionReceipt() {
        slidingPanelLayout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
    }
}
