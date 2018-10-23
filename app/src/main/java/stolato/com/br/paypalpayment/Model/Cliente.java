package stolato.com.br.paypalpayment.Model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by luiz on 19/06/17.
 */

public class Cliente implements Serializable {
    private Integer id;
    private String img,name,username;

    public Cliente(Integer id, String img, String name, String username) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
