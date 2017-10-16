package mobile.picpay.com.br.picpaymobile.infra;

import android.widget.EditText;

/**
 * Created by johonatan on 10/10/2017.
 */

public class Util {

    public boolean validaCamposObrig(EditText campo){
        boolean erro = true;
        if(campo.getText().toString().trim().equals("")){
            campo.setError("Campo obrigatório!");
            return false;
        }
        return erro;
    }
}
