package rodolfogusson.testepicpay.payment.view.register

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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
import rodolfogusson.testepicpay.core.ui.dp
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
        observe(viewModel.cardNumber)
        observe(viewModel.cardHolderName)
        observe(viewModel.expiryDate)
        observe(viewModel.cvv)
    }

    private fun observe(data: MutableLiveData<String>) {
        data.observe(this, Observer {
            viewModel.onDataChanged(data)
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
