package com.tmontovaneli.picpaychallenge.model;

import com.tmontovaneli.picpaychallenge.util.DataHelper;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tmontovaneli on 22/08/17.
 */

public class Cartao implements Serializable {

    private String numero;

    private String cvv;

    private Date expiracao;

    public Cartao(String numero, String cvv, Date expiracao) {
        this.numero = numero;
        this.cvv = cvv;
        this.expiracao = expiracao;
    }

    public Cartao() {
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Date getExpiracao() {
        return expiracao;
    }

    public void setExpiracao(Date expiracao) {
        this.expiracao = expiracao;
    }

    public String getDataExpiracaoFormatada(){
        if(expiracao == null)
            return null;

        return DataHelper.formatMMyyyy(expiracao);
    }
}
