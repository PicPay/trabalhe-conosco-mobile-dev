package test.edney.picpay.viewmodel

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import test.edney.picpay.model.PaymentResponseModel
import test.edney.picpay.model.ReceiptModel

class ReceiptDialogVM : ViewModel(){

      fun getReceiptFromJSON( json: String, cardNumber: String?): ReceiptModel? {
            val mGson = Gson()
            val model: ReceiptModel?
            val payment = mGson.fromJson(json, PaymentResponseModel::class.java)

            if (payment != null) {
                  model = ReceiptModel()
                  model.id = payment.transaction?.id.toString()
                  model.card = cardNumber ?: "-"
                  model.img = payment.transaction?.destinationUser?.img ?: "-"
                  model.status = payment.transaction?.status ?: "-"
                  model.timestamp = payment.transaction?.timestamp.toString()
                  model.value = payment.transaction?.value.toString()
                  model.userName = payment.transaction?.destinationUser?.username ?: "-"
            } else
                  model = null

            return model
      }

}