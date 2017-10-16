package mobile.picpay.com.br.picpaymobile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import mobile.picpay.com.br.picpaymobile.application.MyApplication;
import mobile.picpay.com.br.picpaymobile.dao.UsuarioDAO;
import mobile.picpay.com.br.picpaymobile.entity.Usuario;
import mobile.picpay.com.br.picpaymobile.R;
import mobile.picpay.com.br.picpaymobile.infra.Util;

import static mobile.picpay.com.br.picpaymobile.application.MyApplication.getInstance;

public class LoginActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getObjetos();
    }

    private void getObjetos() {
        usuario = (EditText) findViewById(R.id.edUserLog);
        senha = (EditText) findViewById(R.id.edSenhaLog);
    }


    public void logar(View view) {
        Util util = new Util();
        if (util.validaCamposObrig(usuario) && util.validaCamposObrig(senha) ) {
            final Usuario u = new UsuarioDAO(this).getUsusario(usuario.getText().toString(), senha.getText().toString());

            if (u != null) {

                MyApplication.getInstance().setUsuario(u);
                Toast.makeText(getApplicationContext(), "Redirecionando...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "usuário ou senha invalidos, tente novamente.", Toast.LENGTH_SHORT).show();
            }



        }else{
            Toast.makeText(getApplicationContext(), "Existem Campos Obrigatórios a serem Peenchidos.", Toast.LENGTH_SHORT).show();
        }
    }
}
