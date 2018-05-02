package com.oliveira.edney.picpay.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.oliveira.edney.picpay.Class.Card;
import com.oliveira.edney.picpay.Class.Person;
import com.oliveira.edney.picpay.Class.Transaction;
import com.oliveira.edney.picpay.ImageURL;
import com.oliveira.edney.picpay.Payment;
import com.oliveira.edney.picpay.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* Fragmento que finaliza a transação de pagamento */
public class ReciboFragment extends Fragment {

    private ImageView ivFoto;
    private TextView tvNome;
    private TextView tvId;
    private TextView tvUsername;
    private TextView tvCartao;
    private TextView tvValor;
    private Button voltar;
    private Button payAgain;
    private Person person;
    private TextView tvStatus;
    private TextView tvTransacaoId;
    private TextView tvTime;

    public ReciboFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recibo, container, false);

        /* Views */
        ivFoto = v.findViewById(R.id.iv_recibo_foto);
        tvNome = v.findViewById(R.id.tv_recibo_nome);
        tvId = v.findViewById(R.id.tv_recibo_id);
        tvUsername = v.findViewById(R.id.tv_recibo_username);
        tvCartao = v.findViewById(R.id.tv_recibo_cartao);
        tvValor = v.findViewById(R.id.tv_recibo_valor);
        voltar = v.findViewById(R.id.bt_back);
        payAgain = v.findViewById(R.id.bt_payAgain);
        tvStatus = v.findViewById(R.id.tv_recibo_status);
        tvTransacaoId = v.findViewById(R.id.tv_recibo_transacao_id);
        tvTime = v.findViewById(R.id.tv_recibo_time);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        final Bundle bundle = getArguments();
        Transaction transaction = null;

        if (bundle != null) {

            person = (Person) bundle.getSerializable("person");
            transaction = (Transaction) bundle.getSerializable("transaction");
        }

        /* View para voltar ao fragmento anterior */
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainFragment fragment = new MainFragment();

                if (getFragmentManager() != null) {

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frame, fragment)
                            .commit();
                }
            }
        });

        /* Realizar transação novamente */
        payAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle argumentos = new Bundle();
                PaymentCardFragment fragment = new PaymentCardFragment();

                argumentos.putSerializable("person", person);
                fragment.setArguments(argumentos);

                if (getFragmentManager() != null) {

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frame, fragment)
                            .commit();
                }
            }
        });

        /* String com apenas os últimos números do cartão */
        String cardHided = null;
        if (transaction != null)
            cardHided = "**** **** **** "+(new Card().getFinalCard(transaction.getNumeroCartao()));

        /* Carrega os dados da pessoa */
        new ImageURL(new ImageURL.Callback() {
            @Override
            public void onLoaded(Bitmap bitmap) {

                ivFoto.setImageBitmap(bitmap);
            }
        }).execute(person.getFoto());
        tvNome.setText(person.getNome());
        tvId.setText(String.valueOf(person.getId()));
        tvUsername.setText(person.getUsername());
        tvCartao.setText(cardHided);
        if (transaction != null)
            tvValor.setText(String.valueOf(transaction.getValor()));

        /* Realiza um POST de transação de pagamento */
        new Payment(transaction, new Payment.Callback() {
            @Override
            public void onTransaction(JSONObject response) {

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
                String time = dateFormat.format(new Date());
                String texto;

                try {

                    if(response.getString("status").equals("Aprovada")){

                        texto = "Pagamento Aprovado!";
                        tvStatus.setText(texto);
                    }
                    else {
                        texto = "Pagamento Recusado!";
                        tvStatus.setText(texto);
                    }

                    tvTransacaoId.setText(response.getString("id"));

                } catch (JSONException e) {
                    Log.e("MeuErro", e.getMessage());
                }

                tvTime.setText(time);
            }
        }).execute("http://careers.picpay.com/tests/mobdev/transaction");
    }
}
