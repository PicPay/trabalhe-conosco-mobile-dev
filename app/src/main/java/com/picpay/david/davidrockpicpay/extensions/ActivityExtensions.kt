package com.picpay.david.davidrockpicpay.extensions

import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.getString(@StringRes resId: Int): String = resources.getString(resId)

fun AppCompatActivity.getString(@StringRes resId: Int, vararg args: Any): String = resources.getString(resId, *args)
