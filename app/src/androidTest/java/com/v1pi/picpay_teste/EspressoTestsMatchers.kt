package com.v1pi.picpay_teste

import android.view.View
import org.hamcrest.Matcher

// Métodos estáticos para ser enxergado diretamente
class EspressoTestsMatchers {
    companion object {
        fun withDrawable(resourceId : Int): Matcher<View> {
            return DrawableMatcher(resourceId)
        }

        fun noDrawable() : Matcher<View> {
            return DrawableMatcher(-1)
        }
    }
}