package br.com.everaldocardosodearaujo.picpay.Object;

/**
 * Created by E. Cardoso de Araújo on 14/03/2018.
 */

public class UsersObject {
    private long id;
    private String name;
    private String img;
    private String username;

    public UsersObject(){
        this.id = 0;
        this.name = "";
        this.img = "";
        this.username = "";
    }

    public UsersObject(long id, String name, String img, String username){
        this.id = id;
        this.name = name;
        this.img = img;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
