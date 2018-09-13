package com.picpay.david.davidrockpicpay.features.creditCard

import com.picpay.david.davidrockpicpay.DavidRockPicPayApplication
import com.picpay.david.davidrockpicpay.entities.CreditCard
import com.picpay.david.davidrockpicpay.features.base.BasePresenter
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import android.util.Log
import com.picpay.david.davidrockpicpay.R


class NewCreditCardPresenter : BasePresenter<NewCreditCardMvpView>() {

    fun addCreditCard(edCardHolder: String, edCardNumber: String, edCardCsc: String, edCardValidity: String, edCep: String) {

        if (!edCardHolder.isNullOrEmpty() || !edCardNumber.isNullOrEmpty() || !edCardCsc.isNullOrEmpty() || !edCardValidity.isNullOrEmpty() || !edCep.isNullOrEmpty()) {
            val box: Box<CreditCard> = DavidRockPicPayApplication.boxStore.boxFor()

            val cc = CreditCard(0, edCardHolder, edCardNumber, edCardValidity, edCardCsc.toInt(), true)
            box.put(cc)

            if(cc.Id <= 0 ){
                mvpView?.showSuccessDialog("Cartão cadastrado com sucesso!")
            }

            Log.d("PIC", "CreditCard Added, ID: " + cc.Id)
        } else {

            mvpView?.showErrorDialog(DavidRockPicPayApplication.instance.getString(R.string.newcard_error_all_required))
        }

    }
}