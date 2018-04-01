package br.com.vdsantana.picpaytest.transaction

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.EditText
import android.widget.Toast
import br.com.vdsantana.picpaytest.BaseActivity
import br.com.vdsantana.picpaytest.R
import br.com.vdsantana.picpaytest.creditcard.CreditCard
import br.com.vdsantana.picpaytest.creditcard.choose.ChooseCreditCardActivity
import br.com.vdsantana.picpaytest.transaction.di.DaggerTransactionActivityComponent
import br.com.vdsantana.picpaytest.transaction.di.TransactionActivityModule
import br.com.vdsantana.picpaytest.transaction.presenter.TransactionPresenter
import br.com.vdsantana.picpaytest.transaction.presenter.TransactionView
import br.com.vdsantana.picpaytest.users.User
import br.com.vdsantana.picpaytest.utils.picasso.CircleTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_transaction.*
import javax.inject.Inject

class TransactionActivity : BaseActivity(), TransactionView {

    @Inject
    lateinit var mTransactionPresenter: TransactionPresenter

    private val EXTRA_USER = "extra_user"
    private var selectedCreditCard: CreditCard? = null
    private var SELECT_CREDIT_CARD_REQUEST: Int = 102
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        initializeUI()
    }

    private fun initializeUI() {
        user = intent.getSerializableExtra(EXTRA_USER) as User
        Picasso.get()
                .load(user?.img)
                .placeholder(R.drawable.ic_default_avatar)
                .transform(CircleTransformation())
                .into(avatar);
        username.text = user?.username
    }

    fun clickChooseCreditCard(v: View?) {
        startActivityForResult(Intent(this, ChooseCreditCardActivity::class.java), SELECT_CREDIT_CARD_REQUEST)
    }

    fun clickPayTransaction(v: View?) {
        if (selectedCreditCard == null) {
            Toast.makeText(this, "É necessário escolher um cartão de crédito!", Toast.LENGTH_LONG).show()
            return
        }

        if (editAmount.text.length == 0 || editAmount.currencyDouble < 0.01) {
            Toast.makeText(this, "Digite um valor válido", Toast.LENGTH_LONG).show()
            return
        }

        showCvvDialog()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_CREDIT_CARD_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedCreditCard = data?.extras?.get("selected_card") as CreditCard
            txtFinalCard.text = getString(R.string.final_card, selectedCreditCard?.cardNumber!!.substring(selectedCreditCard?.cardNumber!!.length.minus(4)))
        }
    }

    override fun onTransactionRequestSuccess(transactionResponse: TransactionResponse) {
        if (transactionResponse.transaction.success) {
            Toast.makeText(this, "Transação realizada com sucesso!", Toast.LENGTH_LONG).show()
            finish()
        } else
            onError()
    }

    override fun showProgress() {
        progressTransaction.visibility = View.VISIBLE
        layForm.visibility = View.GONE
    }

    override fun onError() {
        Toast.makeText(this, "Falha ao realizar transação!", Toast.LENGTH_LONG).show()
        progressTransaction.visibility = View.GONE
        layForm.visibility = View.VISIBLE
    }

    override fun onActivityInject() {
        DaggerTransactionActivityComponent.builder().appComponent(getAppcomponent())
                .transactionActivityModule(TransactionActivityModule())
                .build()
                .inject(this)

        mTransactionPresenter.attachView(this)
    }

    fun showCvvDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        var cvvDialog: AlertDialog? = null
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_cvv, null)
        dialogBuilder.setView(dialogView)

        val editCVV = dialogView.findViewById<View>(R.id.editCVV) as EditText

        dialogBuilder.setTitle("CVV")
        dialogBuilder.setMessage("Digite o CVV do seu cartão")

        dialogBuilder.setPositiveButton("Continuar", { dialog, whichButton ->
            if (!editCVV.text.isEmpty()) {
                mTransactionPresenter.sendTransaction(selectedCreditCard?.cardNumber, editCVV.text.toString().toInt(), editAmount.currencyDouble, selectedCreditCard?.expiryDate, user?.id)
                cvvDialog?.dismiss()
            }
        })

        dialogBuilder.setNegativeButton("Cancelar", { dialog, whichButton ->
            cvvDialog?.dismiss()
        })

        cvvDialog = dialogBuilder.create()
        cvvDialog.show()
    }
}
