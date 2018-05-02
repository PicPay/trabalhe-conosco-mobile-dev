package com.oliveira.edney.picpay.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.oliveira.edney.picpay.Adapter.CardAdapter;
import com.oliveira.edney.picpay.Class.Card;
import com.oliveira.edney.picpay.ItemClickListener;
import com.oliveira.edney.picpay.R;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/* Fragmento para gerenciamento dos cartões */
public class CardFragment extends Fragment {

    private View v;
    private Context c;
    private List<Card> listCard;
    private RelativeLayout voltar;
    private Bundle argumentos;
    private RelativeLayout addCard;
    private int indexCartaoCheck = 0;
    private CardAdapter adapter;
    private Button btSelect;

    public CardFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_card, container, false);
        c = v.getContext();
        RecyclerView recyclerView = v.findViewById(R.id.rv_cards);

        /* Views */
        voltar = v.findViewById(R.id.rl_card_voltar);
        addCard = v.findViewById(R.id.rl_card_2);
        btSelect = v.findViewById(R.id.bt_selectCard);

        /* Recyclerview */
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));

        adapter = new CardAdapter();
        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        argumentos = getArguments();

        /* Carrega os cartões */
        loadCard();

        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Atualiza a lista de cartões para o cartão principal */
                updateCards();

                PaymentCardFragment paymentCardFragment = new PaymentCardFragment();
                paymentCardFragment.setArguments(argumentos);

                if (getFragmentManager() != null) {

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frame, paymentCardFragment)
                            .commit();
                }
            }
        });

        /* Ações da lista de cartões */
        adapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {

                indexCartaoCheck = position;

                adapter.setIndexCheck(position);
                adapter.notifyDataSetChanged();
            }
        });

        /* View para cadastrar o cartão de crédito */
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddCardFragment addCardFragment = new AddCardFragment();
                addCardFragment.setArguments(argumentos);

                if (getFragmentManager() != null) {

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frame, addCardFragment)
                            .commit();
                }
            }
        });

        /* View para voltar ao fragmento anterior */
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PaymentCardFragment paymentCardFragment = new PaymentCardFragment();
                paymentCardFragment.setArguments(argumentos);

                if (getFragmentManager() != null) {

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frame, paymentCardFragment)
                            .commit();
                }
            }
        });
    }

    /* Carrega os cartões e adiciona ao recyclerview */
    private void loadCard(){

        try {

            FileInputStream fis;
            ObjectInputStream ois;
            String FILENAME = "cards";

            fis = c.openFileInput(FILENAME);
            ois = new ObjectInputStream(fis);
            listCard = (List<Card>) ois.readObject();

            adapter.setData(listCard);
            adapter.notifyDataSetChanged();

            ois.close();
            fis.close();

        } catch (Exception e) {
            Log.e("MeuErro", e.getMessage());
        }
    }

    /* Atualiza a ordem dos cartões */
    private void updateCards(){

        try{
            String FILENAME = "cards";
            FileOutputStream fos = c.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            Card card = listCard.get(indexCartaoCheck);
            Card auxCard = listCard.get(0);

            listCard.set(0, card);
            listCard.set(indexCartaoCheck, auxCard);
            os.writeObject(listCard);

            os.close();
            fos.close();
        }
        catch (Exception e){
            Log.e("MeuErro", e.getMessage());
        }
    }
}
