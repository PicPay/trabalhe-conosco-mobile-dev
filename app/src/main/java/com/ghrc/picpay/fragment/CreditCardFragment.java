package com.ghrc.picpay.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ghrc.picpay.R;
import com.ghrc.picpay.activity.RegisterCardActivity;
import com.ghrc.picpay.adapter.CreditCardAdapter;
import com.ghrc.picpay.model.CreditCard;
import com.ghrc.picpay.util.ButtonHackClick;
import com.ghrc.picpay.util.MyApplication;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Guilherme on 27/08/2017.
 */

public class CreditCardFragment extends Fragment implements ButtonHackClick {
    private ArrayList<CreditCard> mListCreditCard;
    private CreditCardAdapter adapter ;
    public static final int  REQUESTSAVE = 9999;
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
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        if (mListCreditCard == null){
            mListCreditCard =((MyApplication) getActivity().getApplication()).getInstanceBD().getCards();
        }
        if(mListCreditCard.size() > 0){
            ((TextView) view.findViewById(R.id.txtNoCard)).setVisibility(View.GONE);
        }
        adapter = new CreditCardAdapter(getContext(),mListCreditCard,this);
        mRecyclerView.setAdapter(adapter);
        Button button = (Button) view.findViewById(R.id.btnAddCard);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterCardActivity.class);
                CreditCardFragment.this.startActivityForResult(intent,REQUESTSAVE);
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mListCreditCard",mListCreditCard);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTSAVE && data != null){
            if(data.getBooleanExtra("issave",false)){
                ArrayList<CreditCard> mListCardNew = ((MyApplication) getActivity().getApplication()).getInstanceBD().getCards();
                adapter.updateList(mListCardNew);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClickListener(View view, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Atenção...")
                .setMessage("Você tem certeza que deseja deletar esse cartão?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MyApplication) getActivity().getApplication()).getInstanceBD().deleteCard(mListCreditCard.get(position));
                        mListCreditCard.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }}).show();
    }
}
