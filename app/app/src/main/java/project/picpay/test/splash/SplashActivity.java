package project.picpay.test.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import project.picpay.test.home.view.HomeActivity;

/**
 * Created by Rodrigo Oliveira on 17/11/2018 for PicPay Test.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }

}