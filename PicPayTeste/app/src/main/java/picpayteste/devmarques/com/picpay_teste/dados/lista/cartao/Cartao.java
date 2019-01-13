package picpayteste.devmarques.com.picpay_teste.dados.lista.cartao;

import java.io.Serializable;

public class Cartao  implements Serializable {

    public static final String PARAM_CARD = "PARAM_CARD";
    public static final String PARAM_CARD_EDIT = "PARAM_CARD_EDIT";
    private long id;
    private String nome_titular;
    private String card_number;
    private String expiry_date;
    private String cvv;

    public Cartao(long id, String nome_titular, String card_number, String expiry_date, String cvv) {
        this.id = id;
        this.nome_titular = nome_titular;
        this.card_number = card_number;
        this.expiry_date = expiry_date;
        this.cvv = cvv;
    }

    public Cartao( String nome_titular, String card_number, String expiry_date, String cvv) {
        this.nome_titular = nome_titular;
        this.card_number = card_number;
        this.expiry_date = expiry_date;
        this.cvv = cvv;
    }

    public Cartao() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome_titular() {
        return nome_titular;
    }

    public void setNome_titular(String nome_titular) {
        this.nome_titular = nome_titular;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
