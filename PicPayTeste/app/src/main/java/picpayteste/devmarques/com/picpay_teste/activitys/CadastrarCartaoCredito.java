package picpayteste.devmarques.com.picpay_teste.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import picpayteste.devmarques.com.picpay_teste.R;
import picpayteste.devmarques.com.picpay_teste.dados.lista.usuario.Usuario;

public class CadastrarCartaoCredito extends AppCompatActivity {

    private ImageView onbackactivity;
    private Button cadastrarCartao;
    private Usuario usuarioSelecionado;
    private ImageView voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_cartao);

        Bundle extras = getIntent().getExtras();

        if (extras.containsKey(Usuario.PARAM_USER_SELECTED)){
            usuarioSelecionado = (Usuario) extras.getSerializable(Usuario.PARAM_USER_SELECTED);
        }

        voltar = findViewById(R.id.voltar);
        cadastrarCartao = findViewById(R.id.cadastrar_cartao);
        onbackactivity = findViewById(R.id.voltar);

        onbackactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cadastrarCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastrarCartaoCredito.this, CadastrandoCartao.class);
                intent.putExtra(Usuario.PARAM_USER_SELECTED, usuarioSelecionado);
                startActivity(intent);
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
