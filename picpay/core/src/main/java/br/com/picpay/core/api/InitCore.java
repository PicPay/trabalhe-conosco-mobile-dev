package br.com.picpay.core.api;

import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class InitCore {

    public void init() {
        ApiClient.getInstance().setEndPoint("http://careers.picpay.com/tests/mobdev/");
        ApiClient.getInstance().withLog();
    }
}
