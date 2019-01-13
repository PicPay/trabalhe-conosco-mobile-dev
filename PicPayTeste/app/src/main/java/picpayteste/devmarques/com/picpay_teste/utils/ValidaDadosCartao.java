package picpayteste.devmarques.com.picpay_teste.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidaDadosCartao {


    public boolean getValidaNumeroCartao(String numerocartao) {
        if (numerocartao != null) {
            if (numerocartao.length() >= 13) {
                return true;
            }
        }
        return false;
    }

    public boolean getValidaNome(String nome) {
        if (nome != null) {
            if (nome.length() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean getValidaCVV(String cvv) {
        if (cvv != null) {
            if (cvv.length() == 3) {
                return true;
            }
        }
        return false;
    }

    public boolean getValidaData(String data) {

        String mes, ano;
        int mesInt, anoInt;

        int anoSistema = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int mesSistema = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        if (data != null) {
            if (data.length() > 0) {
                mes = data.substring(0, 2);
                ano = data.substring(3, 5);

                mesInt = Integer.parseInt(mes);
                anoInt = Integer.parseInt(("20" + ano));

                if ((anoInt >= anoSistema) && (mesInt <= 12)) {
                    if (anoInt == anoSistema){
                        if (mesInt > (mesSistema + 1)){
                            return true;
                        }
                    }else {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
