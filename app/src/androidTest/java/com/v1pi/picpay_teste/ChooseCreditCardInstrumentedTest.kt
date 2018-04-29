package com.v1pi.picpay_teste

import android.app.Activity
import android.app.Instrumentation
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.ActivityResultMatchers.hasResultCode
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.matcher.IntentMatchers.toPackage
import android.support.test.espresso.matcher.ViewMatchers.assertThat
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChooseCreditCardInstrumentedTest {

    @get: Rule
    val activityTestRule = ActivityTestRule(ChooseCreditCardActivity::class.java)

    @Before
    fun setUp(){
        Intents.init()
        Intents.intending(CoreMatchers.not(IntentMatchers.isInternal())).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
    }

    @After
    fun finished(){
        Intents.release()
    }

    @Test
    fun shouldCallRegister() {
        onView(withId(R.id.smButton_new_credit_card)).perform(click())
        intended(allOf(toPackage(CreateCreditCardActivity::class.java.`package`.name)))
    }

    @Test
    fun shouldCallSetResult () {
        onView(withId(R.id.btn_select_credit_card)).perform(click())
        assertThat(activityTestRule.activityResult, hasResultCode(Activity.RESULT_OK))
    }
}