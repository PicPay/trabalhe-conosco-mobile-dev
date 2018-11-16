package project.picpay.test.home.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import project.picpay.test.api.ApiClient;
import project.picpay.test.api.repository.transaction.TransRepository;
import project.picpay.test.home.model.DataLoadState;
import project.picpay.test.home.model.transaction_received.TransactionPost;
import project.picpay.test.home.model.transaction_sended.ResponseTransaction;

/**
 * Created by Rodrigo Oliveira on 13/11/2018 for app.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public class TransactionViewModel extends AndroidViewModel {

    private TransRepository repository;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = new TransRepository(ApiClient.get());
    }

    public LiveData<ResponseTransaction> getReturnTransaction(TransactionPost transactionPost) {
        return repository.getReturnTransaction(transactionPost);
    }

    public LiveData<DataLoadState> dataLoadStatus() {
        return repository.getDataLoadStatus();
    }

}
