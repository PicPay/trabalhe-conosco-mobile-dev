package rodolfogusson.testepicpay.sendpayment.view.contact

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_contact_list.*
import kotlinx.android.synthetic.main.contact_list_header.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.utils.executeIfHasConnection
import rodolfogusson.testepicpay.core.utils.showErrorDialog
import rodolfogusson.testepicpay.databinding.ActivityContactListBinding
import rodolfogusson.testepicpay.sendpayment.model.contact.Contact
import rodolfogusson.testepicpay.sendpayment.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendpayment.view.payment.PaymentActivity
import rodolfogusson.testepicpay.sendpayment.view.priming.CardPrimingActivity
import rodolfogusson.testepicpay.sendpayment.viewmodel.contact.ContactListViewModel
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.*
import rodolfogusson.testepicpay.sendpayment.model.payment.Transaction

class ContactListActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactListViewModel

    private var registeredCard: CreditCard? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityContactListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_contact_list)
        binding.setLifecycleOwner(this)

        viewModel = ViewModelProviders
            .of(this).get(ContactListViewModel::class.java)

        binding.viewModel = viewModel

        ActivityContactListBinding.inflate(layoutInflater)
            .let {
                it.setLifecycleOwner(this@ContactListActivity)
                it.viewModel = viewModel
            }

        setupLayout()
        registerObservers()
    }

    override fun onNewIntent(newIntent: Intent?) {
        newIntent?.let {
            intent = it
            val transaction: Transaction? = intent.getParcelableExtra(Transaction.key)
            val cardUsedInTransaction: CreditCard? = intent.getParcelableExtra(CreditCard.key)
            if (transaction != null && cardUsedInTransaction != null) {
                viewModel.setTransactionData(transaction, cardUsedInTransaction)
            }
        }
    }

    private fun setupLayout() {
        supportActionBar?.hide()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled = false
        setupSlidingPanel()
    }

    private fun setupSlidingPanel() {
        slidingPanelLayout.panelState = HIDDEN
        slidingPanelLayout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {}

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {
                if (previousState == DRAGGING && newState == COLLAPSED
                ) {
                    slidingPanelLayout.panelState = HIDDEN
                }
            }
        })
    }

    override fun onBackPressed() {
        if (slidingPanelLayout.panelState == EXPANDED ||
            slidingPanelLayout.panelState == DRAGGING) {
                slidingPanelLayout.panelState = HIDDEN
            } else {
            super.onBackPressed()
        }
    }

    private fun registerObservers() {
        Handler().postDelayed({ observeTransactionCompleted() }, 200)
        executeIfHasConnection(::observeContacts)
        observeLastRegisteredCard()
    }


    private fun observeContacts() {
        contactsProgressBar.visibility = VISIBLE
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
        slidingPanelLayout.panelState = EXPANDED
    }
}
