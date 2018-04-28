package gilianmarques.dev.picpay_test.utils;

import gilianmarques.dev.picpay_test.models.Contact;
import gilianmarques.dev.picpay_test.models.CreditCard;

public interface TransactionCallback {

    void contactSelected( Contact mContact);
    void cardAndAmountSet(CreditCard choosedCreditCard, float amount);
}
