package com.v1pi.picpay_teste

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.v1pi.picpay_teste.Controllers.ChooseCreditCardActivityController
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

    override fun onResume() {
        super.onResume()
        controller.setUpCreditCardsFromDb()
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

    fun registerCreditCard(view : View) {
        val intent = Intent(this, CreateCreditCardActivity::class.java)
        startActivity(intent)

    }


}