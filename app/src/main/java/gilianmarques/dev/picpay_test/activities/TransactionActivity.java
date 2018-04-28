package gilianmarques.dev.picpay_test.activities;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Currency;
import java.util.Locale;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.asyncs.ProfilePictureUtils;
import gilianmarques.dev.picpay_test.asyncs.TransactionSenderAsynck;
import gilianmarques.dev.picpay_test.models.Contact;
import gilianmarques.dev.picpay_test.models.Transaction;
import gilianmarques.dev.picpay_test.utils.AppPatterns;

public class TransactionActivity extends MyActivity implements TransactionSenderAsynck.TrasactionInterface, View.OnClickListener {
    private Transaction mTransaction;
    private ConstraintLayout parent;
    private CardView mCardView;
    private boolean trasactionOccurWithSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        mTransaction = (Transaction) getIntent().getSerializableExtra("transaction");
        animateBars();
        setContentView(R.layout.activity_transaction);
        mCardView = findViewById(R.id.cv);
        parent = findViewById(R.id.parent);
        sendTransaction();
    }

    /**
     * inicia o upload do json passando um callback para prosseguir quando obter resposta do servidor
     */
    private void sendTransaction() {
        new TransactionSenderAsynck(this, mTransaction).execute();
    }

    /**
     * A ideia é passar do dialogo de confirmação para a activity sem que o usuario perceba
     * por isso animo as barras
     */
    private void animateBars() {
        final Window mWindow = getWindow();

        int toColor = ContextCompat.getColor(this, R.color.colorPrimaryDark);
        int fromColor = ContextCompat.getColor(this, R.color.colorPrimary);


        ObjectAnimator.AnimatorUpdateListener mListener = new ObjectAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                mWindow.setStatusBarColor(animatedValue);
                mWindow.setNavigationBarColor(animatedValue);
            }
        };


        final ObjectAnimator statusBarAnimator = ObjectAnimator.ofInt(null, "", fromColor, toColor);
        statusBarAnimator.setDuration(2000);
        statusBarAnimator.setEvaluator(new ArgbEvaluator());
        statusBarAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        statusBarAnimator.addUpdateListener(mListener);
        statusBarAnimator.start();

    }

    /**
     * @param response a resposta do servidor
     *                 Método pertence a interface passada como callback no momento do upload do json atraves do método sendTransaction()
     */
    @Override
    public void success(JSONObject response) {
        findViewById(R.id.progressBar).setVisibility(View.GONE);

        JSONObject mJsonTransaction = response.optJSONObject("transaction");

        if (mJsonTransaction == null) {
            showErrorMessage("Object transaction inside json was null");
            return;
        }

        boolean succsess = mJsonTransaction.optBoolean("success", false);
        String status = mJsonTransaction.optString("status");

        if (!succsess) showErrorMessage(status);
        else {
            trasactionOccurWithSuccess = true;
            AppPatterns.vibrate(AppPatterns.SUCCESS);
            showVoucher(response);
        }

    }

    /**
     * @param response a resposta do servidor
     *                 <p>
     *                 Exibe o recibo da transação para o usuario
     */
    private void showVoucher(JSONObject response) {
        JSONObject mJsonTransaction = response.optJSONObject("transaction");
        if (mJsonTransaction == null) return;

        String transactioId = mJsonTransaction.optString("id");
        String value = mJsonTransaction.optString("value");
        long timeStamp = mJsonTransaction.optLong("timestamp");

        value = AppPatterns.convertCurrency(value);

        /*Converto valores como R$35,00  ou $35.00 para  R$35 ou $35*/
        if (value.replace(Currency.getInstance(Locale.getDefault()).getSymbol(), "").length() <= 6) {
            if (value.endsWith(",00")) value = value.replace(",00", "");
            else if (value.endsWith(".00")) value = value.replace(".00", "");
        }
        TextView tvAmount = findViewById(R.id.tv_amount);


        Contact mContact = mTransaction.getContact();
        ((TextView) findViewById(R.id.tv_trasaction_id)).setText("#".concat(transactioId));
        ((TextView) findViewById(R.id.tv_date)).setText(AppPatterns.formatDate(timeStamp, false));
        tvAmount.setText(value);
        ((TextView) findViewById(R.id.tv_user_name)).setText(mContact.getName());

        findViewById(R.id.btn_transfer_again).setOnClickListener(this);
        findViewById(R.id.btn_finish).setOnClickListener(this);


        ProfilePictureUtils.Callback mCallback = new ProfilePictureUtils.Callback() {
            @Override
            public void result(Drawable mDrawable) {
                ((ImageView) findViewById(R.id.iv_contact_pic)).setImageDrawable(mDrawable);
            }
        };

        ProfilePictureUtils.getPicAsync(mContact, mCallback);

        /* Aplica um gradiente no textView*/
        tvAmount.getPaint().setShader(new RadialGradient(0, 0, 1000, ContextCompat.getColor(this, R.color.gradient_1)
                , ContextCompat.getColor(this, R.color.gradient_2), Shader.TileMode.REPEAT));

        animate();


    }

    private void animate() {

        AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);
        alphaAnim.setDuration(300);

        TranslateAnimation transAnim = new TranslateAnimation(0f, 0f, mCardView.getY() + mCardView.getMeasuredHeight(), 0f);
        transAnim.setDuration(500);
        transAnim.setInterpolator(new DecelerateInterpolator());

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(alphaAnim);
        set.addAnimation(transAnim);
        mCardView.startAnimation(set);


        mCardView.setVisibility(View.VISIBLE);
        mCardView.startAnimation(set);

    }

    /**
     * @param errorMessage a causa do erro
     *                     Método pertence a interface passada como callback no momento do upload do json atraves do método sendTransaction()
     */
    @Override
    public void fail(final String errorMessage) {
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                showErrorMessage(errorMessage);
            }
        };

        // esse metodo pode ser chamado de uma WorkerThread e isso jogaria uma exceçao por causa das instruçoes contidas em showErrorMessage()
        runOnUiThread(mRunnable);
    }

    private void showErrorMessage(String status) {
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        AppPatterns.vibrate(AppPatterns.ERROR);
        View.OnClickListener mListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTransaction();
            }
        };
        Toast.makeText(this, "Cause: ".concat(status), Toast.LENGTH_LONG).show();
        Snackbar.make(parent, getString(R.string.a_transferencia_falhou), Snackbar.LENGTH_INDEFINITE).setAction(R.string.Tentar_novamente, mListener).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_transfer_again) {
            finish();
            startActivity(new Intent(this, SendCashActivity.class));
        } else if (id == R.id.btn_finish) {
            finish();
            /*N preciso iniciar a MainActivity pq ela já esta aberta, basta fechar essa activity que ela voltara ao topo da pilha */
            //startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        /*permito ao usuario voltar com o botão voltar apenas se a transação falhou, assim ele pode
         * tentar novamente usando a ação do Snackbar ou apertar volta para volta a MainActivity. Se a transação foi
         * bem sucedida ele usará os botões no dialogo pq eu n os coloquei lá atoa, mas n coloquei msm!*/
        if (!trasactionOccurWithSuccess) super.onBackPressed();
    }
}


