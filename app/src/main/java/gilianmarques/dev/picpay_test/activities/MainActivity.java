package gilianmarques.dev.picpay_test.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import gilianmarques.dev.picpay_test.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_send_cash).setOnClickListener(this);
        //    startActivity(new Intent(this, AddCreditCard.class));

    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_send_cash) {
            startActivity(new Intent(this, SendCashActivity.class));
        }


    }
}
