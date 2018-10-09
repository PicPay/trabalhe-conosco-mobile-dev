package br.com.picpay.core.api.payment;

import android.content.Context;
import android.support.annotation.NonNull;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import br.com.picpay.core.api.BaseApi;
import br.com.picpay.core.listerner.ResponseServer;
import br.com.picpay.core.modelRequest.PaymentRequest;
import br.com.picpay.core.modelResponse.PaymentResponse;
import br.com.picpay.core.modelResponse.TransactionResponse;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

@EBean(scope = EBean.Scope.Singleton)
public class PaymentApi implements IPaymentApi {

    @RootContext
    Context context;

    private BaseApi<PaymentRetrofit> paymentApi = new BaseApi<>(PaymentRetrofit.class);

    @Override
    public void executeTransaction(@NonNull String cardNumber, @NonNull String cvv, float value, @NonNull String expiryDate, @NonNull String userId, @NonNull final ResponseServer<TransactionResponse> listerner) {
        paymentApi.getApi().postPayment(new PaymentRequest(cardNumber, cvv, value, expiryDate, userId)).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Response<PaymentResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<PaymentResponse> response) {
                listerner.success(response.body().getTransactionResponse());
            }

            @Override
            public void onError(Throwable e) {
                paymentApi.verifyErrorDefault(context, e, listerner);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
