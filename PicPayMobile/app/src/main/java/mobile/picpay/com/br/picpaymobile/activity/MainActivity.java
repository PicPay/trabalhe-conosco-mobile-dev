package mobile.picpay.com.br.picpaymobile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mobile.picpay.com.br.picpaymobile.dao.UsuarioDAO;
import mobile.picpay.com.br.picpaymobile.entity.Usuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UsuarioDAO usuarioDAO = new UsuarioDAO(this);

        if (usuarioDAO.getAll().size() == 0) {
            Intent intent = new Intent(MainActivity.this, PrimeiroLoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        //setContentView(R.layout.activity_main);
    }
}
