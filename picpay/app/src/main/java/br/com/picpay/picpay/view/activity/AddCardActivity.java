package br.com.picpay.picpay.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Button;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import br.com.picpay.picpay.R;
import br.com.picpay.picpay.contract.AddCardContract;
import br.com.picpay.picpay.custom.CardDateEditText;
import br.com.picpay.picpay.custom.CreditCardEditText;
import br.com.picpay.picpay.custom.Form;
import br.com.picpay.picpay.custom.TitleEditText;
import br.com.picpay.picpay.model.Card;
import br.com.picpay.picpay.presenter.AddCardPresenter;
import br.com.picpay.picpay.util.CardUtil;
import io.card.payment.CreditCard;

@EActivity(R.layout.activity_add_card)
public class AddCardActivity extends BaseActivity implements AddCardContract.AddCardView {

    @Bean
    AddCardPresenter presenter;

    @Extra
    @InstanceState
    Card card;

    @ViewById
    Form form;

    @ViewById
    TitleEditText numberCard, validateCard, cvv, nameCard;

    @ViewById
    Button btnSaveCard;

    @Override
    public void init() {
        super.init();
        presenter.setView(this);
        if (title != null) {
            title.setText(getString(card == null ? R.string.register_card : R.string.update_card));
        }
        if (card != null) {
            CreditCard creditCard = card.getCreditCard();
            numberCard.setTextEditText(creditCard.cardNumber);
            if (creditCard.expiryMonth > 0 && creditCard.expiryYear > 0) {
                validateCard.setTextEditText(CardUtil.getDateFormated(creditCard).replace("/", ""));
            }
            cvv.setTextEditText(creditCard.cvv);
            nameCard.setTextEditText(creditCard.cardholderName);
        }
    }

    @Override
    protected void onPause() {
        saveCardInstance();
        super.onPause();
    }

    private void saveCardInstance() {
        if (card == null) {
            card = new Card();
        }

        CreditCard creditCard = new CreditCard();

        CreditCard creditCardNumber = ((CreditCardEditText) numberCard.getCustomEditText()).getCreditCard();
        creditCard.cardNumber = creditCardNumber.cardNumber;

        CreditCard creditCardDate = ((CardDateEditText) validateCard.getCustomEditText()).getCreditCard();
        creditCard.expiryMonth = creditCardDate.expiryMonth;
        creditCard.expiryYear = creditCardDate.expiryYear;

        creditCard.cardholderName = nameCard.getText();
        creditCard.cvv = cvv.getText();
        card.setCreditCard(creditCard);
    }

    @Click(R.id.btn_save_card)
    void onBtnSaveCard() {
        if (form.validate()) {
            saveCardInstance();
            presenter.saveCard(card);
        }
    }

    @Override
    public void onCardSaved(@NonNull Card card) {
        Intent intent = new Intent();
        intent.putExtra(Card.class.getName(), card);
        setResult(RESULT_OK, intent);
        finish();
    }
}

