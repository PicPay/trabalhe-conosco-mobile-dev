package br.com.dalcim.picpay.data.remote;

import java.util.List;

import br.com.dalcim.picpay.data.Payment;
import br.com.dalcim.picpay.data.Transaction;
import br.com.dalcim.picpay.data.TransactionResponse;
import br.com.dalcim.picpay.data.User;
import br.com.dalcim.picpay.utils.Collection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Wiliam on 24/08/2017.
 */

public class RepositoryRemote {

    private MobDevService service;

    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://careers.picpay.com/tests/mobdev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(MobDevService.class);
    }

    public void getUsers(final GetUsersCallback callback){
        service.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(Collection.isEmpty(response.body())){
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

    public void transaction(Payment payment, final TransactionCallback callback){
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

    public interface TransactionCallback{
        void onSucess(Transaction transaction);
        void notApproved(Transaction transaction);
        void onFailure(String failure);
    }

    public interface GetUsersCallback{
        void onSucess(List<User> users);
        void emptyList();
        void onFailure(String failure);
    }
}
