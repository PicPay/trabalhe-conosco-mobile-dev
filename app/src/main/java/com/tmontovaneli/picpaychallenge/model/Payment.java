package com.tmontovaneli.picpaychallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by tmontovaneli on 23/08/17.
 */

public class Payment implements Serializable {

    @JsonProperty("card_number")
    private String nrCartao;

    private Integer cvv;

    private double value;

    @JsonProperty("expiry_date")
    private String dtExpiracao;

    @JsonProperty("destination_user_id")
    private Integer idUser;

    public String getNrCartao() {
        return nrCartao;
    }

    public void setNrCartao(String nrCartao) {
        this.nrCartao = nrCartao;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDtExpiracao() {
        return dtExpiracao;
    }

    public void setDtExpiracao(String dtExpiracao) {
        this.dtExpiracao = dtExpiracao;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
