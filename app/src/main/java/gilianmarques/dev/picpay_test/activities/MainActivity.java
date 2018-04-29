package gilianmarques.dev.picpay_test.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.utils.MyApp;



/*
 * Bibliotecas que teriam salvo horas, senão dias de trabalho caso isso não fosse um teste:
 *
 *  Picasso
 *  CircleImageView
 *  volley
 *  Gson
 *  credit_card_number
 *  SQLCypher
 */


public class MainActivity extends MyActivity implements View.OnClickListener {
    private ConnectivityManager mConnectivityManager;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_send_cash).setOnClickListener(this);
        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    protected void onStart() {
        registerReceiver(networkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")); // register the receiver
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(networkChangeReceiver); // unregister the receiver
        super.onDestroy();
    }

    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_send_cash) {
            startActivity(new Intent(this, SendCashActivity.class));
        }
    }

    BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("MainActivity.onReceive conexao mudou");
            if (mConnectivityManager == null) return;

            NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();

            if (activeNetwork == null) {
                System.out.println("MainActivity.onReceive sem conexao");
                Activity mActivity = ((MyApp) MainActivity.this.getApplicationContext()).getCurrentActivity();

                mAlertDialog = new AlertDialog.Builder(mActivity, R.style.Theme_AppCompat_DayNight).create();
                mAlertDialog.setMessage(getString(R.string.voce_esta_offline));
                mAlertDialog.setTitle(getString(R.string.sem_conexao_com_a));
                mAlertDialog.setIcon(R.drawable.vec_alert);
                mAlertDialog.setCancelable(false);
                mAlertDialog.show();

            } else {
                System.out.println("MainActivity.onReceive  conectado");
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                    System.out.println("MainActivity.onReceive fechando dialogo");
                }
            }
        }
    };

}
