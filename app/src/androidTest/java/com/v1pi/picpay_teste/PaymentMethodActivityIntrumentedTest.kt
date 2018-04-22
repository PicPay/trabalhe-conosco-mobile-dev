package com.v1pi.picpay_teste

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.v1pi.picpay_teste.Domains.User
import com.v1pi.picpay_teste.EspressoTestsMatchers.Companion.noDrawable
import org.hamcrest.CoreMatchers.not
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class PaymentMethodActivityIntrumentedTest {
    private val user = User(1001, "Eduardo Santos", "https://randomuser.me/api/portraits/men/9.jpg", "@eduardo.santos")

    @get: Rule
    val activityTestRule : ActivityTestRule<PaymentMethodActivity> = object : ActivityTestRule<PaymentMethodActivity>(PaymentMethodActivity::class.java) {
        override fun getActivityIntent() : Intent {
            val intent = Intent()


            intent.putExtra("name", user.name)
            intent.putExtra("username", user.username)
            intent.putExtra("id", user.id.toString())
            intent.putExtra("img", user.img)
            return intent
        }
    }

    @Test
    fun shouldGetIntentExtras(){
        Assert.assertTrue(activityTestRule.activity.controller.user == user)
    }

    @Test
    fun shouldLoadDataInView(){
        onView(withId(R.id.txt_id)).check(matches(withText("id: " + user.id.toString())))
        onView(withId(R.id.txt_name)).check(matches(withText(user.name)))
        onView(withId(R.id.txt_username)).check(matches(withText(user.username)))
        onView(withId(R.id.user_image)).check(matches(not(noDrawable())))
    }

}