package com.v1pi.picpay_teste.Components

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.v1pi.picpay_teste.Domains.CreditCard
import com.v1pi.picpay_teste.R
import kotlinx.android.synthetic.main.activity_choose_credit_card.*
import java.util.*

class CreditCardItem(private val activity: Activity, val creditCard: CreditCard, private val clickListener: View.OnClickListener, private val bellowTo : Int? = null) {
    var id : Int? = null
        private set
    private val imgCreditCard = ImageView(activity)
    private val content = RelativeLayout(activity)
    private val textViewNumber = TextView(activity)
    private val imgCheck = ImageView(activity)

    private val doubleDefaultSpace = activity.resources.getDimension(R.dimen.double_default_space).toInt()
    private val defaultSpace = activity.resources.getDimension(R.dimen.default_space).toInt()


    init {
        id = Calendar.getInstance().timeInMillis.toInt()

        val rlCreditCards = activity.rl_credit_cards

        createContent()

        createCreditCardIcon()

        createTextViewNumber()

        createCheckIcon()

        rlCreditCards.addView(content)
    }

    fun select() {
        content.setBackgroundColor(ContextCompat.getColor(activity, R.color.white_10))
        imgCheck.visibility = View.VISIBLE
    }

    fun deselect() {
        content.setBackgroundColor(Color.TRANSPARENT)
        imgCheck.visibility = View.GONE
    }

    private fun createContent() {
        content.id = id!!
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        content.setPadding(doubleDefaultSpace, content.paddingTop, doubleDefaultSpace, content.paddingBottom)
        layoutParams.height = activity.resources.getDimension(R.dimen.height_credit_card_item).toInt()
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        bellowTo?.let {
            layoutParams.addRule(RelativeLayout.BELOW, bellowTo)
        }
        content.layoutParams = layoutParams
        content.setOnClickListener(clickListener)
    }

    private fun createCreditCardIcon() {
        //Configuring Image
        imgCreditCard.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_credit_card))
        imgCreditCard.layoutParams = createLayoutParamsToIcon()
        imgCreditCard.id = Calendar.getInstance().timeInMillis.toInt()


        content.addView(imgCreditCard)

    }

    private fun createTextViewNumber() {
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(defaultSpace, layoutParams.topMargin, layoutParams.rightMargin, layoutParams.bottomMargin)
        layoutParams.addRule(RelativeLayout.END_OF, imgCreditCard.id)
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
        textViewNumber.layoutParams = layoutParams

        textViewNumber.text = creditCard.number

        textViewNumber.setTextColor(ContextCompat.getColor(activity, R.color.white))
        textViewNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        textViewNumber.setTypeface(textViewNumber.typeface, Typeface.BOLD)

        content.addView(textViewNumber)
    }

    private fun createCheckIcon(){
        imgCheck.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_check_mark))

        val layoutParams = createLayoutParamsToIcon()
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
        imgCheck.layoutParams = layoutParams
        imgCheck.visibility = View.GONE

        content.addView(imgCheck)
    }

    private fun createLayoutParamsToIcon() : RelativeLayout.LayoutParams {
        val rllp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        rllp.width = activity.resources.getDimension(R.dimen.low_image).toInt()
        rllp.height = activity.resources.getDimension(R.dimen.low_image).toInt()
        rllp.addRule(RelativeLayout.CENTER_VERTICAL)
        return rllp
    }


}