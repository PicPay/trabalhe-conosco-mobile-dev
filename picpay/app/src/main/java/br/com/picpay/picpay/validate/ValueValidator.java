package br.com.picpay.picpay.validate;

import br.com.picpay.picpay.R;
import br.com.picpay.picpay.custom.TitleEditText;

public class ValueValidator implements IValidate {

    private final TitleEditText titleEditText;

    public ValueValidator(TitleEditText titleEditText) {
        this.titleEditText = titleEditText;
    }

    @Override
    public boolean validate() {
        String valueString = titleEditText.getText().replaceAll("[R$,.]", "");
        try {
            if (Float.parseFloat(valueString) / 100 <= 0) {
                titleEditText.setErrorMessage(titleEditText.getContext().getResources().getText(R.string.campo_obrigatorio).toString());
                return false;
            }
        } catch (Exception e) {
            titleEditText.setErrorMessage(titleEditText.getContext().getResources().getText(R.string.campo_obrigatorio).toString());
            return false;
        }
        return true;
    }
}
