package project.picpay.test.api.repository.transaction;

import android.arch.lifecycle.LiveData;

import project.picpay.test.home.model.DataLoadState;
import project.picpay.test.home.model.transaction_received.TransactionPost;
import project.picpay.test.home.model.transaction_sended.ResponseTransaction;

/**
 * Created by Rodrigo Oliveira on 29/08/2018 for PnWeather.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public interface ITransRepository {

    LiveData<DataLoadState> getDataLoadStatus();

    LiveData<ResponseTransaction> getReturnTransaction(TransactionPost post);

}