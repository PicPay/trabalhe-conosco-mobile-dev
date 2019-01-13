package picpayteste.devmarques.com.picpay_teste.activitys;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import picpayteste.devmarques.com.picpay_teste.R;
import picpayteste.devmarques.com.picpay_teste.dados.lista.cartao.Cartao;
import picpayteste.devmarques.com.picpay_teste.dados.lista.transacao.Transacoes;
import picpayteste.devmarques.com.picpay_teste.dados.lista.transacao.Transaction;
import picpayteste.devmarques.com.picpay_teste.dados.lista.usuario.Usuario;

public class Recibo extends AppCompatActivity {

    private ImageView imageUser;
    private TextView userId;
    private TextView transacao;
    private TextView horaTransacao;
    private TextView cartaoUsado;
    private TextView valorTransacao;
    private TextView valorTotalPago;
    private Transaction transactionObj;
    private Cartao cartaoUsadopag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibo);
        inicializarIds();

        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(Transaction.PARAM_TRANSACTION) && extras.containsKey(Cartao.PARAM_CARD)){
            transactionObj = (Transaction) extras.getSerializable(Transaction.PARAM_TRANSACTION);
            cartaoUsadopag = (Cartao) extras.getSerializable(Cartao.PARAM_CARD);
        }

        Picasso.get()
                .load(transactionObj.getTransaction().getDestination_user().getImg())
                .fit()
                .centerInside()
                .into(imageUser);

        userId.setText(transactionObj.getTransaction().getDestination_user().getUsername());

        String transactionNm = "Transação: "+ transactionObj.getTransaction().getId();

        transacao.setText(transactionNm);

        horaTransacao.setText(getDateCurrentTimeZone(transactionObj.getTransaction().getTimestamp()));

        String cartaoUsadoeNumero = cartaoUsadopag.getNome_titular() + " " + cartaoUsadopag.getCard_number().substring(0,4);

        cartaoUsado.setText(cartaoUsadoeNumero);

        String valorTransaction = "R$ " +transactionObj.getTransaction().getValue();

        valorTransacao.setText(valorTransaction);
        valorTotalPago.setText(valorTransaction);

    }

    public void inicializarIds(){
        imageUser = findViewById(R.id.image_user);
        userId = findViewById(R.id.nome_id);
        transacao = findViewById(R.id.numero_transacao);
        horaTransacao = findViewById(R.id.hora);
        cartaoUsado = findViewById(R.id.cartao_usado);
        valorTransacao = findViewById(R.id.valor_pago);
        valorTotalPago = findViewById(R.id.valor_total_pago);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Recibo.this, ListaAmigosActivity.class));
        finish();
    }

    public  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone).replaceAll("\\s"," ás ");
        }catch (Exception e) {
        }
        return "";
    }
}
