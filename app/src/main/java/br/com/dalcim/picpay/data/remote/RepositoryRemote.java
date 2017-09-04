package br.com.dalcim.picpay.data.remote;

import java.util.List;

import br.com.dalcim.picpay.data.Payment;
import br.com.dalcim.picpay.data.Transaction;
import br.com.dalcim.picpay.data.User;

/**
 * @author Wiliam
 * @since 27/08/2017
 */
public interface RepositoryRemote {
    void getUsers(final GetUsersCallback callback);
    void transaction(Payment payment, final TransactionCallback callback);

    interface TransactionCallback{
        void onSucess(Transaction transaction);
        void notApproved(Transaction transaction);
        void onFailure(String failure);
    }

    interface GetUsersCallback{
        void onSucess(List<User> users);
        void emptyList();
        void onFailure(String failure);
    }


}
