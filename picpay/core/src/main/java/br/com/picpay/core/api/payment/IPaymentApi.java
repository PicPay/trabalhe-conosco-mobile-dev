package br.com.picpay.core.api.payment;

import android.support.annotation.NonNull;

import br.com.picpay.core.listerner.ResponseServer;
import br.com.picpay.core.modelResponse.TransactionResponse;

public interface IPaymentApi {

    void executeTransaction(@NonNull String cardNumber, @NonNull String cvv, float value, @NonNull String expiryDate, @NonNull String userId, @NonNull final ResponseServer<TransactionResponse> listerner);
}
