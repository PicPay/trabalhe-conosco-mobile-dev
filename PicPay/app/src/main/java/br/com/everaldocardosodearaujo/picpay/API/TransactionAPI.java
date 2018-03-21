package br.com.everaldocardosodearaujo.picpay.API;

import java.util.List;

import br.com.everaldocardosodearaujo.picpay.Object.TransactionObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by E. Cardoso de Araújo on 21/03/2018.
 */

public interface TransactionAPI {
    @POST("tests/mobdev/transaction")
    Call<TransactionObject> setTransaction(@Body TransactionObject transactionObject);
}
