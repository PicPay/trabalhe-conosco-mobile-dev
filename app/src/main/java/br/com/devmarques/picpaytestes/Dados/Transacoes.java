package br.com.devmarques.picpaytestes.Dados;

/**
 * Created by Roger on 10/11/2017.
 */

public class Transacoes {

    private long id;
    private String nome;
    private String fotoperfil;
    private String user;
    private String cardNameandNumber;
    private String transacaoNum;
    private String valor;
    private String aprovada;
    private String status;

    public Transacoes(String nome, String fotoperfil, String user, String cardNameandNumber, String transacaoNum, String valor, String aprovada, String status) {

        this.nome = nome;
        this.fotoperfil = fotoperfil;
        this.user = user;
        this.cardNameandNumber = cardNameandNumber;
        this.transacaoNum = transacaoNum;
        this.valor = valor;
        this.aprovada = aprovada;
        this.status = status;
    }

    public Transacoes() {
    }

    public String getCardNameandNumber() {
        return cardNameandNumber;
    }

    public void setCardNameandNumber(String cardNameandNumber) {
        this.cardNameandNumber = cardNameandNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFotoperfil() {
        return fotoperfil;
    }

    public void setFotoperfil(String fotoperfil) {
        this.fotoperfil = fotoperfil;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTransacaoNum() {
        return transacaoNum;
    }

    public void setTransacaoNum(String transacaoNum) {
        this.transacaoNum = transacaoNum;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getAprovada() {
        return aprovada;
    }

    public void setAprovada(String aprovada) {
        this.aprovada = aprovada;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
