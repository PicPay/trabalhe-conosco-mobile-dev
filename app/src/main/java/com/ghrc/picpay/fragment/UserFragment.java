package com.ghrc.picpay.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ghrc.picpay.R;
import com.ghrc.picpay.activity.PayActivity;
import com.ghrc.picpay.adapter.UserAdapter;
import com.ghrc.picpay.api.PicPayApi;
import com.ghrc.picpay.model.User;
import com.ghrc.picpay.util.ButtonHackClick;
import com.ghrc.picpay.util.CheckConnection;
import com.ghrc.picpay.util.MyApplication;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_FIRST_USER;

/**
 * Created by Guilherme on 27/08/2017.
 */

public class UserFragment extends Fragment  implements ButtonHackClick {
    private RecyclerView mRecyclerView;
    private ArrayList<User> mListUser;
    private UserAdapter mUserAdapter;
    private View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(savedInstanceState!= null) {
            mListUser =  savedInstanceState.getParcelableArrayList("mListUser");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView() ;
        search(searchView);
        searchView.setQueryHint("Pesquisa por Nome/UserName");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user,container,false);
        mRecyclerView =(RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        if(CheckConnection.isOnline(getActivity())){
            getDadosUsers();
        }else{
            showSnackBar();
        }
        return view;
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mUserAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mListUser",mListUser);
    }
    public void showSnackBar()
    {
        Snackbar
                .make(view.findViewById(R.id.snackbarPosition),"Sem conexão com  a internet.",Snackbar.LENGTH_INDEFINITE)
                .setAction("Abrir Wifi", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK);
                        UserFragment.this.startActivityForResult(intent, RESULT_FIRST_USER);
                    }
                })
                .show();
    }

    private void getDadosUsers(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Aguarde carregando a lista de usuários");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final ButtonHackClick buttonHack = this;
        if(mListUser == null || mListUser.size() == 0){
            PicPayApi picPayApi = ((MyApplication) getActivity().getApplication()).getApiInstance();
            Call<ArrayList<User>> callUsers = picPayApi.getUsers();
            callUsers.enqueue(new Callback<ArrayList<User>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<User>> call, @NonNull Response<ArrayList<User>> response) {
                    mListUser = response.body();
                    mUserAdapter = new UserAdapter(getContext(),mListUser,buttonHack);
                    mRecyclerView.setAdapter(mUserAdapter);
                    progressDialog.dismiss();
                    if(mListUser.size() == 0) {
                        Toast.makeText(getContext(), "Sem usuários disponíveis para enviar pagamentos.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                    Toast.makeText(getContext(), "Erro ao buscar usuários"+ t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            UserAdapter adapter = new UserAdapter(getContext(), mListUser,buttonHack);
            mRecyclerView.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_FIRST_USER) {
            if(CheckConnection.isOnline(getActivity())) {
                getDadosUsers();
            }
            else {
                showSnackBar();
            }
        }
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(getActivity(), PayActivity.class);
        intent.putExtra("user",mListUser.get(position));
        startActivity(intent);
    }
}
