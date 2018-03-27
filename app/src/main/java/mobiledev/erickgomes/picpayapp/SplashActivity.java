package mobiledev.erickgomes.picpayapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by erickgomes on 26/03/2018.
 */

public class SplashActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        showSplash();
    }

    private void showSplash() { //Mostra Splash Screen por 3 segundos até iniciar a aplicação
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToFriendList();
            }
        }, 3000);
    }

    private void goToFriendList() {
        Intent i = new Intent(this, FriendList.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
