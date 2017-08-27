package com.ghrc.picpay.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ghrc.picpay.R;
import com.ghrc.picpay.activity.RegisterCardActivity;
import com.ghrc.picpay.adapter.CreditCardAdapter;
import com.ghrc.picpay.model.CreditCard;
import com.ghrc.picpay.util.BD;

import java.util.ArrayList;

/**
 * Created by Guilherme on 27/08/2017.
 */

public class CreditCardFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private BD bd;
    private ArrayList<CreditCard> mListCreditCard;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!= null) {
            mListCreditCard =  savedInstanceState.getParcelableArrayList("mListCreditCard");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_credit_card,container,false);
        bd = new BD(getActivity());
        mRecyclerView =(RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mListCreditCard = bd.getCards();
        CreditCardAdapter adapter = new CreditCardAdapter(getContext(),mListCreditCard);
        mRecyclerView.setAdapter(adapter);
        Button button = (Button) view.findViewById(R.id.btnAddCard);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterCardActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mListCreditCard",mListCreditCard);
    }

}
