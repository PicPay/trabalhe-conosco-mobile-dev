package com.jvtnascimento.picpay.ui.views

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.jvtnascimento.picpay.R
import com.jvtnascimento.picpay.application.BaseApplication
import com.jvtnascimento.picpay.dagger.modules.GlideApp
import com.jvtnascimento.picpay.models.*
import com.jvtnascimento.picpay.presenter.CreditCardPresenter
import com.jvtnascimento.picpay.presenter.MainPresenter
import com.jvtnascimento.picpay.ui.components.BottomSheetDialog
import com.jvtnascimento.picpay.ui.components.Toaster
import com.jvtnascimento.picpay.ui.contracts.CreditCardViewContractInterface
import com.jvtnascimento.picpay.ui.contracts.MainViewContractInterface
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class PaymentActivity : AppCompatActivity(), CreditCardViewContractInterface, MainViewContractInterface {

    @BindView(R.id.content) lateinit var content: LinearLayout
    @BindView(R.id.userPicture) lateinit var userPicture: CircleImageView
    @BindView(R.id.userUsername) lateinit var userUsername: TextView
    @BindView(R.id.value) lateinit var value: EditText
    @BindView(R.id.prefix) lateinit var prefix: TextView
    @BindView(R.id.creditCardInfo) lateinit var creditCardInfo: TextView
    @BindView(R.id.editCreditCardButton) lateinit var editCreditCardButton: Button
    @BindView(R.id.bottomButton) lateinit var bottomButton: Button
    @BindView(R.id.progressBar) lateinit var progressBar: ProgressBar

    @Inject lateinit var creditCardPresenter: CreditCardPresenter
    @Inject lateinit var mainPresenter: MainPresenter

    private val CREDIT_CARD_RESULT: Int = 1
    private val EDIT_CREDIT_CARD_RESULT: Int = 2

    private var user: User? = null
    private var creditCard: CreditCard? = null
    private var result = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        ButterKnife.bind(this)

        if (this.intent.hasExtra("user"))
            this.user = this.intent.getSerializableExtra("user") as User

        this.configureComponents()
        this.configureView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CREDIT_CARD_RESULT) {
                if (data != null) {
                    val creditCard = data.getSerializableExtra("creditCard") as CreditCard
                    val shouldFinish = data.getBooleanExtra("shouldFinish", false)
                    if (!shouldFinish && creditCard !== this.creditCard) {
                        this.creditCard = creditCard
                        this.creditCardInfo.text = getString(R.string.card_info_text) + " " + creditCard.firstFourNumbers
                    }
                    else {
                        finish()
                    }
                }
            }
            else if (requestCode == EDIT_CREDIT_CARD_RESULT) {
                if (data != null) {
                    val creditCard = data.getSerializableExtra("creditCard") as CreditCard
                    if (creditCard !== this.creditCard) {
                        this.creditCard = creditCard
                        this.creditCardInfo.text = getString(R.string.card_info_text) + " " + creditCard.firstFourNumbers

                        if (value.text.toString() != "") {
                            bottomButton.background = ContextCompat.getDrawable(this, R.drawable.shape_bottom_button)
                            prefix.setTextColor(Color.parseColor("#21C15F"))
                        }
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun getCreditCard(creditCard: CreditCard?) {
        this.creditCard = creditCard
    }

    override fun showProgressBar() {
        this.content.visibility = View.GONE
        this.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        this.progressBar.visibility = View.GONE
        this.content.visibility = View.VISIBLE
    }

    override fun showError(error: Throwable) {
        Toaster.showError(error, this)
    }

    override fun showResult(transaction: TransactionResponse) {
        if (transaction.transaction.success) {
            this.result.putExtra("creditCard", this.creditCard)
            this.result.putExtra("transaction", transaction)
            setResult(Activity.RESULT_OK, result)
            finish()
        } else {
            this.hideProgressBar()
            val status = transaction.transaction.status
            Toaster.showMessage("Status: $status. O pagamento não pode ser efetuado =(", this)
        }
    }

    private fun configureComponents() {
        (application as BaseApplication).component.inject(this)
        this.creditCardPresenter.attach(this)
        this.mainPresenter.attach(this)
        this.creditCardPresenter.findOne()

        (this as AppCompatActivity).supportActionBar!!.title = ""
        (this as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (this as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        (this as AppCompatActivity).supportActionBar!!.elevation = 0f

        this.editCreditCardButton.setOnClickListener {
            this.onEditCreditCardButtonTap()
        }

        this.bottomButton.setOnClickListener {
            if (this.value.text.toString() != "" &&  this.creditCard != null) {
                this.onBottomButtonTap()
            }
        }

        val self = this
        this.value.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (value.text.toString() != "" && creditCard != null) {
                    bottomButton.background = ContextCompat.getDrawable(self, R.drawable.shape_bottom_button)
                    prefix.setTextColor(Color.parseColor("#21C15F"))
                }
                else {
                    bottomButton.background = ContextCompat.getDrawable(self, R.drawable.shape_disabled_bottom_button)
                    prefix.setTextColor(Color.parseColor("#7B7D7F"))
                }
            }
        })
    }

    private fun configureView() {
        if (this.creditCard != null) {
            this.creditCardInfo.text = getString(R.string.card_info_text) + " " + this.creditCard!!.firstFourNumbers
        } else {
            this.creditCardInfo.text = "Cartão não informado."
            showCreditCardPrimingActivity()
        }

        if (this.user != null) {

            GlideApp.with(this)
                .load(this.user!!.img)
                .placeholder(R.color.imageViewBackground)
                .into(userPicture)

            this.userUsername.text = this.user!!.username
        }
    }

    private fun showCreditCardPrimingActivity() {
        val intent = Intent(this, CreditCardPrimingActivity::class.java)
        startActivityForResult(intent, CREDIT_CARD_RESULT)
    }

    private fun onEditCreditCardButtonTap() {
        val intent = Intent(this, CreditCardRegisterActivity::class.java)
        intent.putExtra("creditCard", this.creditCard)
        startActivityForResult(intent, EDIT_CREDIT_CARD_RESULT)
    }

    private fun onBottomButtonTap() {
        if (this.creditCard != null && this.user != null) {
            val transaction = TransactionRequest(
                this.value.text.toString().toFloat(),
                this.creditCard!!.number.replace("-", ""),
                this.creditCard!!.expirationDate,
                this.creditCard!!.cvv,
                this.user!!.id
            )

            this.mainPresenter.pay(transaction)
        }
    }
}
