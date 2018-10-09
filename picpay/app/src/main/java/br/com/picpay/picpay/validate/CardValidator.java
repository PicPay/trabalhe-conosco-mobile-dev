package br.com.picpay.picpay.validate;

import android.text.TextUtils;

import br.com.picpay.picpay.R;
import br.com.picpay.picpay.custom.TitleEditText;

public class CardValidator implements IValidate {

    private final TitleEditText titleEditText;

    public CardValidator(TitleEditText titleEditText) {
        this.titleEditText = titleEditText;
    }

    @Override
    public boolean validate() {
        String cardNumber = titleEditText.getText();
        if (TextUtils.isEmpty(cardNumber)) {
            titleEditText.setErrorMessage(titleEditText.getContext().getResources().getText(R.string.campo_obrigatorio).toString());
            return false;
        } else {
            titleEditText.setErrorMessage(titleEditText.getContext().getResources().getText(R.string.campo_invalido).toString());
            return titleEditText.isCardValid();
        }
    }
}
