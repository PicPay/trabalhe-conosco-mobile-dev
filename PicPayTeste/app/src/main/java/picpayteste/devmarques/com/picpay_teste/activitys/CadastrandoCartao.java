package picpayteste.devmarques.com.picpay_teste.activitys;

import android.content.Intent;
import android.os.Build;
import android.print.PrinterId;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import picpayteste.devmarques.com.picpay_teste.R;
import picpayteste.devmarques.com.picpay_teste.dados.lista.cartao.Cartao;
import picpayteste.devmarques.com.picpay_teste.dados.lista.usuario.Usuario;
import picpayteste.devmarques.com.picpay_teste.dao.cartao.BancoCardDao;
import picpayteste.devmarques.com.picpay_teste.dao.cartao.CartaoDaoDB;
import picpayteste.devmarques.com.picpay_teste.utils.MaskType;
import picpayteste.devmarques.com.picpay_teste.utils.MaskUtil;
import picpayteste.devmarques.com.picpay_teste.utils.ValidaDadosCartao;

import static java.security.AccessController.getContext;

public class CadastrandoCartao extends AppCompatActivity {

    private TextInputEditText numeroCartao;
    private TextInputEditText dataValidade;
    private TextInputEditText nomeCartao;
    private TextInputEditText cartaocvv;
    private ValidaDadosCartao valida;
    private Button salvarCartao;
    private ArrayList<Cartao> cartao = new ArrayList<Cartao>();
    private CartaoDaoDB cartaoDaoDB;
    private String numeroCard;
    private ImageView voltar;
    private Usuario usuarioSelecionado;
    private Cartao cartaoEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrando_cartao);
        Bundle extras = getIntent().getExtras();

        // Inicialização de ids
        inicializacao_ids();


        if (extras.containsKey(Usuario.PARAM_USER_SELECTED)){
            usuarioSelecionado = (Usuario) extras.getSerializable(Usuario.PARAM_USER_SELECTED);
        }

        // Máscaras
        numeroCartao.addTextChangedListener(MaskUtil.insert(numeroCartao, MaskType.NumeroCartao));
        dataValidade.addTextChangedListener(MaskUtil.insert(dataValidade, MaskType.Data));

        if (extras.containsKey(Cartao.PARAM_CARD_EDIT)){
            cartaoEditar = (Cartao) extras.getSerializable(Cartao.PARAM_CARD_EDIT);
            numeroCartao.setText(cartaoEditar.getCard_number());
            dataValidade.setText(cartaoEditar.getExpiry_date());
            nomeCartao.setText(cartaoEditar.getNome_titular());
            cartaocvv.setText(cartaoEditar.getCvv());
        }

        numeroCartao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                numeroCard = s.toString();
            }
        });

        valida = new ValidaDadosCartao();


        salvarCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardnumbernoMask = numeroCartao.getText().toString().replaceAll(" ","");

               if ((valida.getValidaNumeroCartao(cardnumbernoMask)) && (valida.getValidaNome(nomeCartao.getText().toString()))
                       && (valida.getValidaCVV(cartaocvv.getText().toString())) && (valida.getValidaData(dataValidade.getText().toString()))){
                   Toast.makeText(getApplicationContext(), "Tudo ok", Toast.LENGTH_SHORT).show();

                   // salvando cartão no banco // depois de validado.
                   salvaCartao(new Cartao(nomeCartao.getText().toString(), cardnumbernoMask, dataValidade.getText().toString(), cartaocvv.getText().toString()));
                }else{
                   Toast.makeText(getApplicationContext(), "Revise os dados!", Toast.LENGTH_SHORT).show();
               }
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void inicializacao_ids() {
        numeroCartao = findViewById(R.id.numero_cartao);
        dataValidade = findViewById(R.id.vencimento_cartao);
        nomeCartao = findViewById(R.id.nome_titular);
        cartaocvv = findViewById(R.id.cartao_cvv);
        voltar = findViewById(R.id.voltar);
        salvarCartao = findViewById(R.id.salvar_cartao);
    }

    public void salvaCartao(Cartao cartao){
        InstanciandoBanco();
        inserir(cartao);
    }

    public void InstanciandoBanco(){
        cartaoDaoDB = new CartaoDaoDB(getApplicationContext());
    }

    private void inserir(Cartao cartao) {
        if (cartaoEditar!=null){
            cartao.setId(cartaoEditar.getId());
            cartaoDaoDB.alterar_cartao(cartao);
            Intent intent = new Intent(CadastrandoCartao.this, Pagamento.class);
            intent.putExtra(Usuario.PARAM_USER_SELECTED, usuarioSelecionado);
            intent.putExtra(Cartao.PARAM_CARD, cartaoDaoDB.listar_cartoes().get(0));
            startActivity(intent);
            finish();
        }else {
            if (cartaoDaoDB.inserir_cartao(cartao) > 0) {
                Intent intent = new Intent(CadastrandoCartao.this, Pagamento.class);
                intent.putExtra(Usuario.PARAM_USER_SELECTED, usuarioSelecionado);
                intent.putExtra(Cartao.PARAM_CARD, cartaoDaoDB.listar_cartoes().get(0));
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Houve um erro inesperado!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
