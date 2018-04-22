package gilianmarques.dev.picpay_test.utils;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

public class ProfilePicHolder {
    private static final ProfilePicHolder ourInstance = new ProfilePicHolder();

    public static ProfilePicHolder getInstance() {
        return ourInstance;
    }

    private ProfilePicHolder() {
    }

    /**
     * Este hashmap tem como única funcionalidade evitar o carregamento desnecessário de bitmaps do servidor
     * para as views do {@link android.support.v7.widget.RecyclerView}. Uma abordagem mais simples como alternativa
     * para a criação de uma lógica para cacheamento das imagens de perfil dos contatos.
     */
    private HashMap<String, Drawable> hashMap = new HashMap<>();

    public Drawable getPic(String photo) {
        return hashMap.get(photo);
    }

    public void addPic(String photo, Drawable mDrawable) {
        hashMap.put(photo, mDrawable);
    }
}
