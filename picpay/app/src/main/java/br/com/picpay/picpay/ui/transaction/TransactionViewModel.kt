package br.com.picpay.picpay.ui.transaction

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.picpay.picpay.base.BaseViewModel
import br.com.picpay.picpay.db.CreditCard
import br.com.picpay.picpay.model.ResponseTransaction
import br.com.picpay.picpay.model.Payment
import br.com.picpay.picpay.remote.EndPoints
import br.com.picpay.picpay.repository.CreditCardRepository
import br.com.picpay.picpay.ui.contact.ContactActivity
import br.com.picpay.picpay.ui.register.RegisterCreditCardActivity
import br.com.picpay.picpay.utils.Constants.Companion.RECEIPT
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

class TransactionViewModel: BaseViewModel() {

    @Inject
    lateinit var creditCardRepo: CreditCardRepository
    @Inject
    lateinit var api: Retrofit

    val cardNumber = MutableLiveData<String>()
    val error = MutableLiveData<String>()
    val loadingVisibility = MutableLiveData<Int>()
    val responseTransaction = MutableLiveData<ResponseTransaction>()

    private lateinit var subscription: Disposable
    private val creditCard: CreditCard? = null
    init {
        loadCreditCard()
    }

    fun loadCreditCard() {
        subscription = creditCardRepo.getCreditCard()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {result-> onSuccessResponseDatabase(result)},
                {error-> onErrorResponse(error.message)})
    }

    fun startTransaction (value: Float, userId: Int) {
        if (creditCard != null) {
            val payment = Payment()
            payment.cardNumber = creditCard.cardNumber
            payment.cvv = creditCard.cvv
            payment.expiryDate = creditCard.expiryDate
            payment.value = value
            payment.destinationUserId = userId

            val call = api.create(EndPoints::class.java)

            subscription = call.sendTransaction(payment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveTransactionStart() }
                .doOnTerminate { onRetrieveTransactionFinish() }
                .subscribe(
                    {result -> onSuccessResponseService(result)},
                    {error -> onErrorResponse(error.message)})

        } else loadCreditCard()
    }

    fun setActivityRegister(context: Context){
        val intent = Intent(context, RegisterCreditCardActivity::class.java)
        context.startActivity(intent)
    }

    fun setActivityContact(activity: AppCompatActivity){
        val intent = Intent(activity, ContactActivity::class.java)
        intent.putExtra(RECEIPT, responseTransaction.value)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity.startActivity(intent)
        activity.finish()
    }

    private fun onRetrieveTransactionStart() {
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveTransactionFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onSuccessResponseService(result: ResponseTransaction?) {
        responseTransaction.value = result
    }

    private fun onSuccessResponseDatabase(result: List<CreditCard>?) {
        if (result != null) {
            cardNumber.value = result[0].cardNumber
        } else onErrorResponse(null)
    }

    private fun onErrorResponse(error: String?) {
        this.error.value = error
    }
}