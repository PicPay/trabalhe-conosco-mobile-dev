package gilianmarques.dev.picpay_test.utils;

import android.view.View;

import gilianmarques.dev.picpay_test.models.Contact;

public interface TransactionCallback {

    void contactSelected( Contact mContact);
    void creditCardSelected();
}
