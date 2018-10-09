package br.com.picpay.picpay.validate;

import android.text.TextUtils;

import br.com.picpay.picpay.R;
import br.com.picpay.picpay.custom.CardDateEditText;
import br.com.picpay.picpay.custom.TitleEditText;

public class CardDateValidator implements IValidate {

    private final TitleEditText titleEditText;

    public CardDateValidator(TitleEditText titleEditText) {
        this.titleEditText = titleEditText;
    }

    @Override
    public boolean validate() {
        String date = titleEditText.getText();
        if (TextUtils.isEmpty(date)) {
            titleEditText.setErrorMessage(titleEditText.getContext().getResources().getText(R.string.campo_obrigatorio).toString());
            return false;
        } else {
            titleEditText.setErrorMessage(titleEditText.getContext().getResources().getText(R.string.campo_invalido).toString());
            if (titleEditText.getCustomEditText() instanceof CardDateEditText) {
                CardDateEditText cardDateEditText = (CardDateEditText) titleEditText.getCustomEditText();
                return cardDateEditText.isValid();
            }
            return true;
        }
    }
}
