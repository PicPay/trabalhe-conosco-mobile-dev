package viniciusmaia.com.vinipay.modelo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by User on 03/12/2017.
 */

public class CartaoCredito extends RealmObject {
    @PrimaryKey
    private int id;
    private int idUsuario;
    private String numero;
    private String codigoSeguranca;
    private String validade;

    public CartaoCredito(){}

    public CartaoCredito(int id, int idUsuario, String numero, String codigoSeguranca, String validade) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.numero = numero;
        this.codigoSeguranca = codigoSeguranca;
        this.validade = validade;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero.replace(".", "");
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
