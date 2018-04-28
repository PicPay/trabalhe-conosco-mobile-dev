package com.v1pi.picpay_teste.Utils

import android.app.Activity
import com.v1pi.picpay_teste.Components.CreditCardItem
import com.v1pi.picpay_teste.Domains.CreditCard
import com.v1pi.picpay_teste.Listeners.ListCreditCardChooseListener
import kotlinx.android.synthetic.main.activity_choose_credit_card.*

class ListCreditCardManager(private val activity: Activity) {
    private val list : MutableList<CreditCardItem> = mutableListOf()
    var selectedItem : Int? = null
        private set

    fun selectedCreditCard() : CreditCard? = if (selectedItem != null) list.filter { it.id == selectedItem }[0].creditCard else null

    val size = if (list.isNotEmpty()) list.size else 0

    fun insertNewItem(creditCard: CreditCard) {
        list.add(CreditCardItem(activity, creditCard, ListCreditCardChooseListener(this), list.lastOrNull()?.id))
    }

    fun selectItem(index : Int){
        list.getOrNull(index)?.select()
        selectedItem = list.getOrNull(index)?.id
    }

    fun clear() {
        if(list.isNotEmpty()) {
            activity.rl_credit_cards.removeAllViews()
            selectedItem = null
            list.clear()
        }
    }

    fun getIndexFromId(id : Int) : Int {
        list.mapIndexed { index, creditCardItem ->
            if(creditCardItem.creditCard.uid == id)
                return index
        }

        return 0
    }

    private fun deselectItem(index : Int){
        list.getOrNull(index)?.deselect()
    }

    fun changeSelectedItem(id : Int) {
        list.mapIndexed { index, creditCardItem ->
            if(creditCardItem.id == id)
                selectItem(index)
            else
                deselectItem(index)
        }
    }

}