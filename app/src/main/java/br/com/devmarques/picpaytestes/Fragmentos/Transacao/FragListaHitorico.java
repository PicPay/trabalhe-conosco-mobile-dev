package br.com.devmarques.picpaytestes.Fragmentos.Transacao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import br.com.devmarques.picpaytestes.Adapter.AdapterCard;
import br.com.devmarques.picpaytestes.Adapter.AdapterTransacoes;
import br.com.devmarques.picpaytestes.DAO.CartoesCad.CartaoDAO;
import br.com.devmarques.picpaytestes.DAO.HistoryTransaction.TransacoesDAO;
import br.com.devmarques.picpaytestes.Dados.Cartao;
import br.com.devmarques.picpaytestes.Dados.Transacoes;
import br.com.devmarques.turismo.picpaytestes.R;

/**
 * Created by Roger on 12/11/2017.
 */

public class FragListaHitorico extends Fragment{

    View root;
    ArrayList<Transacoes> transacaos = new ArrayList<Transacoes>();
    TransacoesDAO transacoesDAO;
    ImageView adicionar, adicionarprimeiro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.listatrancao, container, false);
        InstanciandoBanco();
        transacaos = transacoesDAO.buscaContatos();

        if (transacaos.size() == 0){
            root = inflater.inflate(R.layout.nenhumatransacao, container, false);
        }else{
            ListaRecycler();
        }

        return root;
    }

    public void InstanciandoBanco(){
        transacoesDAO = new TransacoesDAO(getContext());
    }

    public void ListaRecycler(){
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.listartransacoes);
        recyclerView.setAdapter(new AdapterTransacoes( transacaos  ,getContext() ));
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }
}
