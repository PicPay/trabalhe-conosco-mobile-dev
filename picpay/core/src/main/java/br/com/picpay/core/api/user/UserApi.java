package br.com.picpay.core.api.user;

import android.content.Context;
import android.support.annotation.NonNull;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;

import br.com.picpay.core.api.BaseApi;
import br.com.picpay.core.listerner.ResponseServer;
import br.com.picpay.core.modelResponse.UserResponse;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

@EBean(scope = EBean.Scope.Singleton)
public class UserApi implements IUserApi {

    @RootContext
    Context context;

    private BaseApi<UserRetrofit> authApi = new BaseApi<>(UserRetrofit.class);

    @Override
    public void getUsers(@NonNull final ResponseServer<ArrayList<UserResponse>> listerner) {
        authApi.getApi().getUsers().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Response<ArrayList<UserResponse>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<ArrayList<UserResponse>> response) {
                listerner.success(response.body());
            }

            @Override
            public void onError(Throwable e) {
                authApi.verifyErrorDefault(context, e, listerner);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
