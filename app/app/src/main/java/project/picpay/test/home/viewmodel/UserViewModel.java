package project.picpay.test.home.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import project.picpay.test.api.ApiClient;
import project.picpay.test.api.repository.user.ListUserRepository;
import project.picpay.test.home.model.DataLoadState;
import project.picpay.test.home.model.UserModel;

/**
 * Created by Rodrigo Oliveira on 26/08/2018 for GitSuper.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public class UserViewModel extends AndroidViewModel {

    private ListUserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new ListUserRepository(ApiClient.get());
    }

    public LiveData<List<UserModel>> getAvailableUsers() {
        return repository.getAvailableUsers();
    }

    public LiveData<DataLoadState> dataLoadStatus() {
        return repository.getDataLoadStatus();
    }

}