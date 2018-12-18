package br.com.kassianoresende.picpay.util

import android.content.Context
import android.content.SharedPreferences


class CkeckoutUserPrefs (context: Context) {
    val PREFS_FILENAME = "br.com.kassianoresende.picpay.prefs"

    private val USER_ID = "USER_ID"
    private val USER_NAME = "USER_NAME"
    private val USER_IMAGE = "USER_IMAGE"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var userId: Int
        get() = prefs.getInt(USER_ID, 0)
        set(value) = prefs.edit().putInt(USER_ID, value).apply()

    var userName: String
        get() = prefs.getString(USER_NAME,"")
        set(value) = prefs.edit().putString(USER_NAME, value).apply()

    var userImage: String
        get() = prefs.getString(USER_IMAGE, "")
        set(value) = prefs.edit().putString(USER_IMAGE, value).apply()


    fun clear(){
        prefs.edit().clear().apply()
    }

}