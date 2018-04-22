package com.v1pi.picpay_teste

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.v1pi.picpay_teste.Adapter.UserListAdapter
import com.v1pi.picpay_teste.Domains.User
import com.v1pi.picpay_teste.Utils.RequestState
import org.hamcrest.CoreMatchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityInstrumentedTest {
    // Para testes de intent tem que usar essa IntentTestRule
    @get: Rule
    val activityTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun stubAllExternalIntents() {
        // Por padrão Espresso Intents não para as intentes. Para para elas é necessário configurar
        // antes de toda vez que for rodar o test. Nesse caso toda as intents externas são bloqueadas
        intending(not(isInternal())).respondWith(ActivityResult(Activity.RESULT_OK, null))
    }

    @Test
    fun testLaunchActivityOnClickInRecycler() {
        //Gambiarra
        while(RequestState.REQUEST_USER != RequestState.STATES.READY);

        // A requisição deve retornar um usuário desse tipo
        val user = User(1001, "Eduardo Santos", "https://randomuser.me/api/portraits/men/9.jpg", "@eduardo.santos")

        onView(withId(R.id.user_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<UserListAdapter.ViewHolder>(0, ViewActions.click()))
        //onView(withText(R.string.test_recyclerview_id)).inRoot(ToastMatcher()).check(matches(isDisplayed()))

        intended(allOf(toPackage(PaymentMethodActivity::class.java.`package`.name), hasExtras(allOf(
                hasEntry(equalTo("name"), equalTo(user.name)),
                hasEntry(equalTo("username"), equalTo(user.username)),
                hasEntry(equalTo("img"), equalTo(user.img)),
                hasEntry(equalTo("id"), equalTo(user.id.toString())))
        )))
    }
}