package viniciusmaia.com.vinipay.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import viniciusmaia.com.vinipay.R;
import viniciusmaia.com.vinipay.adapters.UsuarioAdapter;
import viniciusmaia.com.vinipay.modelo.Usuario;
import viniciusmaia.com.vinipay.restclient.PicPayRestClient;

public class UsuariosFragment extends Fragment {
    private Retrofit retrofit;
    private SwipeRefreshLayout refresher;
    private RecyclerView recyclerUsuarios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.usuario_lista, container, false);

        refresher = (SwipeRefreshLayout) view.findViewById(R.id.refresher);
        recyclerUsuarios = (RecyclerView) view.findViewById(R.id.recyler_usuarios);
        recyclerUsuarios.setHasFixedSize(true);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerUsuarios.getContext(), DividerItemDecoration.VERTICAL);
        recyclerUsuarios.addItemDecoration(dividerItemDecoration);

        refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                preencheRecyclerUsuarios();
            }
        });

        refresher.setRefreshing(true);

        preencheRecyclerUsuarios();

        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.text_usuarios);
    }

    private void preencheRecyclerUsuarios(){
        if (retrofit == null){
            inicializaRetrofit();
        }

        PicPayRestClient restClient = retrofit.create(PicPayRestClient.class);
        Call<List<Usuario>> call = restClient.getUsuarios();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                List<Usuario> usuarios = response.body();
                UsuarioAdapter adapter = new UsuarioAdapter(usuarios, getActivity());
                recyclerUsuarios.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                refresher.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                refresher.setRefreshing(false);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("Erro");
                dialogBuilder.setMessage("Falha ao baixar dados dos usuários. Por favor, tente novamente!");
                dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogBuilder.show();
            }
        });
    }

    private void inicializaRetrofit(){
        retrofit = new Retrofit.Builder().
                        baseUrl(getText(R.string.url_rest).toString()).
                        addConverterFactory(GsonConverterFactory.create()).
                        build();
    }
}
