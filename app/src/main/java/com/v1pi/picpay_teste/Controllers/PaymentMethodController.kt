package com.v1pi.picpay_teste.Controllers

import com.v1pi.picpay_teste.Domains.User
import com.v1pi.picpay_teste.PaymentMethodActivity
import com.v1pi.picpay_teste.R
import com.v1pi.picpay_teste.Utils.DownloadImageTask
import kotlinx.android.synthetic.main.activity_payment_method.*

class PaymentMethodController(private val activity: PaymentMethodActivity) : Controller {
    lateinit var user : User
        private set

    override fun init() {
        val intent = activity.intent

        user = User(intent.getStringExtra("id").toInt(),
                intent.getStringExtra("name"),
                intent.getStringExtra("img"),
                intent.getStringExtra("username"))

        activity.txt_id.text = activity.getString(R.string.id, user.id.toString())
        activity.txt_name.text = user.name
        activity.txt_username.text = user.username
        DownloadImageTask(activity.user_image).execute(user.img)

    }

}