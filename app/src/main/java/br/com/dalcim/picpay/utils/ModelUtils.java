package br.com.dalcim.picpay.utils;

import android.content.Intent;

import br.com.dalcim.picpay.data.User;

/**
 * @author Wiliam
 * @since 27/08/2017
 */

public final class ModelUtils {

    private static final String EXTRA_USER_ID = "id";
    private static final String EXTRA_USER_NAME = "name";
    private static final String EXTRA_USER_USERNAME = "username";
    private static final String EXTRA_USER_IMG = "img";
    private ModelUtils(){}

    public static User intentToUser(Intent intent){
        User user = new User();
        user.setId(intent.getLongExtra(EXTRA_USER_ID, -1));
        user.setName(intent.getStringExtra(EXTRA_USER_NAME));
        user.setUsername(intent.getStringExtra(EXTRA_USER_USERNAME));
        user.setImg(intent.getStringExtra(EXTRA_USER_IMG));
        return user;
    }

    public static void populeIntent(Intent intent, User user) {
        intent.putExtra(EXTRA_USER_ID, user.getId());
        intent.putExtra(EXTRA_USER_NAME, user.getName());
        intent.putExtra(EXTRA_USER_USERNAME, user.getUsername());
        intent.putExtra(EXTRA_USER_IMG, user.getImg());
    }
}
