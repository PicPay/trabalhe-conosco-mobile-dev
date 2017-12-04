package viniciusmaia.com.vinipay.modelo;

/**
 * Created by User on 03/12/2017.
 */

public class CartaoCredito {
    private String numero;
    private String codigoSeguranca;
    private String validade;


    public CartaoCredito(){}

    public CartaoCredito(String numero, String codigoSeguranca, String validade) {
        this.numero = numero;
        this.codigoSeguranca = codigoSeguranca;
        this.validade = validade;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public void setCodigoSeguranca(String codigoSeguranca) {
        this.codigoSeguranca = codigoSeguranca;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }
}
