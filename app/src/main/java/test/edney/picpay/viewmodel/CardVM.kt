package test.edney.picpay.viewmodel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import test.edney.picpay.database.AppDatabase

class CardVM(application: Application) : AndroidViewModel(application) {

      var hasCard = false

      init {
            hasCard = HasCardTask(AppDatabase.get(application))
                  .execute()
                  .get()
      }

      class HasCardTask(private var database: AppDatabase?) : AsyncTask<Void, Void, Boolean>() {
            override fun doInBackground(vararg params: Void?): Boolean {
                  return database?.cardDao()?.hasCard() ?: false
            }
      }
}