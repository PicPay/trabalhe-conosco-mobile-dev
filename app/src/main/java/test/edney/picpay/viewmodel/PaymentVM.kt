package test.edney.picpay.viewmodel

import android.app.Application
import android.os.AsyncTask
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import test.edney.picpay.api.ApiClient
import test.edney.picpay.database.AppDatabase
import test.edney.picpay.database.CardEntity
import test.edney.picpay.model.PaymentRequestModel
import test.edney.picpay.model.PaymentResponseModel
import test.edney.picpay.util.MyLog

class PaymentVM(application: Application) : AndroidViewModel(application) {

    private val log = MyLog(PaymentVM::class.java.simpleName)
    private val api = ApiClient().getRequests()
    private val database = AppDatabase.get(application)
    val paymentResponse = MutableLiveData<PaymentResponseModel>()
    val cardSave = MutableLiveData<CardEntity>()

    init {
        cardSave.value = GetCardTask(database).execute().get()
    }

    fun requestPayment(value: Double, userId: Int){
        val card = if(cardSave.value != null) cardSave.value else GetCardTask(database).execute().get()

        if(card != null){
            val gson = Gson()
            val model = PaymentRequestModel()

            model.cardNumber = card.number?.replace(" ", "") ?: "-"
            model.cvv = card.cvv?.toInt()
            model.value = value
            model.expiryDate = card.expiration
            model.destinationUserId = userId

            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                gson.toJson(model)
            )

            log.showD("requestPayment", "model", gson.toJson(model))
            api.sendPayment(body).enqueue(object : retrofit2.Callback<PaymentResponseModel>{
                override fun onFailure(call: Call<PaymentResponseModel>, t: Throwable) {
                    log.showD("requestPayment", "onFailure", "fs")
                    paymentResponse.value = null
                }

                override fun onResponse(call: Call<PaymentResponseModel>, response: Response<PaymentResponseModel>) {
                    log.showD("requestPayment", "onResponse", response.body())
                    paymentResponse.value = response.body()
                }
            })
        }
    }

    class GetCardTask(private val db: AppDatabase?) : AsyncTask<Void, Void, CardEntity?>() {
        override fun doInBackground(vararg params: Void?): CardEntity? {
            return db?.cardDao()?.getCard()
        }
    }
}