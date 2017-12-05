package viniciusmaia.com.vinipay.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class NovaSenhaActivity extends AppCompatActivity {
    private EditText editSenha;
    private EditText editConfirmaSenha;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_senha);

        inflateToolbar();

        if (realm == null){
            realm = Realm.getDefaultInstance();
        }

        editSenha = (EditText) findViewById(R.id.editSenha);
        editConfirmaSenha = (EditText) findViewById(R.id.editConfirmeSenha);

        Button botaoSalvar = (Button) findViewById(R.id.botaoSalvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdicaoValida()){
                    ControleSessao controleSessao = new ControleSessao(NovaSenhaActivity.this);

                    RealmResults<Usuario> usuarios = realm.where(Usuario.class).
                            equalTo("id", controleSessao.getIdUsuario()).
                            findAll();

                    Usuario usuario = usuarios.get(0);

                    try{
                        realm.beginTransaction();

                        usuario.setSenha(editSenha.getText().toString());

                        realm.copyToRealm(usuario);
                        realm.commitTransaction();

                        Toast.makeText(NovaSenhaActivity.this, "Edição realizada com sucesso!", Toast.LENGTH_LONG).show();

                        finish();

                    }
                    catch (Exception e){
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NovaSenhaActivity.this);
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
        toolbar.setTitle(R.string.titulo_alterar_senha);

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

    private boolean isEdicaoValida(){
        StringBuilder mensagemErroBuilder = new StringBuilder();


        String senha = editSenha.getText().toString();

        if (senha == null || senha.equals("")){
            mensagemErroBuilder.append("- Preencha o campo 'Nova Senha'. \n");
        }

        String confirmacaoSenha = editConfirmaSenha.getText().toString();

        if (confirmacaoSenha == null || confirmacaoSenha.equals("")){
            mensagemErroBuilder.append("- Preencha o campo 'Confirme a Senha'. \n");
        }

        if (!senha.equals(confirmacaoSenha)){
            mensagemErroBuilder.append("- A confirmação da senha está diferente da senha. \n");
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
}
