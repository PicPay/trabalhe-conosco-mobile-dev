package viniciusmaia.com.vinipay.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.realm.Realm;
import io.realm.RealmResults;
import viniciusmaia.com.vinipay.R;
import viniciusmaia.com.vinipay.modelo.Usuario;
import viniciusmaia.com.vinipay.util.ControleSessao;

public class LoginActivity extends AppCompatActivity {
    private EditText editUsuario;
    private EditText editSenha;
    private Realm realm;
    private ControleSessao controleSessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        controleSessao = new ControleSessao(this);

        if (controleSessao.isUsuarioLogado()){
            Intent mainIntent = new Intent(this, MainActivity.class);
            abreMainActivity();
        }
        else{
            realm = Realm.getDefaultInstance();

            editUsuario = (EditText) findViewById(R.id.editUsuario);
            editSenha = (EditText) findViewById(R.id.editSenha);

            TextView textCriarConta = (TextView) findViewById(R.id.linkCriarConta);
            textCriarConta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentCadastro = new Intent(LoginActivity.this, CadastroActivity.class);
                    startActivity(intentCadastro);
                }
            });

            Button botaoEntrar = (Button) findViewById(R.id.botaoEntrar);
            botaoEntrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCamposPreenchidos()){
                        Usuario objUsuario = null;
                        String usuario = "@" + editUsuario.getText().toString();
                        String senha = editSenha.getText().toString();

                        RealmResults<Usuario> usuarios = realm.where(Usuario.class).
                                equalTo("usuario", usuario).
                                equalTo("senha", senha).findAll();

                        if (usuarios != null && usuarios.size() > 0){
                            objUsuario = usuarios.get(0);

                            ControleSessao controleSessao = new ControleSessao(LoginActivity.this);
                            controleSessao.iniciaSessao(objUsuario.getId(), objUsuario.getUsuario());

                            abreMainActivity();
                        }
                        else{
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                            dialogBuilder.setTitle("Alerta");
                            dialogBuilder.setMessage("usuario ou senha errado.");
                            dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            dialogBuilder.show();
                        }

                    }
                }
            });
        }
    }

    private boolean isCamposPreenchidos(){
        StringBuilder mensagemErroBuilder = new StringBuilder();

        String usuario = editUsuario.getText().toString();

        if (usuario == null || usuario.equals("")){
            mensagemErroBuilder.append("- Preencha o campo 'Usuario'. \n");
        }

        String senha = editSenha.getText().toString();

        if (senha == null || senha.equals("")){
            mensagemErroBuilder.append("- Preencha o campo 'Senha'");
        }

        String mensagemErro = mensagemErroBuilder.toString();

        if (mensagemErro != null && !mensagemErro.equals("")){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Alerta");
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

    private void abreMainActivity(){
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }
}
