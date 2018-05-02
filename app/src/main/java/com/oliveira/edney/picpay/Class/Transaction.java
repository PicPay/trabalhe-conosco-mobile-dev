package com.oliveira.edney.picpay.Class;

import java.io.Serializable;

public class Transaction implements Serializable {

    private String numeroCartao;
    private int cvv;
    private Float valor;
    private String dataExpiracao;
    private int idDestino;

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(String dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
    }
}
