package com.example.eduardo.demoapppagamento.payment;

import android.view.View;

public interface CardsListClickListener {

    interface Select {
        void onClick(View view, int position);
    }

    interface Delete {
        void onClick(View view, int position);
    }

    /*void onClickRadioButton(View view, int position);
    void onClickExcludeButton(View view, int position);*/
}
