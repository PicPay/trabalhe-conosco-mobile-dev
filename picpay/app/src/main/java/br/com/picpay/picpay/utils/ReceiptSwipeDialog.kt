package br.com.picpay.picpay.utils

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.picpay.picpay.model.Transaction
import br.com.picpay.picpay.utils.Constants.Companion.RECEIPT
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import java.sql.Timestamp
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.widget.FrameLayout
import android.view.ViewTreeObserver



class ReceiptSwipeDialog: BottomSheetDialogFragment() {

    private lateinit var transaction: Transaction
    private lateinit var image: CircleImageView
    private lateinit var username: TextView
    private lateinit var timestamp: TextView
    private lateinit var transactionId: TextView
    private lateinit var card: TextView
    private lateinit var value: TextView
    private lateinit var totalValue: TextView

    companion object {
        fun newInstance(transaction: Transaction): ReceiptSwipeDialog {
            val receiptSwipeDialog = ReceiptSwipeDialog()
            val bundle = Bundle()
            bundle.putParcelable(RECEIPT, transaction)
            receiptSwipeDialog.arguments = bundle

            return receiptSwipeDialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(br.com.picpay.picpay.R.layout.fragment_receipt, container, false)

        view.viewTreeObserver.addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
            val dialog = dialog as BottomSheetDialog
            val bottomSheet = dialog.findViewById<FrameLayout>(android.support.design.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        })

        val args = arguments
        args?.let {
            transaction = it.getParcelable(RECEIPT)!!
        }

        image = view.findViewById(br.com.picpay.picpay.R.id.receipt_image)
        username = view.findViewById(br.com.picpay.picpay.R.id.receipt_username)
        timestamp = view.findViewById(br.com.picpay.picpay.R.id.receipt_timestamp)
        transactionId = view.findViewById(br.com.picpay.picpay.R.id.receipt_transaction_id)
        card = view.findViewById(br.com.picpay.picpay.R.id.receipt_card)
        value = view.findViewById(br.com.picpay.picpay.R.id.receipt_value)
        totalValue = view.findViewById(br.com.picpay.picpay.R.id.receipt_value_total)

        fillData(view.context)

        return view
    }

    private fun fillData(context: Context) {
        Glide.with(context)
            .load(transaction.destinationUser?.img)
            .into(image)

        username.text = transaction.destinationUser?.username
        val ts = Timestamp(transaction.timestamp)
        val dt = Date(ts.time)
        val calendar = Calendar.getInstance()
        calendar.time = dt
        val format = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        val date = format.format(dt)
        val hour = (calendar.get(Calendar.HOUR_OF_DAY).toString() + ":" + calendar.get(Calendar.MINUTE).toString())

        timestamp.text = context.getString(br.com.picpay.picpay.R.string.receipt_date, date, hour)
        transactionId.text = context.getString(br.com.picpay.picpay.R.string.receipt_transaction, transaction.id.toString())
        card.text = context.getString(br.com.picpay.picpay.R.string.receipt_card, transaction.cardCompany, transaction.cardLastNumbers)

        var formatted = NumberFormat
            .getCurrencyInstance(Locale("pt", "BR"))
            .format(transaction.value)

        formatted = formatted.replace("[R$]".toRegex(), "")
        value.text = getString(br.com.picpay.picpay.R.string.receipt_value, formatted)
        totalValue.text = getString(br.com.picpay.picpay.R.string.receipt_value, formatted)
    }
}