package br.com.dalcim.picpay.data.remote;

import java.util.List;

import br.com.dalcim.picpay.data.Payment;
import br.com.dalcim.picpay.data.Transaction;
import br.com.dalcim.picpay.data.TransactionResponse;
import br.com.dalcim.picpay.data.User;
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
        MobDevService service = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MobDevService.class);

        service.transaction(payment).enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                if (response.body() == null || response.body().getTransaction() == null){
                    callback.onFailure("Erro Inesperado");
                    return;
                }

                Transaction transaction = response.body().getTransaction();

                if(transaction.isSuccess()){
                    callback.onSucess(transaction);
                }else{
                    callback.notApproved(transaction);
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
            }
        });
    }
}
