package com.v1pi.picpay_teste

import android.support.test.espresso.Root
import android.view.WindowManager
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher


class ToastMatcher : TypeSafeMatcher<Root>() {
    override fun describeTo(description: Description) {
        description.appendText("is toast")
    }

    override fun matchesSafely(root: Root): Boolean {
        val type = root.windowLayoutParams.get().type
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            val windowToken = root.decorView.windowToken
            val appToken = root.decorView.applicationWindowToken
            if (windowToken === appToken) {
                //means this window isn't contained by any other windows.
                return true
            }
        }
        return false
    }
}