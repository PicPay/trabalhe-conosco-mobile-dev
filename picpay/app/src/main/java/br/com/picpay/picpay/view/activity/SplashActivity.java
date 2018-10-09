package br.com.picpay.picpay.view.activity;

import android.os.Handler;

import org.androidannotations.annotations.EActivity;

import br.com.picpay.picpay.R;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements Runnable {

    @Override
    public void init() {
        new Handler().postDelayed(this, 3000);
    }

    @Override
    public void run() {
        UsersActivity_.intent(this).start();
        finish();
    }
}
