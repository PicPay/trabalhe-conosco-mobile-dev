package test.edney.picpay.viewmodel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import test.edney.picpay.database.AppDatabase

class CardVM(application: Application) : AndroidViewModel(application){

    private val database = AppDatabase.get(application)
}