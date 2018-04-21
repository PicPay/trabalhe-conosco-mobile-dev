package com.v1pi.picpay_teste

import android.support.test.rule.ActivityTestRule
import com.v1pi.picpay_teste.Utils.RequestState
import org.junit.Rule
import org.junit.Test
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import com.v1pi.picpay_teste.Adapter.UserListAdapter
import org.hamcrest.CoreMatchers.not

class MainActivityInstrumentedTest {
    @get: Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testApiResponse() {
        //Gambiarra
        while(RequestState.REQUEST_USER != RequestState.STATES.READY);

        onView(withId(R.id.user_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<UserListAdapter.ViewHolder>(2, ViewActions.click()))
        onView(withText(R.string.test_recyclerview_id)).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }
}