package br.com.gsas.app.picpay.Carteira;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.com.gsas.app.picpay.Carteira.Cartao.CartaoAdapter;
import br.com.gsas.app.picpay.Carteira.Novo.NovoActivity;
import br.com.gsas.app.picpay.Connection.MyCallback;
import br.com.gsas.app.picpay.Connection.ServiceCartao;
import br.com.gsas.app.picpay.Domain.Cartao;
import br.com.gsas.app.picpay.R;

public class CarteiraFragment extends Fragment{

    private List<Cartao> cartaos;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    private List<Boolean> status_editar = new ArrayList<>();

    private CardView cardView;
    private Button editar;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_carteira, container, false);

        status_editar.add(false);

        cardView = view.findViewById(R.id.adicionar_cartao);
        editar = view.findViewById(R.id.editar_cartao);

        editar.setOnClickListener(editarClick());

        recyclerView = view.findViewById(R.id.lista_cartao);

        toolbar = view.findViewById(R.id.toolbar_home);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setNestedScrollingEnabled(true);

        cardView.setOnClickListener(clickCard());

        setToolbar();


        return view;
    }

    private View.OnClickListener editarClick() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean trocar = status_editar.get(0);
                status_editar.set(0, !trocar);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();

        taskCartaos();
    }

    private void taskCartaos(){

        ServiceCartao service = new ServiceCartao(getContext());
        service.getAllBD(callbackGetAll());

    }

    private MyCallback<Cartao> callbackGetAll() {

        return new MyCallback<Cartao>() {
            @Override
            public void sucess(List<Cartao> list) {

                CarteiraFragment.this.cartaos = list;
                editar.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(new CartaoAdapter(getContext(), cartaos, clickAdapter(), status_editar));
            }

            @Override
            public void empty() {

                CarteiraFragment.this.cartaos = null;
                editar.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(new CartaoAdapter(getContext(), cartaos, clickAdapter(), status_editar));
            }

            @Override
            public void failure(String msg) {

            }
        };
    }

    private CartaoAdapter.CartaoClickListener clickAdapter() {

        return new CartaoAdapter.CartaoClickListener() {
            @Override
            public void onClickPadrao(View view, int pos) {

               Cartao cartao = cartaos.get(pos);
               ServiceCartao serviceCartao = new ServiceCartao(getContext());
               serviceCartao.trocarAtivoBD(mudaCartao(), cartao);
            }

            @Override
            public void onClickVazio(View view) {

                Intent intent = new Intent(getContext(), NovoActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickCartao(View view, int pos) {

            }

            @Override
            public void onClickDelete(View view, int pos) {

                Cartao cartao = cartaos.get(pos);

                DialogExcluir frag = DialogExcluir.newInstance(cartao, listenerExcluir());
                frag.show(getChildFragmentManager(), "Excluir-Dialog");
            }


        };
    }

    private DialogExcluir.DialogListener listenerExcluir() {

        return new DialogExcluir.DialogListener() {
            @Override
            public void onExcluir(Cartao cartao) {

                ServiceCartao serviceCartao = new ServiceCartao(getContext());
                serviceCartao.deleteBD(mudaCartao(), cartao);
            }
        };
    }

    private MyCallback<Cartao> mudaCartao(){

        return new MyCallback<Cartao>() {
            @Override
            public void sucess(List<Cartao> list) {

                CarteiraFragment.this.cartaos.clear();
                CarteiraFragment.this.cartaos.addAll(list);
                editar.setVisibility(View.VISIBLE);
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void empty() {

                CarteiraFragment.this.cartaos.clear();
                editar.setVisibility(View.INVISIBLE);
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void failure(String msg) {

            }
        };
    }


    private View.OnClickListener clickCard() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), NovoActivity.class);
                startActivity(intent);
            }
        };
    }

    void setToolbar(){

        if(getActivity() != null){

            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            getActivity().setTitle(getString(R.string.toolbar_carteira));

        }
    }
}
