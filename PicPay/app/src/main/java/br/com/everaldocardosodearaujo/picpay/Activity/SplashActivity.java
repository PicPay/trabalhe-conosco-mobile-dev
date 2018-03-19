package br.com.everaldocardosodearaujo.picpay.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;

import br.com.everaldocardosodearaujo.picpay.App.FunctionsApp;
import br.com.everaldocardosodearaujo.picpay.Object.CreditCardObject;
import br.com.everaldocardosodearaujo.picpay.R;
import br.com.everaldocardosodearaujo.picpay.Repository.CreditCardRepository;

import static br.com.everaldocardosodearaujo.picpay.App.SessionApp.CREDIT_CARD;
import static br.com.everaldocardosodearaujo.picpay.App.SessionApp.TB_CREDIT_CARD;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.LoadTableCreditCard();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FunctionsApp.startActivity(SplashActivity.this,UsersActivity.class,null);
                FunctionsApp.closeActivity(SplashActivity.this);
            }
        }, 3000);
    }

    private void LoadTableCreditCard(){
        TB_CREDIT_CARD = new CreditCardRepository(SplashActivity.this);
        List<CreditCardObject> lstCreditCard = TB_CREDIT_CARD.select();
        if (lstCreditCard.size() > 0){CREDIT_CARD = lstCreditCard.get(0);}

    }
}
