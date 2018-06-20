package br.com.gsas.app.picpay.Carteira.Novo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.gsas.app.picpay.Connection.OnCallback;
import br.com.gsas.app.picpay.Connection.ServiceCartao;
import br.com.gsas.app.picpay.Domain.Cartao;
import br.com.gsas.app.picpay.R;
import br.com.gsas.app.picpay.Util.MaskWatcher;

public class NovoActivity extends AppCompatActivity implements CartaoDialog.DialogListener {

    private Button salvar;

    private TextInputLayout lbandeira;
    private TextInputLayout lnome;
    private TextInputLayout lnumero;
    private TextInputLayout lvalidade;
    private TextInputLayout lcvv;
    private TextInputLayout lcep;

    private EditText bandeira;
    private EditText nome;
    private EditText numero;
    private EditText validade;
    private EditText cvv;
    private EditText cep;

    private Toolbar toolbar;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo);

        setUpLayout();

        numero.addTextChangedListener(new MaskWatcher("#### #### #### #### ###"));
        validade.addTextChangedListener(new MaskWatcher("##/####"));
        cep.addTextChangedListener(new MaskWatcher("##.###-###"));

        bandeira.setOnClickListener(clickBandeira());
        salvar.setOnClickListener(clickSalvar());

        setToolbar();
    }

    void setToolbar(){

        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.toolbar_novo));

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){

            finish();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener clickSalvar() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(conferirDados()){

                    startLoad();

                    Cartao cartao = new Cartao();

                    cartao.setAtivo(1);
                    cartao.setBandeira(bandeira.getText().toString());
                    cartao.setNome(nome.getText().toString());
                    cartao.setCard_nunber(numero.getText().toString().replace(" ", ""));
                    cartao.setMes(validade.getText().toString().split("/")[0]);
                    cartao.setAno(validade.getText().toString().split("/")[1]);
                    cartao.setCvv(cvv.getText().toString());
                    cartao.setCep(cep.getText().toString().replace(".","").replace("-",""));

                    salvarCartao(cartao);
                }

            }
        };
    }

    private void salvarCartao(Cartao cartao) {

        ServiceCartao service = new ServiceCartao(NovoActivity.this);
        service.insertBD(callbackSalvar(), cartao);

    }

    private OnCallback<Cartao> callbackSalvar() {

        return new OnCallback<Cartao>() {
            @Override
            public void sucess(Cartao element) {

                cancelLoad();
                finish();
            }

            @Override
            public void empty() {

            }

            @Override
            public void failure(String msg) {

            }
        };

    }


    private boolean conferirDados() {

        return conferirNome() & conferirBandeira() & conferirNumero()
                & conferirValidade() & conferirCvv() & conferirCep();

    }

    private boolean conferirBandeira() {

        if (bandeira.getText().length() == 0) {

            lbandeira.setError(getString(R.string.erro_cadastro));
            return false;
        }
        else{
            return true;
        }
    }

    private boolean conferirNome() {

        if (nome.getText().length() == 0) {

            lnome.setError(getString(R.string.erro_cadastro));
            return false;
        }
        else{
            return true;
        }
    }

    private boolean conferirNumero() {

        if (numero.getText().length() == 0) {

            lnumero.setError(getString(R.string.erro_cadastro));
            return false;
        }
        else{
            return true;
        }
    }

    private boolean conferirValidade() {

        if (validade.getText().length() == 0) {

            lvalidade.setError(getString(R.string.erro_cadastro));
            return false;
        }
        else{
            return true;
        }
    }

    private boolean conferirCvv() {

        if (cvv.getText().length() == 0) {

            lcvv.setError(getString(R.string.erro_cadastro));
            return false;
        }
        else{
            return true;
        }
    }

    private boolean conferirCep() {

        if (cep.getText().length() == 0) {

            lcep.setError(getString(R.string.erro_cadastro));
            return false;
        }
        else{
            return true;
        }
    }

    private View.OnClickListener clickBandeira() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CartaoDialog dig = new CartaoDialog();
                dig.show(getSupportFragmentManager(), "Lista");
            }
        };
    }

    private void setUpLayout() {

        bandeira = findViewById(R.id.bandeira_cartao);
        nome = findViewById(R.id.nome_cartao);
        numero = findViewById(R.id.numero_cartao);
        validade = findViewById(R.id.validade_cartao);
        cvv = findViewById(R.id.cvv_cartao);
        cep = findViewById(R.id.cep_cartao);

        lbandeira = findViewById(R.id.bandeiraLayout_cartao);
        lnome = findViewById(R.id.nomeLayout_cartao);
        lnumero = findViewById(R.id.numeroLayout_cartao);
        lvalidade = findViewById(R.id.validadeLayout_cartao);
        lcvv = findViewById(R.id.cvvLayout_cartao);
        lcep = findViewById(R.id.cepLayout_cartao);

        salvar = findViewById(R.id.salvar_cartao);

        toolbar = findViewById(R.id.toolbar_home);
    }

    private void startLoad (){

        dialog = new ProgressDialog(NovoActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.text_salvando));
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void cancelLoad(){

        dialog.cancel();
    }

    @Override
    public void onFinishDialog(String inputText) {

        bandeira.setText(inputText);
    }
}
