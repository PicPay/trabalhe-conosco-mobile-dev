package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserData
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.repository.UserDataRepository

class UserDataViewModel constructor(application: Application) : AndroidViewModel(application) {

    private var mUserDataResository: UserDataRepository? = null

    private var mSaldo: LiveData<UserData>? = null

    init {
        mUserDataResository = UserDataRepository(application)
        mSaldo = mUserDataResository!!.getSaldo()
    }

    fun getSaldo() : LiveData<UserData> {
        return mSaldo!!
    }

    fun insert(saldo: UserData) {
        mUserDataResository!!.insert(saldo)
    }

    fun update(saldo: Double) {
        mUserDataResository!!.update(saldo)
    }

}