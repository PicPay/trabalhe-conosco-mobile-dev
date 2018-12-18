package br.com.kassianoresende.picpay.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.view.View
import android.widget.EditText
import br.com.kassianoresende.picpay.model.User
import br.com.kassianoresende.picpay.ui.adapters.UserAdapter
import br.com.kassianoresende.picpay.ui.viewstate.ListUsersState
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

    val userAdapter = UserAdapter()

    val errorClickListener = View.OnClickListener { loadUsers() }

    lateinit var users: List<User>

    val viewstate = MutableLiveData<ListUsersState>()

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
        viewstate.value = ListUsersState.StartLoading
    }

    fun onRetrieveUsersFinish(){
        viewstate.value = ListUsersState.FinishLoading
    }

    fun onRetrieveUsersSuccess(results:List<User>){
        users = results
        userAdapter.updateUserList(results)
        viewstate.value = ListUsersState.Sucess
    }

    fun onRetrieveUsersError(){
        viewstate.value = ListUsersState.LoadError
    }

    override fun onCleared() {
        super.onCleared()
        if(::subscription.isInitialized) subscription.dispose()
        if(::subscriptionSearch.isInitialized) subscriptionSearch.dispose()
    }

}