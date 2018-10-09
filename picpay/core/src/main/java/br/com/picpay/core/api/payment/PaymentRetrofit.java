package br.com.picpay.core.api.payment;

import br.com.picpay.core.modelRequest.PaymentRequest;
import br.com.picpay.core.modelResponse.PaymentResponse;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentRetrofit {

    @POST("transaction")
    Observable<Response<PaymentResponse>> postPayment(@Body PaymentRequest paymentRequest);
}
