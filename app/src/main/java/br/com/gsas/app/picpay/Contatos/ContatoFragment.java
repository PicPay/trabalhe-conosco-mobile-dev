package br.com.gsas.app.picpay.Contatos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import br.com.gsas.app.picpay.Connection.MyCallback;
import br.com.gsas.app.picpay.Connection.ServiceContato;
import br.com.gsas.app.picpay.Domain.Contato;
import br.com.gsas.app.picpay.Pagamento.PagamentoActivity;
import br.com.gsas.app.picpay.R;
import br.com.gsas.app.picpay.Util.ErroFragment;
import br.com.gsas.app.picpay.Util.VazioFragment;

public class ContatoFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Contato> contatos;
    private ProgressBar load;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contato, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar_home);

        load = view.findViewById(R.id.load);

        recyclerView = view.findViewById(R.id.lista_contato);

        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        setToolbar(toolbar);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        taskContato();
    }

    private void taskContato() {

        ServiceContato service = new ServiceContato(getContext());

        service.getContatoWeb(atualizaContato());

    }

    private MyCallback<Contato> atualizaContato() {

        return new MyCallback<Contato>() {
            @Override
            public void sucess(List<Contato> list) {

                load.setVisibility(View.GONE);
                ContatoFragment.this.contatos = list;
                recyclerView.setAdapter(new ContatoAdapter(getContext(), contatos, clickContato()));
            }

            @Override
            public void empty() {
                load.setVisibility(View.GONE);

                VazioFragment vazioFragment = new VazioFragment();
                getChildFragmentManager().beginTransaction().replace(R.id.frame_pagamento, vazioFragment, "Vazio").commitAllowingStateLoss();
            }

            @Override
            public void failure(String msg) {
                load.setVisibility(View.GONE);

                if(getContext() != null){

                    ErroFragment erroFragment = new ErroFragment();
                    getChildFragmentManager().beginTransaction().replace(R.id.frame_pagamento, erroFragment, "Erro").commitAllowingStateLoss();
                }

            }
        };
    }

    private ContatoAdapter.ContatoOnClickListener clickContato() {

        return new ContatoAdapter.ContatoOnClickListener() {
            @Override
            public void onClickContato(View view, int idx) {

                Contato contato = contatos.get(idx);

                Intent intent = new Intent(getContext(), PagamentoActivity.class);
                intent.putExtra("USER", contato);
                startActivity(intent);
            }
        };
    }

    void setToolbar(Toolbar toolbar){

        if(getActivity() != null){

            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            getActivity().setTitle(getString(R.string.toolbar_contato));

        }
    }

}
