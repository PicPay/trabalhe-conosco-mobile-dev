package project.picpay.test.creditcard.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import project.picpay.test._db.AppDatabase;
import project.picpay.test.creditcard.model.CreditCardModel;

/**
 * Created by Rodrigo Oliveira on 17/11/2018 for PicPay Test.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public class CreditCardViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public CreditCardViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public LiveData<List<CreditCardModel>> getListCards() {
        return appDatabase.creditCardDao().getAllCreditCards();
    }

    public void inserNewCard(CreditCardModel creditCardModel) {
        new insertAsyncTask(appDatabase).execute(creditCardModel);
    }

    private static class insertAsyncTask extends AsyncTask<CreditCardModel, Void, Void> {
        private AppDatabase db;

        insertAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final CreditCardModel... params) {
            db.creditCardDao().newCreditCard(params[0]);
            return null;
        }
    }

}