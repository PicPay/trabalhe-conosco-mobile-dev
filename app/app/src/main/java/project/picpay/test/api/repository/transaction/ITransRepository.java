package project.picpay.test.api.repository.transaction;

import android.arch.lifecycle.LiveData;

import project.picpay.test.home.model.DataLoadState;
import project.picpay.test.home.model.transaction_received.TransactionPost;
import project.picpay.test.home.model.transaction_sended.ResponseTransaction;

/**
 * Created by Rodrigo Oliveira on 17/11/2018 for PicPay Test.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public interface ITransRepository {

    LiveData<DataLoadState> getDataLoadStatus();

    LiveData<ResponseTransaction> getReturnTransaction(TransactionPost post);

}