package project.picpay.test.api.repository.user;

import android.arch.lifecycle.LiveData;

import java.util.List;

import project.picpay.test.home.model.DataLoadState;
import project.picpay.test.home.model.UserModel;

/**
 * Created by Rodrigo Oliveira on 29/08/2018 for PnWeather.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public interface IUserRepository {

    LiveData<DataLoadState> getDataLoadStatus();

    LiveData<List<UserModel>> getAvailableUsers();

}