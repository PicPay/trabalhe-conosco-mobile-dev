package com.oliveira.edney.picpay.Class;

import android.support.v4.app.FragmentActivity;
import java.io.File;
import java.io.Serializable;

public class Card implements Serializable {

    private String bandeira;
    private String nome;
    private String numero;
    private String dataExpiracao;
    private int cvv;
    private String cep;

    public Card(){

    }

    public boolean fileExists(FragmentActivity activity){

        File file = activity.getBaseContext().getFileStreamPath("cards");
        boolean existe = false;

        if(file != null)
            existe = file.exists();

        return existe;
    }

    /* Retorna uma string com os últimos dígitos do cartão */
    public String getFinalCard(String numero){

        int tamanho = numero.length();
        int i = tamanho-4;
        StringBuilder finalCard = new StringBuilder();

        while (i < tamanho){

            finalCard.append(numero.charAt(i));
            i++;
        }

        return finalCard.toString();
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(String dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
}