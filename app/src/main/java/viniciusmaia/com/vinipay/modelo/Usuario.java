package viniciusmaia.com.vinipay.modelo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 03/12/2017.
 */

public class Usuario {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String nome;

    @SerializedName("img")
    private String imagemUrl;

    @SerializedName("username")
    private String usuario;

    public Usuario(){}

    public Usuario(int id, String nome, String imagemUrl, String usuario) {
        this.id = id;
        this.nome = nome;
        this.imagemUrl = imagemUrl;
        this.usuario = usuario;
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

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
