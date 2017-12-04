package viniciusmaia.com.vinipay.modelo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 04/12/2017.
 */

public class ResponseTransacao {
    @SerializedName("transaction")
    ResultadoTransacao resultado;

    public ResponseTransacao(){}

    public ResponseTransacao(ResultadoTransacao resultado) {
        this.resultado = resultado;
    }

    public ResultadoTransacao getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoTransacao resultado) {
        this.resultado = resultado;
    }
}
