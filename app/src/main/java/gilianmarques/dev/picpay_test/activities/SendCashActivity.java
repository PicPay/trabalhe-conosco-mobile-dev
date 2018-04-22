package gilianmarques.dev.picpay_test.activities;

import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import java.util.List;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.asyncs.ContactsDownloaderAsync;
import gilianmarques.dev.picpay_test.fragments.PaymentFragment;
import gilianmarques.dev.picpay_test.fragments.ContactsListFragment;
import gilianmarques.dev.picpay_test.models.Contact;
import gilianmarques.dev.picpay_test.utils.AppPatterns;
import gilianmarques.dev.picpay_test.utils.TransactionCallback;

public class SendCashActivity extends AppCompatActivity implements TransactionCallback {
    private Animation fadeOut, fadeIn;
    private LinearLayout container;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_cash);

        container = findViewById(R.id.container);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
/*
        if(true){


            ContactsDownloaderAsync.ContactsCallback callback = new ContactsDownloaderAsync.ContactsCallback() {
                @Override
                public void resut(List<Contact> mContacts, int errorCode) {
                    if (errorCode == -1) {
                        final FragmentTransaction mTransaction = getFragmentManager().beginTransaction();
                        mTransaction.addToBackStack(null); mTransaction.replace(R.id.container, new PaymentFragment().newInstance(mContacts.get(0)).attachCallback(SendCashActivity.this)).commit();
                    }
                }
            };
            new ContactsDownloaderAsync(callback).execute();




         return;
        }
*/
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeOut.setInterpolator(new AccelerateDecelerateInterpolator());

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setElevation(0f);
        }

        FragmentTransaction mTransaction = getFragmentManager().beginTransaction();
        mTransaction.add(R.id.container, new ContactsListFragment().attachCallback(this)).commit();


    }

    @Override
    public void contactSelected(final Contact mContact) {
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                final FragmentTransaction mTransaction = getFragmentManager().beginTransaction();
                mTransaction.addToBackStack(null);
                mTransaction.replace(R.id.container, new PaymentFragment().newInstance(mContact).attachCallback(SendCashActivity.this)).commit();
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
