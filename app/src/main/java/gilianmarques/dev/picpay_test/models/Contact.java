package gilianmarques.dev.picpay_test.models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Contact implements Serializable {

    private int id;
    private String name, photo, userName;
    /**
     * Este bitmap tem como única funcionalidade evitar o carregamento desnecessário de bitmaps do servidor
     * para as views do {@link android.support.v7.widget.RecyclerView}. Uma abordagem mais simples como alternativa
     * para a criação de uma lógica para cacheamento das imagens de perfil dos contatos.
     *
     * NÂO DEVE PERSISTIR
     */
    private Drawable photoDrawable;


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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Drawable getPhotoDrawable() {
        return photoDrawable;
    }

    public void setPhotoDrawable(Drawable photoDrawable) {
        this.photoDrawable = photoDrawable;
    }
}

