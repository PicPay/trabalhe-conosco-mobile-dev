package br.com.dalcim.picpay.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * @author Wiliam
 * @since 01/09/2017
 */

public final class DialogUtils {

    private DialogUtils(){}

    public static void showConfirmDialog(Context context, String title, String message){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .create().show();
    }
}
