package com.v1pi.picpay_teste.Controllers

import android.app.Activity.RESULT_OK
import android.content.Intent
import com.v1pi.picpay_teste.ChooseCreditCardActivity
import com.v1pi.picpay_teste.Domains.CreditCard
import com.v1pi.picpay_teste.Utils.ListCreditCardManager

class ChooseCreditCardActivityController(private val activity : ChooseCreditCardActivity) {
    private val listManager = ListCreditCardManager(activity)

    init {
        listManager.insertNewItem(CreditCard(1, "1111 1111 1111 1111", 700, "12/20"))
        listManager.insertNewItem(CreditCard(2, "2222 2222 2222 2222", 700, "12/20"))
        listManager.selectItem(0)
    }

    fun setResultParams(){
        val intent = Intent()
        intent.putExtra("number", listManager.selectedCreditCard().number)
        intent.putExtra("uid", listManager.selectedCreditCard().uid)
        intent.putExtra("cvv", listManager.selectedCreditCard().cvv)
        intent.putExtra("expiry_date", listManager.selectedCreditCard().expiry_date)
        activity.setResult(RESULT_OK, intent)
    }
}