package mobile.picpay.com.br.picpaymobile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import io.realm.Realm;
import mobile.picpay.com.br.picpaymobile.application.MyApplication;
import mobile.picpay.com.br.picpaymobile.dao.UsuarioDAO;
import mobile.picpay.com.br.picpaymobile.entity.Usuario;
import mobile.picpay.com.br.picpaymobile.R;
import mobile.picpay.com.br.picpaymobile.infra.Util;

public class PrimeiroLoginActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText userName;
    private EditText senha;
    private EditText numCard;
    private EditText cvv;
    private EditText dataexp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeiro_login);

        nome = (EditText) findViewById(R.id.edNomeUser);
        email = (EditText) findViewById(R.id.edEmailUser);
        userName = (EditText) findViewById(R.id.edUserNameUser);
        senha = (EditText) findViewById(R.id.edSenha);
        numCard = (EditText) findViewById(R.id.edNumCardUser);
        MaskEditTextChangedListener maskNumCard = new MaskEditTextChangedListener("#### #### #### ####", numCard);
        numCard.addTextChangedListener(maskNumCard);

        cvv = (EditText) findViewById(R.id.edCvvUser);
        MaskEditTextChangedListener maskCvv = new MaskEditTextChangedListener("###", cvv);
        cvv.addTextChangedListener(maskCvv);

        dataexp = (EditText) findViewById(R.id.edDataExpUser);
        MaskEditTextChangedListener maskData = new MaskEditTextChangedListener("##/##", dataexp);
        dataexp.addTextChangedListener(maskData);

    }


    public void SalvarUser(View view) {

        final Usuario u = new Usuario();



        Util util = new Util();
        if(util.validaCamposObrig(nome) && util.validaCamposObrig(email) && util.validaCamposObrig(userName) && util.validaCamposObrig(senha)) {
            u.setNome(nome.getText().toString().trim());
            u.setEmail(email.getText().toString().trim());
            u.setUsername(userName.getText().toString().trim());
            u.setSenha(senha.getText().toString().trim());
            u.setNumcard(numCard.getText().toString().trim().replaceAll(" ", ""));
            u.setCvv(cvv.getText().toString().trim());
            u.setDataexp(dataexp.getText().toString().trim());

            UsuarioDAO usuarioDAO = new UsuarioDAO(this);

            if (usuarioDAO.save(u) != null) {
                MyApplication.getInstance().setUsuario(u);
                Toast.makeText(getApplicationContext(), "Redirecionando...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);

            }

        }else{
            Toast.makeText(this, "Existem Campos Obrigatórios a serem Peenchidos.", Toast.LENGTH_SHORT).show();
        }


    }
}
