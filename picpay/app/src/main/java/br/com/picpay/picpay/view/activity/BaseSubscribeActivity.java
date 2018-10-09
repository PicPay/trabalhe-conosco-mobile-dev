package br.com.picpay.picpay.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.androidannotations.annotations.EBean;
import org.greenrobot.eventbus.EventBus;

@EBean
public class BaseSubscribeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
