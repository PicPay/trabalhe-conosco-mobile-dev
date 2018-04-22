package gilianmarques.dev.picpay_test.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gilianmarques.dev.picpay_test.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_send_cash).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_send_cash) {
            startActivity(new Intent(this, SendCashActivity.class));
        }


    }
}
