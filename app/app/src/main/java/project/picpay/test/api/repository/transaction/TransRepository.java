package project.picpay.test.api.repository.transaction;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import okhttp3.RequestBody;
import project.picpay.test.api.ApiInterface;
import project.picpay.test.home.model.DataLoadState;
import project.picpay.test.home.model.transaction_received.TransactionPost;
import project.picpay.test.home.model.transaction_sended.ResponseTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rodrigo Oliveira on 28/08/2018 for GitSuper.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public class TransRepository implements ITransRepository {

    private static final String TAG = TransRepository.class.getSimpleName();
    private ApiInterface apiInterface;
    private final MutableLiveData<DataLoadState> loadState;

    public TransRepository(ApiInterface api) {
        this.apiInterface = api;
        loadState = new MutableLiveData<>();
    }

    @Override
    public LiveData<DataLoadState> getDataLoadStatus() {
        return loadState;
    }

    @Override
    public LiveData<ResponseTransaction> getReturnTransaction(TransactionPost post) {
        final MutableLiveData<ResponseTransaction> data = new MutableLiveData<>();

        loadState.postValue(DataLoadState.LOADING);

        Log.i(TAG, "getReturnTransaction: " + new Gson().toJson(post));

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new Gson().toJson(post)));
        apiInterface.postTransaction(body).enqueue(new Callback<ResponseTransaction>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTransaction> call, @NonNull Response<ResponseTransaction> response) {
                loadState.postValue(DataLoadState.LOADED);
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTransaction> call, @NonNull Throwable t) {
                loadState.postValue(DataLoadState.FAILED);
                data.setValue(null);
            }
        });

        return data;
    }

}
