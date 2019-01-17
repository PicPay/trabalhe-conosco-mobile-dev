package br.com.picpay.picpay.ui.contact

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.view.View
import br.com.picpay.picpay.base.BaseViewModel
import br.com.picpay.picpay.model.User
import br.com.picpay.picpay.remote.EndPoints
import br.com.picpay.picpay.ui.priming.PrimingActivity
import br.com.picpay.picpay.ui.register.RegisterCreditCardActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

class ContactViewModel: BaseViewModel() {

    @Inject
    lateinit var api: Retrofit
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<String> = MutableLiveData()
    val resultUsers: MutableLiveData<List<User>> = MutableLiveData()
    private lateinit var subscription: Disposable

    init {
        loadingUsers()
    }

    private fun loadingUsers() {
        val call = api.create(EndPoints::class.java)

        subscription = call.getUsers().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveUserStart() }
            .doOnTerminate { onRetrieveUserFinish() }
            .subscribe(
                {result -> onSuccessResponse(result)},
                {error -> onErrorResponse(error.message)})
    }

    fun setActivityPriming(context: Context){
        val intent = Intent(context, PrimingActivity::class.java)
        context.startActivity(intent)
    }

    fun setActivityTransaction(context: Context, user: User){

    }

    private fun onSuccessResponse(result: List<User>?) {
        resultUsers.value = result
    }

    private fun onErrorResponse(error: String?) {
        errorMessage.value = error
    }

    private fun onRetrieveUserStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveUserFinish() {
        loadingVisibility.value = View.GONE
    }
}