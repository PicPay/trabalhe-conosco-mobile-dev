package project.picpay.test.api.repository.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import project.picpay.test.api.ApiInterface;
import project.picpay.test.home.model.DataLoadState;
import project.picpay.test.home.model.UserModel;
import project.picpay.test._util.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rodrigo Oliveira on 17/11/2018 for PicPay Test.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public class ListUserRepository implements IUserRepository {

    private static final String TAG = ListUserRepository.class.getSimpleName();
    private ApiInterface apiInterface;
    private final MutableLiveData<DataLoadState> loadState;

    public ListUserRepository(ApiInterface api) {
        this.apiInterface = api;
        loadState = new MutableLiveData<>();
    }

    @Override
    public LiveData<DataLoadState> getDataLoadStatus() {
        return loadState;
    }

    @Override
    public LiveData<List<UserModel>> getAvailableUsers() {
        loadState.postValue(DataLoadState.LOADING);

        final MutableLiveData<List<UserModel>> data = new MutableLiveData<>();

        apiInterface.getAvailableUsers().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserModel>> call, @NonNull Response<List<UserModel>> response) {
                Logger.withTag(TAG).log(call.request().url().toString());
                loadState.postValue(DataLoadState.LOADED);
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<UserModel>> call, @NonNull Throwable t) {
                Logger.withTag(TAG).log("onFailure -> " + t.getMessage());
                loadState.postValue(DataLoadState.FAILED);
                data.setValue(null);
            }
        });

        return data;
    }

}
