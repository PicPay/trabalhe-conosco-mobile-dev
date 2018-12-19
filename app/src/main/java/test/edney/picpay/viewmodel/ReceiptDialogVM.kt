package test.edney.picpay.viewmodel

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import test.edney.picpay.model.PaymentResponseModel
import test.edney.picpay.model.ReceiptModel
import test.edney.picpay.util.AppUtil

class ReceiptDialogVM : ViewModel(){

      fun getReceiptFromJSON( json: String, cardNumber: String?): ReceiptModel? {
            val mGson = Gson()
            val model: ReceiptModel?
            val payment = mGson.fromJson(json, PaymentResponseModel::class.java)

            if (payment != null) {
                  val time = payment.transaction?.timestamp?.toLong()

                  model = ReceiptModel()
                  model.id = "Transação: "+payment.transaction?.id.toString()
                  model.card = "Cartão Master "+(cardNumber?.substring(0, 4) ?: "-")
                  model.img = payment.transaction?.destinationUser?.img ?: "-"
                  model.status = payment.transaction?.status ?: "-"
                  model.timestamp = AppUtil.formatTimeStamp(time) ?: "-"
                  model.value = "R$ "+(payment.transaction?.value.toString().replace(".", ","))
                  model.userName = payment.transaction?.destinationUser?.username ?: "-"
                  model.status = payment.transaction?.status ?: "-"
            } else
                  model = null

            return model
      }

}