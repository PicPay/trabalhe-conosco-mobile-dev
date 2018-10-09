package br.com.picpay.picpay.mapper;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import br.com.picpay.core.modelResponse.TransactionResponse;
import br.com.picpay.picpay.model.Transaction;

@EBean(scope = EBean.Scope.Singleton)
public class TransactionMapper {

    @Bean
    UserMapper userMapper;

    public Transaction responseToTransaction(TransactionResponse response) {
        Transaction transaction = new Transaction();
        transaction.setId(response.getId());
        transaction.setTimestamp(response.getTimestamp());
        transaction.setValue(response.getValue());
        transaction.setSuccess(response.isSuccess());
        transaction.setStatus(response.getStatus());
        transaction.setDestinationUser(userMapper.responseToUser(response.getDestinationUser()));
        return transaction;
    }
}
