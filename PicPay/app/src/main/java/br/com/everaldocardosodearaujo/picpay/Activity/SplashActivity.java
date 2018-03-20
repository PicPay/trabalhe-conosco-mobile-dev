package br.com.everaldocardosodearaujo.picpay.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import br.com.everaldocardosodearaujo.picpay.App.FunctionsApp;
import br.com.everaldocardosodearaujo.picpay.R;
import br.com.everaldocardosodearaujo.picpay.Repository.CreditCardRepository;
import static br.com.everaldocardosodearaujo.picpay.App.SessionApp.TB_CREDIT_CARD;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TB_CREDIT_CARD = new CreditCardRepository(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FunctionsApp.startActivity(SplashActivity.this,UsersActivity.class,null);
                FunctionsApp.closeActivity(SplashActivity.this);
            }
        }, 3000);
    }
}
