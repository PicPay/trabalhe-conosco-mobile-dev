package com.picpay.picpayproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.picpay.picpayproject.R;
import com.picpay.picpayproject.database.DatabaseHelper;
import com.picpay.picpayproject.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText nome;
    private EditText senha;
    private EditText confSenha;
    private EditText email;
    private EditText username;

    private Button cadastrar;
    private Usuario usuario;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        associaComponentes();
        databaseHelper = new DatabaseHelper(this);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {

        usuario = new Usuario();

        usuario.setNome(nome.getText().toString());
        usuario.setSenha(senha.getText().toString());
        usuario.setEmail(email.getText().toString());
        usuario.setUsername(username.getText().toString());

        if (!usuario.getNome().equals("") && !usuario.getSenha().equals("") && !usuario.getEmail().equals("")
                && !usuario.getUsername().equals("") && validaSenha(senha.getText().toString(), confSenha.getText().toString())) {
            databaseHelper.inserirUsuario(usuario.getNome(), usuario.getEmail(), usuario.getUsername(), usuario.getSenha());
            Toast.makeText(CadastroActivity.this, "Cadastro feito com Sucesso", Toast.LENGTH_SHORT).show();
            abrirTelaLogin();

        } else {
            Toast.makeText(CadastroActivity.this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show();
        }


    }

    private void associaComponentes() {
        nome = (EditText) findViewById(R.id.et_cadastro_nome);
        senha = (EditText) findViewById(R.id.et_cadastro_senha);
        confSenha = (EditText) findViewById(R.id.et_cadastro_csenha);
        email = (EditText) findViewById(R.id.et_cadastro_email);
        username = (EditText) findViewById(R.id.et_cadastro_username);
        cadastrar = (Button) findViewById(R.id.button_cadastrar);
    }

    private boolean validaSenha(String senha, String confirmaSenha) {
        if (senha.equals(confirmaSenha)) {
            return true;
        } else
            return false;
    }

    private void abrirTelaLogin() {
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
