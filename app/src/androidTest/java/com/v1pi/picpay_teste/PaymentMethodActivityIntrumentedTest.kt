package com.v1pi.picpay_teste

import android.app.Activity
import android.app.Instrumentation
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.ActivityResultMatchers.hasResultData
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.util.Log
import com.v1pi.picpay_teste.Database.DatabaseManager
import com.v1pi.picpay_teste.Domains.CreditCard
import com.v1pi.picpay_teste.Domains.User
import com.v1pi.picpay_teste.EspressoTestsMatchers.Companion.noDrawable
import com.v1pi.picpay_teste.EspressoTestsMatchers.Companion.withDrawable
import com.v1pi.picpay_teste.Fragments.WithCreditCardFragment
import com.v1pi.picpay_teste.Fragments.WithoutCreditCardFragment
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.*


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

    @Before
    fun setUp(){
        Intents.init()
        Intents.intending(not(IntentMatchers.isInternal())).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
    }

    @After
    fun finished(){
        Intents.release()
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

    @Test
    fun shouldHasLayoutForNoCreditCard() {
        activityTestRule.activity.changeCreditCardFragment(WithoutCreditCardFragment())
        onView(withId(R.id.alert_image)).check(matches(withDrawable(R.drawable.ic_alert)))
        onView(withId(R.id.txt_none_credit_card)).check(matches(withText(R.string.none_credit_card_text)))
        onView(withId(R.id.txt_register_now)).check(matches(withText(R.string.register_now_text)))
    }

    @Test
    fun shouldHasLayoutForCreditCard(){

        val creditCard = CreditCard(0, "1111 1111 1111 1111", 700, "23/04/2028")
        val withCreditCardFragment = WithCreditCardFragment()
        val bundle = Bundle()
        bundle.putString("number", creditCard.number)
        withCreditCardFragment.arguments = bundle


        activityTestRule.activity.changeCreditCardFragment(withCreditCardFragment)
        onView(withId(R.id.credit_card_image)).check(matches(withDrawable(R.drawable.ic_credit_card)))
        onView(withId(R.id.txt_payment_method)).check(matches(withText(R.string.payment_method_text)))
        onView(withId(R.id.txt_credit_card)).check(matches(withText(R.string.credit_card_example)))
    }

    @Test
    fun shouldCallChooseCreditCard() {
        onView(withId(R.id.fragment_credit_card)).perform(click())
        intended(allOf(toPackage(ChooseCreditCardActivity::class.java.`package`.name)))
    }

    @Test
    fun shouldGetDataForActivityResult() {
        val intent = Intent()
        val creditCard = CreditCard(0, "1111 1111 1111 1111", 520, "10/26")
        intent.putExtra("number", creditCard.number)
        intent.putExtra("uid", creditCard.uid)
        intent.putExtra("cvv", creditCard.cvv)
        intent.putExtra("expiry_date", creditCard.expiry_date)

        intending(hasComponent(ChooseCreditCardActivity::class.java.name)).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, intent))
        activityTestRule.activity.startActivityForResult(Intent(activityTestRule.activity, ChooseCreditCardActivity::class.java), 1)
        val newCreditCard = activityTestRule.activity.controller.selectedCreditCard
        assert(newCreditCard == creditCard)
    }


}