package gilianmarques.dev.picpay_test.activities;

import android.content.Intent;
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
import gilianmarques.dev.picpay_test.models.CreditCard;
import gilianmarques.dev.picpay_test.models.Transaction;
import gilianmarques.dev.picpay_test.utils.DialogConfirmTransaction;
import gilianmarques.dev.picpay_test.utils.TransactionCallback;

public class SendCashActivity extends MyActivity implements TransactionCallback {
    private Animation fadeOut, fadeIn;
    private LinearLayout container;
    private Transaction mTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_cash);
        // impede o teclado de reajustar as views na tela quando for exibido
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        container = findViewById(R.id.container);

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

        // poe o fragmento com os contatos na tela
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, ContactsListFragment.newInstance(SendCashActivity.this))
                .commit();


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * @param mContact O contato selecionado pelo usuario no fragmento de contatos
     *                 <p>
     *                 Inicializa o objeto transação, salva nele uma instancia de contato e chama o proximo fragmento
     */
    @Override
    public void contactSelected(final Contact mContact) {
        mTransaction = new Transaction();
        mTransaction.setContact(mContact);
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

    /**
     * @param choosedCreditCard o cartão selecionado
     * @param amount             o valor selecionado
     *
     *  seta na transação o cartão de credito selecionado pelo usuario e o valor da transação
     *
     *
     */
    @Override
    public void cardAndAmountSet(CreditCard choosedCreditCard, float amount) {
        mTransaction.setCard(choosedCreditCard);
        mTransaction.setAmount(amount);
        confirmTrasaction();
    }

    /**
     * Exibe um dialogo para que o usuario possa conferir os dados da transação e confirma-la
     */
    private void confirmTrasaction() {
        DialogConfirmTransaction.DialogCallback mCallback = new DialogConfirmTransaction.DialogCallback() {
            @Override
            public void trasactionConfirmed() {
                SendCashActivity.this.overridePendingTransition(0, 0);
                startActivity(new Intent(SendCashActivity.this, TransactionActivity.class).putExtra("transaction", mTransaction));
                finish();
            }
        };
        new DialogConfirmTransaction(this, mTransaction, mCallback, container).show();
    }
}
