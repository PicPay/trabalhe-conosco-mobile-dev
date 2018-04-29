package com.v1pi.picpay_teste

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.google.gson.Gson
import com.v1pi.picpay_teste.Domains.Transaction
import junit.framework.Assert
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class PaymentStatusActivityInstrumentedTest {
    private val json = "{\"id\":12314,\"timestamp\":1524863585,\"value\":79.9,\"destination_user\":{\"id\":1002,\"name\":\"Marina Coelho\",\"img\":\"https://randomuser.me/api/portraits/women/37.jpg\",\"username\":\"@marina.coelho\"},\"success\":true,\"status\":\"Aprovada\",\"card_number\":\"1111 1111 1111 1111\"}"
    private val transaction = Gson().fromJson(json, Transaction::class.java)

    @get:Rule
    val activityTestRule : ActivityTestRule<PaymentStatusActivity> = object : ActivityTestRule<PaymentStatusActivity>(PaymentStatusActivity::class.java){
        override fun getActivityIntent(): Intent {
            val intent = Intent()
            intent.putExtra("transaction", json)
            return intent
        }
    }

    @Before
    fun setUp(){
        Intents.init()
        Intents.intending(CoreMatchers.not(IntentMatchers.isInternal())).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
    }

    @Test
    fun testTransactionBundle() {
        val user = transaction.destination_user
        onView(withId(R.id.txt_transaction)).check(matches(withText(transaction.id.toString())))
        onView(withId(R.id.txt_card)).check(matches(withText("**** **** **** " + transaction.card_number.substring(transaction.card_number.length-4))))
        transaction.timestamp?.let {
            onView(withId(R.id.txt_date)).check(matches(withText(SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US).format(Date(it *1000)))))
        }
        onView(withId(R.id.txt_value)).check(matches(withText("R$ ${transaction.value}")))


        onView(withId(R.id.txt_id)).check(matches(withText("id: " + user.id.toString())))
        onView(withId(R.id.txt_name)).check(matches(withText(user.name)))
        onView(withId(R.id.txt_username)).check(matches(withText(user.username)))
        onView(withId(R.id.user_image)).check(matches(CoreMatchers.not(EspressoTestsMatchers.noDrawable())))
    }
}