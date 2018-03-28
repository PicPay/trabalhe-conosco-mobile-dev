package mobiledev.erickgomes.picpayapp.utils;

import android.text.TextUtils;

/**
 * Created by erickgomes on 24/03/2018.
 */

public class Validation {


    public static boolean validateCardNumber(String cardNumber){
        return ( cardNumber.length() >= 16); //verifica se o cartão não possui 16 Dígitos
    }

    public static boolean validateCvv(String cvv){
        return (cvv.length() >= 3); //verifica se o código de segurança não possui 3 digitos
    }

    public static boolean validateExpiration(String expiration){
        return (expiration.length() >= 5); //verifica de se a data de validade não possui 5 digitos
    }


    public static boolean validateEmptyField(String text) { //verifica se um campo qualquer está vazio
        return !TextUtils.isEmpty(text);
    }
}
