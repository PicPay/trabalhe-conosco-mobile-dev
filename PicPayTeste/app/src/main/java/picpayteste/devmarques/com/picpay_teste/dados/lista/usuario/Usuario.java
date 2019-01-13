package picpayteste.devmarques.com.picpay_teste.dados.lista.usuario;

import java.io.Serializable;

public class Usuario implements Serializable {

    public static final String PARAM_USER_SELECTED = "PARAM_USER_SELECTED";

    /*"id":1001,
      "name":"Eduardo Santos",
      "img":"https://randomuser.me/api/portraits/men/9.jpg",
      "username"*/

    private int id;
    private String name;
    private String img;
    private String username;

    public Usuario(int id, String name, String img, String username) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.username = username;
    }



    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
