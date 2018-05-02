package com.oliveira.edney.picpay.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.oliveira.edney.picpay.Class.Card;
import com.oliveira.edney.picpay.Class.Person;
import com.oliveira.edney.picpay.Class.Transaction;
import com.oliveira.edney.picpay.ImageURL;
import com.oliveira.edney.picpay.R;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/* Fragmento que realiza pagamentos quando houver
   cartões cadastrados */
public class PaymentCardFragment extends Fragment {

    private View v;
    private Context c;
    private Bundle bundle;
    private Person person;
    private ImageView ivFoto;
    private TextView tvNome;
    private TextView tvId;
    private RelativeLayout voltar;
    private TextView tvUsername;
    private Card card;
    private TextView tvNumero;
    private RelativeLayout showCards;
    private Button pay;
    private EditText etValor;

    public PaymentCardFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_payment_card, container, false);
        c = v.getContext();

        /* Views */
        tvNome = v.findViewById(R.id.tv_paymentCard_nome);
        tvId = v.findViewById(R.id.tv_paymentCard_id);
        tvUsername = v.findViewById(R.id.tv_paymentCard_username);
        ivFoto = v.findViewById(R.id.iv_paymentCard_foto);
        tvNumero = v.findViewById(R.id.tv_paymentCard_numero);
        voltar = v.findViewById(R.id.rl_paymentCard_voltar);
        showCards = v.findViewById(R.id.rl_paymentCard_2);
        pay = v.findViewById(R.id.bt_pay);
        etValor = v.findViewById(R.id.et_paymentCard_valor);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        bundle = getArguments();

        if(bundle != null)
            person = (Person) bundle.getSerializable("person");

        /* Carrega o cartão principal */
        loadCard();

        /* Limpa o EditText do valor a ser pago para facilitar o uso */
        etValor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                etValor.setText("");

                InputMethodManager imm = (InputMethodManager)c.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.showSoftInput(etValor,InputMethodManager.SHOW_IMPLICIT);
            }
        });

        /* Realiza a transação de pagamento */
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Transaction transaction = new Transaction();
                ReciboFragment reciboFragment = new ReciboFragment();

                transaction.setIdDestino(person.getId());
                transaction.setNumeroCartao(card.getNumero());
                transaction.setDataExpiracao(card.getDataExpiracao());
                transaction.setCvv(card.getCvv());
                transaction.setValor(Float.valueOf(etValor.getText().toString()));

                bundle.putSerializable("transaction", transaction);
                reciboFragment.setArguments(bundle);

                if (getFragmentManager() != null) {

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frame, reciboFragment)
                            .commit();
                }
            }
        });

        /* Volta para fragmento anterior */
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainFragment mainFragment = new MainFragment();

                if (getFragmentManager() != null) {

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frame, mainFragment)
                            .commit();
                }
            }
        });

        /* View que mostra todos os cartões cadastrados */
        showCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CardFragment cardFragment = new CardFragment();
                cardFragment.setArguments(bundle);

                if (getFragmentManager() != null) {

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frame, cardFragment)
                            .commit();
                }
            }
        });

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
    }

    private void loadCard(){

        try {

            List<Card> listCard;
            String FILENAME = "cards";
            FileInputStream fis = c.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);

            /* Carrega o primeiro cartão da lista que é o principal */
            listCard = (List<Card>) ois.readObject();
            card = listCard.get(0);

            ois.close();
            fis.close();

            /* Retorna uma string com os últimos dígitos do cartão */
            String texto = "Cartão de crédito com final "+card.getFinalCard(card.getNumero());
            tvNumero.setText(texto);

        } catch (Exception e) {
            Log.e("MeuErro", e.getMessage());
        }
    }
}
