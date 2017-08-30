package com.ghrc.picpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ghrc.picpay.R;
import com.ghrc.picpay.adapter.HistoryAdapter;
import com.ghrc.picpay.model.Transaction;
import com.ghrc.picpay.util.BD;
import com.ghrc.picpay.util.MyApplication;

import java.util.ArrayList;

/**
 * Created by Guilherme on 29/08/2017.
 */

public class HistoryFragment extends Fragment {
    private ArrayList<Transaction> mListHistory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!= null) {
            mListHistory =  savedInstanceState.getParcelableArrayList("mListHistory");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_history,container,false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mListHistory =  ((MyApplication) getActivity().getApplication()).getInstanceBD().getHistory();
        if(mListHistory.size() > 0){
            ((TextView) view.findViewById(R.id.txtNoCard)).setVisibility(View.GONE);
        }
        HistoryAdapter adapter = new HistoryAdapter(getContext(),mListHistory);
        mRecyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mListHistory",mListHistory);
    }
}
