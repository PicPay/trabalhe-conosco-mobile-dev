package br.com.picpay.picpay.validate;

import android.text.TextUtils;

import br.com.picpay.picpay.R;
import br.com.picpay.picpay.custom.TitleEditText;

public class NameValidator implements IValidate {

    private final TitleEditText titleEditText;

    public NameValidator(TitleEditText titleEditText) {
        this.titleEditText = titleEditText;
    }

    @Override
    public boolean validate() {
        String name = titleEditText.getText();
        if (TextUtils.isEmpty(name)) {
            titleEditText.setErrorMessage(titleEditText.getContext().getResources().getText(R.string.campo_obrigatorio).toString());
            return false;
        } else {
            titleEditText.setErrorMessage(titleEditText.getContext().getResources().getText(R.string.nome_invalido).toString());
            return isValid(name);
        }
    }

    private boolean isValid(String name) {
        return name.split("\\w+").length > 1;
    }
}

