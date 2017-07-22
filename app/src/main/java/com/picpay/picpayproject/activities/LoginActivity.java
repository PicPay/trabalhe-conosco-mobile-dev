package com.picpay.picpayproject.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.picpay.picpayproject.Preferences;
import com.picpay.picpayproject.R;
import com.picpay.picpayproject.database.DatabaseHelper;
import com.picpay.picpayproject.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText senha;
    private TextView cadastreSe;
    private Button logar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        associaComponentes();

        cadastreSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(i);
            }
        });

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean isValid = validaLogin(login.getText().toString(), senha.getText().toString());
                    if (isValid) {
                        Preferences p = new Preferences(LoginActivity.this);
                        p.salvarId(login.getText().toString());

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);

                    } else {
                        AlertDialog dialog;
                        AlertDialog.Builder erro = new AlertDialog.Builder(LoginActivity.this);
                        erro.setTitle("Erro:");
                        erro.setMessage("Login ou Senha incorreto");
                        erro.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog = erro.create();
                        dialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public boolean validaLogin(String user, String senha) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        Usuario usuario = dbHelper.consultarUsuario(user);
        if (usuario == null || usuario.getUsername() == null || usuario.getSenha() == null) {
            return false;
        }
        String informado = user + senha;
        String esperado = usuario.getUsername() + usuario.getSenha();

        if (informado.equals(esperado)) {
            return true;
        }
        return false;
    }

    public void associaComponentes() {
        login = (EditText) findViewById(R.id.et_login_user);
        senha = (EditText) findViewById(R.id.et_login_senha);
        cadastreSe = (TextView) findViewById(R.id.tv_login_cadastrese);
        logar = (Button) findViewById(R.id.button_login);
    }

}
