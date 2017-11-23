package br.com.devmarques.picpaytestes.Dados;

/**
 * Created by Roger on 08/11/2017.
 */

public class Pessoas {

    private int id;
    private String nome;
    private String fotoperfil;
    private String user;

    public Pessoas(int id, String nome, String fotoperfil, String user) {
        this.id = id;
        this.nome = nome;
        this.fotoperfil = fotoperfil;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
