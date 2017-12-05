package viniciusmaia.com.vinipay.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import viniciusmaia.com.vinipay.R;
import viniciusmaia.com.vinipay.modelo.Usuario;
import viniciusmaia.com.vinipay.util.ControleSessao;

public class CadastroActivity extends AppCompatActivity {
    private EditText editNomeCompleto;
    private EditText editUsuario;
    private EditText editSenha;
    private EditText editConfirmaSenha;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);
        inflateToolbar();

        if (realm == null){
            realm = Realm.getDefaultInstance();
        }

        editNomeCompleto = (EditText) findViewById(R.id.editNomeCompleto);
        editUsuario = (EditText) findViewById(R.id.editUsuario);
        editSenha = (EditText) findViewById(R.id.editSenha);
        editConfirmaSenha = (EditText) findViewById(R.id.editConfirmeSenha);

        Button botaoSalvar = (Button) findViewById(R.id.botaoSalvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCadastroValido()){
                    Usuario usuario = new Usuario();

                    usuario.setNome(editNomeCompleto.getText().toString());
                    usuario.setUsuario("@" + editUsuario.getText().toString());
                    usuario.setSenha(editSenha.getText().toString());

                    try{
                        realm.beginTransaction();

                        Number maiorId = realm.where(Usuario.class).max("id");
                        int proximoId;

                        if (maiorId == null){
                            proximoId = 1;
                        }
                        else{
                            proximoId = maiorId.intValue() + 1;
                        }

                        usuario.setId(proximoId);

                        realm.copyToRealm(usuario);
                        realm.commitTransaction();

                        ControleSessao controleSessao = new ControleSessao(CadastroActivity.this);
                        controleSessao.iniciaSessao(usuario.getId(), usuario.getUsuario());

                        Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();

                        Intent mainIntent = new Intent(CadastroActivity.this, MainActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);

                        finish();
                    }
                    catch (Exception e){
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CadastroActivity.this);
                        dialogBuilder.setTitle("Erro fatal");
                        dialogBuilder.setMessage("O aplicativo apresentou uma falha e precisou ser finalizado.");
                        dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }
            }
        });
    }

    private void inflateToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.text_cadastro);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private boolean isCadastroValido(){
        StringBuilder mensagemErroBuilder = new StringBuilder();

        String nome = editNomeCompleto.getText().toString();

        if (nome == null || nome.equals("")){
            mensagemErroBuilder.append("- Preencha o campo 'Nome Completo'. \n");
        }

        String usuario = editUsuario.getText().toString();

        if (usuario == null || usuario.equals("")){
            mensagemErroBuilder.append("- Preencha o campo 'Usuário'. \n");
        }

        String senha = editSenha.getText().toString();

        if (senha == null || senha.equals("")){
            mensagemErroBuilder.append("- Preencha o campo 'Senha'. \n");
        }

        String confirmacaoSenha = editConfirmaSenha.getText().toString();

        if (confirmacaoSenha == null || confirmacaoSenha.equals("")){
            mensagemErroBuilder.append("- Preencha o campo 'Confirme a Senha'. \n");
        }

        if (!senha.equals(confirmacaoSenha)){
            mensagemErroBuilder.append("- A confirmação da senha está diferente da senha. \n");
        }

        if (!isUsuarioValido(usuario)){
            mensagemErroBuilder.append("- Já existe um cadastro com o usuário informado :( \n");
        }

        String mensagemErro = mensagemErroBuilder.toString();

        if (mensagemErro != null && !mensagemErro.equals("")){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Atenção");
            dialogBuilder.setMessage(mensagemErro);
            dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialogBuilder.show();

            return false;
        }

        return true;
    }

    private boolean isUsuarioValido(String usuario){
        usuario = "@" + usuario;
        RealmResults<Usuario> usuarios = realm.where(Usuario.class).equalTo("usuario", usuario).findAll();

        if (usuarios == null || usuarios.size() == 0){
            return true;
        }
        return false;
    }
}
