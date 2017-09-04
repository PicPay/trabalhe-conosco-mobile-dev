package br.com.dalcim.picpay.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.com.dalcim.picpay.data.Payment;
import br.com.dalcim.picpay.data.Transaction;
import br.com.dalcim.picpay.data.User;
import br.com.dalcim.picpay.jsonadapter.JsonPaymentAdapter;
import br.com.dalcim.picpay.jsonadapter.JsonTransactioAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Wiliam
 * @since 25/08/2017
 */
public class RepositoryRemoteImpl implements RepositoryRemote{

    private static final String BASE_URL = "http://careers.picpay.com/tests/mobdev/";

    @Override
    public void getUsers(final GetUsersCallback callback){
        MobDevService service = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MobDevService.class);

        service.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.body() == null || response.body().isEmpty()){
                    callback.emptyList();
                }else{
                    callback.onSucess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                callback.onFailure("Erro Inesperado");
            }
        });
    }

    @Override
    public void transaction(Payment payment, final TransactionCallback callback){
        final Gson gsonPaymentAdapter = new GsonBuilder()
                .registerTypeAdapter(Payment.class, new JsonPaymentAdapter())
                .registerTypeAdapter(Transaction.class, new JsonTransactioAdapter())
                .create();
//        final Gson gsonTransactionAdapter = new GsonBuilder().create();

        MobDevService service = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonPaymentAdapter))
//                .addConverterFactory(GsonConverterFactory.create(gsonTransactionAdapter))
                .build().create(MobDevService.class);

        service.transaction(payment).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if (response.body() == null || response.body() == null){
                    callback.onFailure("Erro Inesperado");
                    return;
                }

                Transaction transaction = response.body();

                if(transaction.isSuccess()){
                    callback.onSucess(transaction);
                }else{
                    callback.notApproved(transaction);
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                callback.onFailure("Erro Inesperado");
            }
        });
    }
}
