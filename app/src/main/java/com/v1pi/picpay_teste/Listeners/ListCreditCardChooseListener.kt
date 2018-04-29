package com.v1pi.picpay_teste.Listeners

import android.view.View
import com.v1pi.picpay_teste.Utils.ListCreditCardManager

class ListCreditCardChooseListener(private val listCreditCardManager: ListCreditCardManager) : View.OnClickListener {
    override fun onClick(view: View?) {
        view?.let {
            if(view.id != listCreditCardManager.selectedItem) {
                listCreditCardManager.changeSelectedItem(view.id)
            }
        }
    }

}