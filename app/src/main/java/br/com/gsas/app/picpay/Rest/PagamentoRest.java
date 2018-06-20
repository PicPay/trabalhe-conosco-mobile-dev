package br.com.gsas.app.picpay.Rest;

import br.com.gsas.app.picpay.Domain.Pagamento;
import br.com.gsas.app.picpay.Domain.Transaction;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PagamentoRest {

    @POST("tests/mobdev/transaction")
    Call<Transaction> postPagamento(@Body Pagamento pagamento);
}
