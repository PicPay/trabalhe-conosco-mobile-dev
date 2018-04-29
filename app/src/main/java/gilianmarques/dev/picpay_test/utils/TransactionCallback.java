package gilianmarques.dev.picpay_test.utils;

import gilianmarques.dev.picpay_test.models.Contact;
import gilianmarques.dev.picpay_test.models.CreditCard;

/**
 * Criada externamente simplesmente para que eu possa implementar na {@link gilianmarques.dev.picpay_test.activities.SendCashActivity}
 * ao invés de criar uma instancia dentro dela
 */
public interface TransactionCallback {

    void contactSelected(Contact mContact);

    void cardAndAmountSet(CreditCard choosedCreditCard, float amount);
}
