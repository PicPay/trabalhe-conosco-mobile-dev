package com.v1pi.picpay_teste.Listeners

import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.view.View
import com.v1pi.picpay_teste.ChooseCreditCardActivity
import com.v1pi.picpay_teste.PaymentMethodActivity

class FragmentCreditCardListener(private val activity: PaymentMethodActivity) : View.OnClickListener {
    override fun onClick(view: View?) {
        view?.let {
            val intent = Intent(view.context, ChooseCreditCardActivity::class.java)
            ActivityCompat.startActivityForResult(activity, intent, 1, null)
        }
    }
}