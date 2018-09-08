package com.picpay.david.davidrockpicpay.features.sendMoney

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.blackcat.currencyedittext.CurrencyEditText
import com.google.gson.Gson
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.features.base.BaseActivity
import com.picpay.david.davidrockpicpay.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_send_money.*
import java.util.*
import kotlin.concurrent.schedule
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


class SendMoneyActivity : BaseActivity(), SendMoneyMvpView {

    private lateinit var user: User
    private lateinit var name: TextView
    private lateinit var userName: TextView
    private lateinit var userId: TextView
    private lateinit var imgUser: ImageView
    private lateinit var valor: CurrencyEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_money)

        buildView()


    }

    fun buildView() {
        if (!intent.hasExtra("user")) {

            showError(getString(R.string.user_not_found))

            Timer().schedule(5000) {
                finish()
            }
        } else {
            user = Gson().fromJson(intent.getStringExtra("user"), User::class.java)

            userId = tvId
            name = tvName
            userName = tvUserName
            imgUser = userImage
            valor = edValor
            valor.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(valor, InputMethodManager.SHOW_IMPLICIT)


            name.text = user.Name
            userId.text = "id: " + user.Id
            userName.text = user.UserName
            Picasso.get().load(user.Img).into(imgUser)
        }
    }

}
