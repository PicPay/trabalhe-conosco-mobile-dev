package gilianmarques.dev.picpay_test.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.fragments.ContactsListFragment;
import gilianmarques.dev.picpay_test.fragments.PaymentFragment;
import gilianmarques.dev.picpay_test.models.Contact;
import gilianmarques.dev.picpay_test.utils.TransactionCallback;

public class SendCashActivity extends AppCompatActivity implements TransactionCallback {
    private Animation fadeOut, fadeIn;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_cash);

        container = findViewById(R.id.container);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeOut.setInterpolator(new AccelerateDecelerateInterpolator());

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setElevation(15f);


        }


        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, ContactsListFragment.newInstance(SendCashActivity.this), ContactsListFragment.class.getSimpleName()) .commit();


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void contactSelected(final Contact mContact) {
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, PaymentFragment.newInstance(SendCashActivity.this, mContact))
                        .addToBackStack(null).commit();
                container.startAnimation(fadeIn);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        container.startAnimation(fadeOut);

    }


    @Override
    public void creditCardSelected() {

    }
}
