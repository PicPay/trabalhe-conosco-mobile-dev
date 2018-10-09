package br.com.picpay.picpay.listerner;

import android.support.annotation.NonNull;

import br.com.picpay.picpay.model.Card;
import br.com.picpay.picpay.model.User;

public interface TransactionListerner {
    void execute(@NonNull User user, @NonNull Card card, float value);
}
