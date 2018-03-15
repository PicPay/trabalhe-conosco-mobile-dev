package br.com.everaldocardosodearaujo.picpay.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.everaldocardosodearaujo.picpay.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it;
                it = new Intent(SplashActivity.this,ContactActivity.class);
                SplashActivity.this.startActivity(it);
                SplashActivity.this.finish();
            }
        }, 4000);
    }
}
