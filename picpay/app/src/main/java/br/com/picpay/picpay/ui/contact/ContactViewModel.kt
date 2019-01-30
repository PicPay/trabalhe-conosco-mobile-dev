package br.com.picpay.picpay.ui.contact

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.picpay.picpay.base.BaseViewModel
import br.com.picpay.picpay.model.User
import br.com.picpay.picpay.remote.EndPoints
import br.com.picpay.picpay.ui.priming.PrimingActivity
import br.com.picpay.picpay.ui.transaction.TransactionActivity
import br.com.picpay.picpay.utils.Constants.Companion.SAVE_CARD
import br.com.picpay.picpay.utils.Constants.Companion.USER
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

    private lateinit var sortedList: List<User>

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

    fun setActivityPriming(activity: AppCompatActivity){
        val intent = Intent(activity, PrimingActivity::class.java)
        activity.startActivityForResult(intent, SAVE_CARD)
    }

    fun setActivityTransaction(context: Context, user: User){
        val intent = Intent(context, TransactionActivity::class.java)
        intent.putExtra(USER, user)
        context.startActivity(intent)
    }

    fun sortList(query: String) {

        if (!query.isEmpty()) {
            val filteredList = ArrayList<User>()
            for (user in sortedList) {
                if (user.username!!.toLowerCase().contains(query) ||
                    user.name!!.toLowerCase().contains(query)){
                    filteredList.add(user)
                }
            }
            resultUsers.value = filteredList
        } else {
            resultUsers.value = sortedList
        }

    }

    private fun onSuccessResponse(result: List<User>?) {
        if (result != null) {
            resultUsers.value = result
            sortedList = result
        }
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