package mobile.picpay.com.br.picpaymobile.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by johonatan on 03/10/2017.
 */

public class Usuario extends RealmObject {
    @PrimaryKey
    private int id;
    private String nome;
    private String username;
    private String senha;
    private String email;
    private String numcard;
    private String cvv;
    private String dataexp;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumcard() {
        return numcard;
    }

    public void setNumcard(String numcard) {
        this.numcard = numcard;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getDataexp() {
        return dataexp;
    }

    public void setDataexp(String dataexp) {
        this.dataexp = dataexp;
    }

}
