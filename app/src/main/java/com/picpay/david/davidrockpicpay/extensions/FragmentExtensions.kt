package com.picpay.david.davidrockpicpay.extensions

import android.support.annotation.StringRes
import android.support.v4.app.Fragment

fun Fragment.getString(@StringRes resId: Int): String = resources.getString(resId)

fun Fragment.getString(@StringRes resId: Int, vararg args: Any): String = resources.getString(resId, *args)
