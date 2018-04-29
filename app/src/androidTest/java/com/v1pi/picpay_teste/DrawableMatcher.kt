package com.v1pi.picpay_teste

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import android.support.v4.graphics.drawable.DrawableCompat
import android.os.Build
import android.graphics.drawable.VectorDrawable
import android.graphics.drawable.BitmapDrawable


//https://medium.com/@dbottillo/android-ui-test-espresso-matcher-for-imageview-1a28c832626f
class DrawableMatcher(private val expectedId : Int) : TypeSafeMatcher<View>() {
    private var resourceName : String = ""


    override fun describeTo(description: Description?) {

        description?.let {
            it.appendText("with drawable from resource id: ");
            it.appendValue(expectedId);
            it.appendText("[");
            it.appendText(resourceName);
            it.appendText("]");
        }

    }

    override fun matchesSafely(item: View?): Boolean {
        if(item !is ImageView)
            return false

        if(expectedId < 0)
            return item.drawable == null

        val expectedDrawable = item.resources.getDrawable(expectedId, item.context.theme) ?: return false
        resourceName = item.resources.getResourceEntryName(expectedId)

        val bitmap = getBitmap(item.drawable)
        val otherBitmap = getBitmap(expectedDrawable)

        return bitmap.sameAs(otherBitmap)
    }

    //https://medium.com/@enrico.gueli_42167/hi-thank-you-for-your-article-c0efd11c6dee
    private fun getBitmap(d: Drawable): Bitmap {
        var drawable = d
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable).mutate()
        }

        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

}