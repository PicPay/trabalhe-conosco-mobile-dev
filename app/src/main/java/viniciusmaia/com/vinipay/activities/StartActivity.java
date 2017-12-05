package viniciusmaia.com.vinipay.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import viniciusmaia.com.vinipay.util.ControleSessao;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent activityIntent;
        ControleSessao controleSessao = new ControleSessao(this);

        if (controleSessao.isUsuarioLogado()){
            activityIntent = new Intent(this, MainActivity.class);
        }
        else {
            activityIntent = new Intent(this, LoginActivity.class);
        }

        startActivity(activityIntent);
        finish();
    }
}
