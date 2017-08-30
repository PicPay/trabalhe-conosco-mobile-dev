package com.example.vitor.picpaytest.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.vitor.picpaytest.R;
import com.example.vitor.picpaytest.activity.AdicionarCartaoActivity;
import com.example.vitor.picpaytest.dominio.Cartao;
import com.example.vitor.picpaytest.helper.ListaCartaoAdapter;
import com.example.vitor.picpaytest.persistencia.PicPayDB;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

//Fragment dentro da MainActivity que lista cartoes
public class CartoesFragment extends Fragment {

    private ListaCartaoAdapter listaAdapter;
    private ListView lv;

    List<Cartao> cartoes = new ArrayList<Cartao>();

    public CartoesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cartoes, container, false);

        lv = (ListView) view.findViewById(R.id.listView_cartoes);

        cartoes = carregaCartao(this.getContext());

        listaAdapter = new ListaCartaoAdapter(cartoes, this.getContext());
        lv.setAdapter(listaAdapter);

        listaAdapter.notifyDataSetChanged();

        ImageButton botaoAdicionar = (ImageButton) view.findViewById(R.id.bt_add_fragment_cartoes);

        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdicionarCartaoActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cartoes = carregaCartao(this.getContext());
        listaAdapter = new ListaCartaoAdapter(cartoes, this.getContext());
        lv.setAdapter(listaAdapter);
    }

    public List<Cartao> carregaCartao(Context context){
        List<Cartao> cards = new ArrayList<Cartao>();
        PicPayDB db =  new PicPayDB(context);
        try{
            cards = db.lista();
            return cards;
        }finally {
            db.close();
        }
    }
}
