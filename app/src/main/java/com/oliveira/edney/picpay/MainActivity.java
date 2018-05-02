package com.oliveira.edney.picpay;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.oliveira.edney.picpay.Fragment.MainFragment;

/* Activity principal */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Cor do titulo */
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorMenuText));
    }

    @Override
    protected void onStart() {
        super.onStart();

        /* Fragmento principal se houver internet*/
        if(connection()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, new MainFragment());
            fragmentTransaction.commit();
        }
        else {
            Toast.makeText(this,"Sem conexão :(", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    private boolean connection(){

        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;

        return networkInfo != null && networkInfo.isConnected();
    }
}
