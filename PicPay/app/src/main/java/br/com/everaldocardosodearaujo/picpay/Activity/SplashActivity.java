package br.com.everaldocardosodearaujo.picpay.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.everaldocardosodearaujo.picpay.App.Functions;
import br.com.everaldocardosodearaujo.picpay.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Functions.startActivity(SplashActivity.this,UsersActivity.class,null);
                Functions.closeActivity(SplashActivity.this);
            }
        }, 4000);
    }
}
