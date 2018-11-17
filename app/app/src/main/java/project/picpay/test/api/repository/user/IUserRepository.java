package project.picpay.test.api.repository.user;

import android.arch.lifecycle.LiveData;

import java.util.List;

import project.picpay.test.home.model.DataLoadState;
import project.picpay.test.home.model.UserModel;

/**
 * Created by Rodrigo Oliveira on 17/11/2018 for PicPay Test.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public interface IUserRepository {

    LiveData<DataLoadState> getDataLoadStatus();

    LiveData<List<UserModel>> getAvailableUsers();

}