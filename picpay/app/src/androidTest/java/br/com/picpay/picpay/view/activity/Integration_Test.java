package br.com.picpay.picpay.view.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.picpay.picpay.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Integration_Test {

    @Rule
    public ActivityTestRule<SplashActivity_> mActivityTestRule = new ActivityTestRule<>(SplashActivity_.class);

    @Test
    public void integration_Test() {
        sleep();

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_users),
                        childAtPosition(
                                withId(R.id.swipe_refresh),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_register_card), withText("Adicionar Cartão"),
                        childAtPosition(
                                allOf(withId(R.id.ll_bottom),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                0),
                        isDisplayed()));

        appCompatButton.perform(click());

        ViewInteraction creditCardEditText = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.number_card),
                                childAtPosition(
                                        withId(R.id.form),
                                        0)),
                        1),
                        isDisplayed()));

        creditCardEditText.perform(click());

        creditCardEditText.perform(replaceText("1111 1111 1111 1111"));

        sleep();

        creditCardEditText.perform(closeSoftKeyboard());

        ViewInteraction cardDateEditText = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.validate_card),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        1),
                        isDisplayed()));
        cardDateEditText.perform(replaceText("1"), closeSoftKeyboard());

        cardDateEditText.perform(replaceText("1023"));

        sleep();

        cardDateEditText.perform(closeSoftKeyboard());

        ViewInteraction customEditText = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.cvv),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2)),
                        1),
                        isDisplayed()));
        customEditText.perform(replaceText("978"), closeSoftKeyboard());

        ViewInteraction customEditText2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.name_card),
                                childAtPosition(
                                        withId(R.id.form),
                                        2)),
                        1),
                        isDisplayed()));
        customEditText2.perform(replaceText("pic pay"), closeSoftKeyboard());

        sleep();

        pressBack();

        sleep();

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_save_card), withText("Salvar"),
                        childAtPosition(
                                allOf(withId(R.id.ll_bottom),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));

        appCompatButton2.perform(click());

        sleep();

        ViewInteraction valueTextEditText = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.value),
                                childAtPosition(
                                        withId(R.id.form),
                                        0)),
                        1),
                        isDisplayed()));
        valueTextEditText.perform(click());

        ViewInteraction valueTextEditText2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.value),
                                childAtPosition(
                                        withId(R.id.form),
                                        0)),
                        1),
                        isDisplayed()));
        valueTextEditText2.perform(replaceText("3"), closeSoftKeyboard());

        ViewInteraction valueTextEditText3 = onView(
                allOf(withText("R$0,03"),
                        childAtPosition(
                                allOf(withId(R.id.value), withText("R$0,03"),
                                        childAtPosition(
                                                withId(R.id.form),
                                                0)),
                                1),
                        isDisplayed()));
        valueTextEditText3.perform(replaceText("R$0,032"));

        ViewInteraction valueTextEditText4 = onView(
                allOf(withText("R$0,032"),
                        childAtPosition(
                                allOf(withId(R.id.value), withText("R$0,03"),
                                        childAtPosition(
                                                withId(R.id.form),
                                                0)),
                                1),
                        isDisplayed()));
        valueTextEditText4.perform(closeSoftKeyboard());

        ViewInteraction valueTextEditText5 = onView(
                allOf(withText("R$0,32"),
                        childAtPosition(
                                allOf(withId(R.id.value), withText("R$0,32"),
                                        childAtPosition(
                                                withId(R.id.form),
                                                0)),
                                1),
                        isDisplayed()));
        valueTextEditText5.perform(replaceText("R$0,320"));

        ViewInteraction valueTextEditText6 = onView(
                allOf(withText("R$0,320"),
                        childAtPosition(
                                allOf(withId(R.id.value), withText("R$0,32"),
                                        childAtPosition(
                                                withId(R.id.form),
                                                0)),
                                1),
                        isDisplayed()));
        valueTextEditText6.perform(closeSoftKeyboard());

        ViewInteraction valueTextEditText7 = onView(
                allOf(withText("R$3,20"),
                        childAtPosition(
                                allOf(withId(R.id.value), withText("R$3,20"),
                                        childAtPosition(
                                                withId(R.id.form),
                                                0)),
                                1),
                        isDisplayed()));
        valueTextEditText7.perform(replaceText("R$3,200"));

        ViewInteraction valueTextEditText8 = onView(
                allOf(withText("R$3,200"),
                        childAtPosition(
                                allOf(withId(R.id.value), withText("R$3,20"),
                                        childAtPosition(
                                                withId(R.id.form),
                                                0)),
                                1),
                        isDisplayed()));
        valueTextEditText8.perform(closeSoftKeyboard());

        ViewInteraction valueTextEditText9 = onView(
                allOf(withText("R$32,00"),
                        childAtPosition(
                                allOf(withId(R.id.value), withText("R$32,00"),
                                        childAtPosition(
                                                withId(R.id.form),
                                                0)),
                                1),
                        isDisplayed()));
        valueTextEditText9.perform(pressImeActionButton());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_confirm), withText("CONFIRMAR"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton4.perform(scrollTo(), click());
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
