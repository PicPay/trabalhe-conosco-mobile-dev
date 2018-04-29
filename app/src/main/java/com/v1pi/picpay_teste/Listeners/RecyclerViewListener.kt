package com.v1pi.picpay_teste.Listeners

import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.v1pi.picpay_teste.PaymentMethodActivity
import kotlinx.android.synthetic.main.user_item.view.*

class RecyclerViewListener(val img: String) : View.OnClickListener {

    override fun onClick(view: View?) {
        view?.let {
            val intent = Intent(view.context, PaymentMethodActivity::class.java)
            intent.putExtra("name", view.txt_name.text.toString())
            intent.putExtra("username", view.txt_username.text.toString())
            intent.putExtra("id", view.txt_id.text.substring(4))
            intent.putExtra("img", img)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(view.context, intent, null)
        }
    }

}