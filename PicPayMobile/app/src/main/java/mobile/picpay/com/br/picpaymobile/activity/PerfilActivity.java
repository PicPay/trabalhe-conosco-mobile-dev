package mobile.picpay.com.br.picpaymobile.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import io.realm.Realm;
import mobile.picpay.com.br.picpaymobile.R;
import mobile.picpay.com.br.picpaymobile.application.MyApplication;
import mobile.picpay.com.br.picpaymobile.dao.UsuarioDAO;
import mobile.picpay.com.br.picpaymobile.entity.Usuario;
import mobile.picpay.com.br.picpaymobile.infra.Util;

public class PerfilActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText numCard;
    private EditText cvv;
    private EditText dataexp;
    private Usuario u = new UsuarioDAO(this).getById(MyApplication.getInstance().getUsuario().getId());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preencherCampos();
    }

    private void preencherCampos() {
        nome = (EditText) findViewById(R.id.edNomePerf);
        email = (EditText) findViewById(R.id.edEmailPerf);
        numCard = (EditText) findViewById(R.id.edNumCardPerf);
        MaskEditTextChangedListener maskNumCard = new MaskEditTextChangedListener("#### #### #### ####", numCard);
        numCard.addTextChangedListener(maskNumCard);
        cvv = (EditText) findViewById(R.id.edCvvPerf);
        MaskEditTextChangedListener maskCvv = new MaskEditTextChangedListener("###", cvv);
        cvv.addTextChangedListener(maskCvv);
        dataexp = (EditText) findViewById(R.id.edDataExpPerf);
        MaskEditTextChangedListener maskData = new MaskEditTextChangedListener("##/##", dataexp);
        dataexp.addTextChangedListener(maskData);



        nome.setText(u.getNome());
        email.setText(u.getEmail());
        numCard.setText(u.getNumcard());
        cvv.setText(u.getCvv());
        dataexp.setText(u.getDataexp());
    }


    public void AtualizarUser(View view) {
        Util util = new Util();
        if( util.validaCamposObrig(nome) && util.validaCamposObrig(email)) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    u.setNome(nome.getText().toString().trim());
                    u.setEmail(email.getText().toString().trim());
                    u.setNumcard(numCard.getText().toString().trim().replaceAll(" ", ""));
                    u.setCvv(cvv.getText().toString().trim());
                    u.setDataexp(dataexp.getText().toString().trim());
                }
            });

            MyApplication.getInstance().setUsuario(u);
            Toast.makeText(this, "Perfil Atualizado com Sucesso", Toast.LENGTH_SHORT).show();
        }
    }
}
