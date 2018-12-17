package br.com.kassianoresende.picpay.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.view.View
import android.widget.EditText
import br.com.kassianoresende.picpay.R
import br.com.kassianoresende.picpay.model.User
import br.com.kassianoresende.picpay.ui.adapters.UserAdapter
import br.com.kassianoresende.picpay.usecase.GetUsersUseCase
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel: BaseViewModel() {

    @Inject
    lateinit var useCase: GetUsersUseCase

    private lateinit var subscription : Disposable
    private lateinit var subscriptionSearch : Disposable

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val userAdapter = UserAdapter()

    val errorClickListener = View.OnClickListener { loadUsers() }

    lateinit var users: List<User>


    fun searchUser(editText:EditText){

        subscriptionSearch = RxTextView.textChangeEvents(editText)
            .skip(1)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.text()
            }
            .switchMap { filterUser(it, users) }
            .subscribe {
                userAdapter.updateUserList(it)
            }
    }

    fun filterUser(query:CharSequence, usersList: List<User>):Observable<List<User>> =
        Observable.just(usersList.filter {
            it.username.toLowerCase().contains(query.toString().toLowerCase())
        })


    fun loadUsers(){

        subscription = useCase
            .getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveUsersStart() }
            .doOnTerminate { onRetrieveUsersFinish() }
            .subscribe(
                { result->
                    onRetrieveUsersSuccess(result)
                }, {
                    onRetrieveUsersError()
                }
            )
    }


    fun onRetrieveUsersStart(){
        loading.value = true
    }

    fun onRetrieveUsersFinish(){
        loading.value = false
    }

    fun onRetrieveUsersSuccess(results:List<User>){
        users = results
        userAdapter.updateUserList(results)
    }

    fun onRetrieveUsersError(){
        errorMessage.value = R.string.user_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
        subscriptionSearch.dispose()
    }

}