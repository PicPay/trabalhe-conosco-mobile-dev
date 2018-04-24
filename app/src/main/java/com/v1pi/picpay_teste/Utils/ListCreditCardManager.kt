package com.v1pi.picpay_teste.Utils

import android.app.Activity
import android.view.View
import com.v1pi.picpay_teste.Components.CreditCardItem
import com.v1pi.picpay_teste.Domains.CreditCard

class ListCreditCardManager(private val activity: Activity) : View.OnClickListener {
    private val list : MutableList<CreditCardItem> = mutableListOf()
    private var selectedItem : Int? = null

    fun selectedCreditCard() = list.filter { it.id == selectedItem }[0].creditCard

    val size = list.size

    fun insertNewItem(creditCard: CreditCard) {
        list.add(CreditCardItem(activity, creditCard, this, list.lastOrNull()?.id))
    }

    fun selectItem(index : Int){
        list.getOrNull(index)?.select()
        selectedItem = list.getOrNull(index)?.id
    }

    private fun deselectItem(index : Int){
        list.getOrNull(index)?.deselect()
    }

    private fun changeSelectedItem(id : Int) {
        list.mapIndexed { index, creditCardItem ->
            if(creditCardItem.id == id)
                selectItem(index)
            else
                deselectItem(index)
        }
    }

    override fun onClick(view: View?) {
        view?.let {
            if(view.id != selectedItem) {
                changeSelectedItem(view.id)
            }
        }
    }

}