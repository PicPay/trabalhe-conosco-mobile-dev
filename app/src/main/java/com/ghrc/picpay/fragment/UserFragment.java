package com.ghrc.picpay.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ghrc.picpay.R;
import com.ghrc.picpay.adapter.UserAdapter;
import com.ghrc.picpay.api.PicPayApi;
import com.ghrc.picpay.deserialize.UserDes;
import com.ghrc.picpay.model.User;
import com.ghrc.picpay.util.ButtonHackClick;
import com.ghrc.picpay.util.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Guilherme on 26/08/2017.
 */

public class UserFragment extends Fragment  implements ButtonHackClick {
    private RecyclerView mRecyclerView;
    private ArrayList<User> mListUser;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!= null) {
            mListUser =  savedInstanceState.getParcelableArrayList("mListUser");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_user,container,false);
        mRecyclerView =(RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Aguarde carregando a lista de usuários");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final ButtonHackClick buttonHack = this;
        if(mListUser == null || mListUser.size() == 0){
            Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new UserDes()).create();
            Retrofit retrofit = new Retrofit
                    .Builder()
                    .baseUrl(Config.API)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            PicPayApi picPayApi = retrofit.create(PicPayApi.class);
            Call<ArrayList<User>> callUsers = picPayApi.getUsers();
            callUsers.enqueue(new Callback<ArrayList<User>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<User>> call, @NonNull Response<ArrayList<User>> response) {
                    mListUser = response.body();
                    UserAdapter adapter = new UserAdapter(getContext(),mListUser,buttonHack);
                    mRecyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                    if(mListUser.size() == 0) {
                        Toast.makeText(getContext(), "Sem usuários disponíveis para enviar pagamentos.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<User>> call, Throwable t) {

                }
            });
        }else{
            UserAdapter adapter = new UserAdapter(getContext(), mListUser,buttonHack);
            mRecyclerView.setAdapter(adapter);
            progressDialog.dismiss();
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mListUser",mListUser);
    }

    @Override
    public void onClickListener(View view, int position) {

        Toast.makeText(getContext(), "Teste" + mListUser.get(position).getUsername(), Toast.LENGTH_SHORT).show();
    }
}
