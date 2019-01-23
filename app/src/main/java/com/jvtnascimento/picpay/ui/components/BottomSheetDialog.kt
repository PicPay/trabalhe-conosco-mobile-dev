package com.jvtnascimento.picpay.ui.components

import android.annotation.SuppressLint
import android.app.Dialog
import android.support.design.widget.BottomSheetDialogFragment
import android.view.View
import android.widget.TextView
import com.jvtnascimento.picpay.R
import com.jvtnascimento.picpay.dagger.modules.GlideApp
import com.jvtnascimento.picpay.models.CreditCard
import com.jvtnascimento.picpay.models.Transaction
import com.jvtnascimento.picpay.models.TransactionResponse
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import java.text.SimpleDateFormat


class BottomSheetDialog: BottomSheetDialogFragment() {

    private lateinit var userPicture: CircleImageView
    private lateinit var userUsername: TextView
    private lateinit var transactionDate: TextView
    private lateinit var transactionId: TextView
    private lateinit var creditCardInfo: TextView
    private lateinit var transactionValue: TextView
    private lateinit var transactionTotalValue: TextView

    private lateinit var transaction: Transaction
    private lateinit var creditCard: CreditCard

    @SuppressLint("RestrictedApi", "SimpleDateFormat")
    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)

        val contentView = View.inflate(context, R.layout.bottom_sheet_receipt, null)
        dialog!!.setContentView(contentView)

        this.userPicture = contentView.findViewById(R.id.userPicture)
        this.userUsername = contentView.findViewById(R.id.userUsername)
        this.transactionDate = contentView.findViewById(R.id.transactionDate)
        this.transactionId = contentView.findViewById(R.id.transactionId)
        this.creditCardInfo = contentView.findViewById(R.id.creditCardInfo)
        this.transactionValue = contentView.findViewById(R.id.transactionValue)
        this.transactionTotalValue = contentView.findViewById(R.id.transactionTotalValue)


        val user =  this.transaction.destination_user

        GlideApp.with(this)
            .load(user.img)
            .placeholder(com.jvtnascimento.picpay.R.color.imageViewBackground)
            .into(this.userPicture)

        this.userUsername.text = user.username

        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = transaction.timestamp.toLong()

        this.transactionDate.text = formatter.format(calendar.time)
        this.transactionId.text = getString(R.string.transaction_id_text) + " " + transaction.id.toString()
        this.creditCardInfo.text = getString(R.string.card_info_text) + " " + creditCard.firstFourNumbers
        this.transactionValue.text = "R$ " + "%.2f".format(transaction.value).replace(".", ",")
        this.transactionTotalValue.text = "R$ " + "%.2f".format(transaction.value).replace(".", ",")
    }

    fun setUpModels(transactionResponse: TransactionResponse, creditCard: CreditCard) {
        this.transaction = transactionResponse.transaction
        this.creditCard = creditCard
    }
}