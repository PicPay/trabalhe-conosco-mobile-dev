package br.com.gsas.app.picpay.Carteira;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.gsas.app.picpay.R;

public class CarteiraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carteira);

        CarteiraFragment carteira = new CarteiraFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.carteira_activity, carteira, "Carteira").commit();
    }
}
