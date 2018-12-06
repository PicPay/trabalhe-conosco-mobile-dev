package test.edney.picpay.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import test.edney.picpay.BuildConfig

class AppUtil(private val context: Context) {

    companion object {

        fun isOreoOrMore(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        }

        fun isLollipopOrMore(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        }

        fun isDebug(): Boolean {
            return BuildConfig.DEBUG
        }
    }

    fun findDrawable(id: Int): Drawable {
        return if (isLollipopOrMore())
            context.resources.getDrawable(id, context.theme)
        else
            context.resources.getDrawable(id)
    }
}