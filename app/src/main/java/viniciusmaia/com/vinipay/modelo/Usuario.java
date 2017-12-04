package viniciusmaia.com.vinipay.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by User on 03/12/2017.
 */

public class Usuario extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String nome;

    @Ignore
    @SerializedName("img")
    private String imagemUrl;

    @SerializedName("username")
    private String usuario;

    @Expose(serialize = false)
    private String senha;

    public Usuario(){}

    public Usuario(int id, String nome, String imagemUrl, String usuario, String senha) {
        this.id = id;
        this.nome = nome;
        this.imagemUrl = imagemUrl;
        this.usuario = usuario;
        this.senha = senha;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
