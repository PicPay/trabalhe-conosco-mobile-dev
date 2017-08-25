package br.com.dalcim.picpay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.dalcim.picpay.data.Payment;
import br.com.dalcim.picpay.data.remote.RepositoryRemote;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Payment payment = new Payment();
        payment.setCardNumber("1111111111111111");
        payment.setCvv(789);
        payment.setDestinationUserId(1002);
        payment.setExpiryData("01/18");
        payment.setValue(79.9);
        new RepositoryRemote().transaction(payment);
    }
}
