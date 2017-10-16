package mobile.picpay.com.br.picpaymobile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import io.realm.Realm;
import mobile.picpay.com.br.picpaymobile.R;
import mobile.picpay.com.br.picpaymobile.application.MyApplication;
import mobile.picpay.com.br.picpaymobile.dao.PessoaDAO;
import mobile.picpay.com.br.picpaymobile.dao.UsuarioDAO;
import mobile.picpay.com.br.picpaymobile.entity.Pessoa;
import mobile.picpay.com.br.picpaymobile.entity.Transacao;
import mobile.picpay.com.br.picpaymobile.entity.Usuario;
import mobile.picpay.com.br.picpaymobile.infra.Conexao;
import mobile.picpay.com.br.picpaymobile.infra.MoneyTextWatcher;
import mobile.picpay.com.br.picpaymobile.infra.Util;

public class TransacaoActivity extends AppCompatActivity {
    private ImageView imagem;
    private TextView nomeUserTrans;
    private TextView UserNameUserTrans;
    private TextView nomeUserEnvio;
    private EditText numCardEnvio;
    private EditText cvvEnvio;
    private EditText dataExpEnvio;
    private EditText valorEnvio;
    private Pessoa p;
    private Usuario u;
    private Transacao t;
    private Locale mLocale = new Locale("pt", "BR");
    private Conexao c = new Conexao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String id = extras.getString("idUsuario");

            buscaInformacaoPreencheCampos(id);

        }
    }

    private void buscaInformacaoPreencheCampos(String id) {
        this.p = new PessoaDAO(this).getById(Integer.parseInt(id));
        this.u = new UsuarioDAO(this).getById(MyApplication.getInstance().getUsuario().getId());




        imagem = (ImageView) findViewById(R.id.imageTran);
        nomeUserTrans = (TextView) findViewById(R.id.txvNomeUserTran);
        UserNameUserTrans = (TextView) findViewById(R.id.txvUserNameUserTran);
        nomeUserEnvio = (TextView) findViewById(R.id.txvNomeUserEnvio);
        numCardEnvio = (EditText) findViewById(R.id.edNumCardEnvio);
        MaskEditTextChangedListener maskNumCard = new MaskEditTextChangedListener("#### #### #### ####", numCardEnvio);
        numCardEnvio.addTextChangedListener(maskNumCard);
        cvvEnvio = (EditText) findViewById(R.id.edCvvEnvio);
        dataExpEnvio = (EditText) findViewById(R.id.edDataExpEnvio);
        MaskEditTextChangedListener maskData = new MaskEditTextChangedListener("##/##", dataExpEnvio);
        dataExpEnvio.addTextChangedListener(maskData);
        valorEnvio = (EditText) findViewById(R.id.edValorEnvio);

        // preencher valores pree definidos

        Picasso.with(this).load(p.getImg()).into(imagem);
        nomeUserTrans.setText(p.getName());
        UserNameUserTrans.setText(p.getUsername());
        nomeUserEnvio.setText(u.getNome());
        numCardEnvio.setText(u.getNumcard());
        cvvEnvio.setText(u.getCvv());
        dataExpEnvio.setText(u.getDataexp());
        valorEnvio.addTextChangedListener(new MoneyTextWatcher(valorEnvio, mLocale));
        valorEnvio.setText("0");

    }

    public void EnviarTransacao(View view) {
        Util util = new Util();

        if(util.validaCamposObrig(numCardEnvio) && util.validaCamposObrig(cvvEnvio) && util.validaCamposObrig(dataExpEnvio) && util.validaCamposObrig(valorEnvio)) {
            if(parseToBigDecimal(valorEnvio.getText().toString().trim(), mLocale).doubleValue() == 0.0){
                Toast.makeText(this, "O valor não pode ser Zero!", Toast.LENGTH_SHORT).show();
            }else {
                t = new Transacao(numCardEnvio.getText().toString().trim().replaceAll(" ", ""), Integer.parseInt(cvvEnvio.getText().toString().trim()), parseToBigDecimal(valorEnvio.getText().toString().trim(), mLocale).doubleValue(), dataExpEnvio.getText().toString().trim(), p.getId());
                c.enviarWsAsync(TransacaoActivity.this, t);

                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        u.setNumcard(numCardEnvio.getText().toString().trim().replaceAll(" ", ""));
                    }
                });
            }
        }else
            Toast.makeText(this, "Existem Campos Obrigatórios a serem Peenchidos.", Toast.LENGTH_SHORT).show();

    }

    private BigDecimal parseToBigDecimal(String value, Locale locale) {
        String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance(locale).getCurrency().getSymbol());

        String cleanString = value.replaceAll(replaceable, "");

        return new BigDecimal(cleanString).setScale(
                2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR
        );
    }
}
