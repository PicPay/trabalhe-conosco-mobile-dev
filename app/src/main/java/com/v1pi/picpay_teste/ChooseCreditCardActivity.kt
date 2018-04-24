package com.v1pi.picpay_teste

import android.graphics.Typeface
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.v1pi.picpay_teste.Controllers.ChooseCreditCardActivityController
import kotlinx.android.synthetic.main.activity_choose_credit_card.*
import java.util.*
import android.content.Intent
import android.view.View


class ChooseCreditCardActivity : AppCompatActivity() {
    lateinit var controller : ChooseCreditCardActivityController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_credit_card)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        controller = ChooseCreditCardActivityController(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                controller.setResultParams()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        controller.setResultParams()
        finish()
    }

    fun onSelectCardPressed(view : View) {
        controller.setResultParams()
        finish()
    }


}