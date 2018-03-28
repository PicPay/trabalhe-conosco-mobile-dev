package mobiledev.erickgomes.picpayapp;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import mobiledev.erickgomes.picpayapp.fragments.FragSelectPayment;

/**
 * Created by erickgomes on 24/03/2018.
 */

public class Payment extends AppCompatActivity {


    private FragSelectPayment mFragSelectPayment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initToolbar(); //seta as configuracoes da custom toolbar
        loadFragment();
    }

    private void initToolbar() {
        Toolbar datenPayToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(datenPayToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void loadFragment() {
        if (getIntent() != null) {
            Bundle infoTransaction = getIntent().getExtras();
            if (mFragSelectPayment == null) {
                mFragSelectPayment = new FragSelectPayment();
            }
            mFragSelectPayment.setArguments(infoTransaction); //Manda os dados da transação para o Fragmento (valor, amigo e etc)
            getFragmentManager().beginTransaction().replace(R.id.layout_container, mFragSelectPayment, FragSelectPayment.TAG).commit();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) { //Seleciona comportamento especial para o "voltar"da Toolbar
        switch (item.getItemId()) {                      //quando estiver no fragmento "FragAddCreditCard
            case android.R.id.home:
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                    return true;
                } else {
                    finish();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { //Trata a pilha de Fragmentos
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


}
