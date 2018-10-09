package br.com.picpay.picpay.usecase.payments;

import android.support.annotation.NonNull;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import br.com.picpay.core.api.payment.PaymentApi;
import br.com.picpay.core.listerner.ResponseServer;
import br.com.picpay.core.modelResponse.ErrorResponse;
import br.com.picpay.core.modelResponse.TransactionResponse;
import br.com.picpay.picpay.listerner.ResponseViewListerner;
import br.com.picpay.picpay.mapper.TransactionMapper;
import br.com.picpay.picpay.model.Card;
import br.com.picpay.picpay.model.Transaction;
import br.com.picpay.picpay.model.User;
import br.com.picpay.picpay.util.CardUtil;
import io.card.payment.CreditCard;

@EBean(scope = EBean.Scope.Singleton)
public class PostPaymentCase {

    @Bean
    PaymentApi paymentApi;

    @Bean
    TransactionMapper transactionMapper;

    public void sendPaymeny(@NonNull User user, @NonNull Card card, float value, final ResponseViewListerner<Transaction> listerner) {
        CreditCard creditCard = card.getCreditCard();
        paymentApi.executeTransaction(creditCard.cardNumber, creditCard.cvv, value, CardUtil.getDateFormated(creditCard), user.getId(), new ResponseServer<TransactionResponse>() {
            @Override
            public void success(TransactionResponse response) {
                listerner.success(transactionMapper.responseToTransaction(response));
            }

            @Override
            public void error(ErrorResponse errorResponse) {
                listerner.error(errorResponse.getMen());
            }
        });
    }
}
