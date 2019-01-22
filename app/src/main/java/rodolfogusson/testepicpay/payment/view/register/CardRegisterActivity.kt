package rodolfogusson.testepicpay.payment.view.register

import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.activity_card_register.*
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.ui.customize
import rodolfogusson.testepicpay.databinding.ActivityCardRegisterBinding
import rodolfogusson.testepicpay.payment.model.contact.Contact
import rodolfogusson.testepicpay.payment.viewmodel.register.CardRegisterViewModel

class CardRegisterActivity : AppCompatActivity() {

    var contact: Contact? = null
    lateinit var viewModel: CardRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityCardRegisterBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_card_register)
        binding.setLifecycleOwner(this)

        viewModel = ViewModelProviders.of(this).get(CardRegisterViewModel::class.java)

        binding.viewModel = viewModel

        contact = intent.getParcelableExtra(Contact.key)
        setupLayout()
        registerObservers()
    }

    private fun setupLayout() {
        supportActionBar?.customize()
        mask(cardNumber, "[0000] [0000] [0000] [0000]")
        mask(expiryDate, "[00]/[00]")
        mask(cvv, "[000]")
    }

    private fun registerObservers() {
        observeField(viewModel.cardNumber)
        observeField(viewModel.cardHolderName)
        observeField(viewModel.expiryDate)
        observeField(viewModel.cvv)
        viewModel.saveButtonVisible.observe(this, Observer { visible ->
            if (visible) scrollToTheBottom()
        })
    }

    private fun scrollToTheBottom() {
        scrollview.postDelayed({
            scrollview.scrollTo(0, scrollview.bottom + 500)
        }, 100)
    }

    private fun observeField(data: MutableLiveData<String>) {
        data.observe(this, Observer {
            viewModel.onFieldDataChanged(data)
        })
    }

    private fun mask(editText: EditText, mask: String) {
        val listener = MaskedTextChangedListener(mask, editText)
        editText.addTextChangedListener(listener)
        editText.onFocusChangeListener = listener
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
