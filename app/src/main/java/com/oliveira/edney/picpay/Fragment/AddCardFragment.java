package com.oliveira.edney.picpay.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.oliveira.edney.picpay.Class.Card;
import com.oliveira.edney.picpay.R;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/* Fragmento que cadastra novos cartões */
public class AddCardFragment extends Fragment {

    private Context c;
    private RelativeLayout voltar;
    private Spinner snBandeira;
    private EditText etNome;
    private EditText etNumero;
    private EditText etValidade;
    private EditText etCvv;
    private EditText etCep;
    private Button addCard;
    private Bundle argumentos;

    public AddCardFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_card, container, false);
        c = v.getContext();

        /* Views */
        voltar = v.findViewById(R.id.rl_addCard_voltar);
        addCard = v.findViewById(R.id.bt_addCard);
        snBandeira = v.findViewById(R.id.sn_addCard_bandeira);
        etNome = v.findViewById(R.id.et_addCard_nome);
        etNumero = v.findViewById(R.id.et_addCard_numero);
        etValidade = v.findViewById(R.id.et_addCard_validade);
        etCvv = v.findViewById(R.id.et_addCard_cvv);
        etCep = v.findViewById(R.id.et_addCard_cep);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        argumentos = getArguments();

        /* Carrega itens para Spinner de bandeiras */
        String[] spinnerItens = new String[]{"Selecione a bandeira", "Mastercad", "Visa"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(c,R.layout.item_spinner, spinnerItens);
        arrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        snBandeira.setAdapter(arrayAdapter);

        /* Máscara para data */
        etValidade.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode != KeyEvent.KEYCODE_DEL) {

                    int tamanho = etValidade.getText().length();

                    if (tamanho == 2) {

                        etValidade.setInputType(InputType.TYPE_CLASS_TEXT);
                        etValidade.append("/");
                        etValidade.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                }

                return false;
            }
        });

        /* Finaliza e adiciona o novo cartão */
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etNumero.getText().toString().length() != 16)
                    Toast.makeText(c, "O número do cartão deve conter 16 dígitos",Toast.LENGTH_LONG).show();

                if(etCvv.getText().toString().length() != 3)
                    Toast.makeText(c, "O código de segurança deve conter 3 dígitos",Toast.LENGTH_LONG).show();

                else if (etNome.getText().toString().equals("") || etValidade.getText().toString().equals("")
                        || etCep.getText().toString().equals(""))
                    Toast.makeText(c, "Alguns campos estão em brancos",Toast.LENGTH_LONG).show();

                else {

                    /* Cadastra novo cartão */
                    saveCard();

                    PaymentCardFragment paymentCardFragment = new PaymentCardFragment();
                    paymentCardFragment.setArguments(argumentos);

                    if (getFragmentManager() != null) {

                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_frame, paymentCardFragment)
                                .commit();
                    }
                }
            }
        });

        /* Volta para fragmento anterior */
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment;

                /* Caso tenha ou não cartões cadastrados */
                if(new Card().fileExists(getActivity()))
                    fragment = new PaymentCardFragment();
                else
                    fragment = new PaymentFragment();

                fragment.setArguments(argumentos);

                if (getFragmentManager() != null) {

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frame, fragment)
                            .commit();
                }
            }
        });
    }

    private void saveCard() {

        try {

            String FILENAME = "cards";
            FileOutputStream fos;
            ObjectOutputStream os;
            FileInputStream fis;
            ObjectInputStream ois;
            Card card = new Card();
            List<Card> listCard = new ArrayList<>();

            card.setBandeira(snBandeira.getSelectedItem().toString());
            card.setNome(etNome.getText().toString());
            card.setNumero(etNumero.getText().toString());
            card.setCvv(Integer.parseInt(etCvv.getText().toString()));
            card.setDataExpiracao(etValidade.toString());
            card.setCep(etCep.getText().toString());

            /* Se houver cartões cadastrados carrega seu array
               para adição do novo a ser cadastrado
            */
            if(new Card().fileExists(getActivity())) {

                fis = c.openFileInput(FILENAME);
                ois = new ObjectInputStream(fis);
                listCard = (List<Card>) ois.readObject();
                ois.close();
                fis.close();
            }

            listCard.add(card);

            fos = c.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(listCard);
            os.close();
            fos.close();

        } catch (java.io.IOException e) {
            Log.e("MeuErro", e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
