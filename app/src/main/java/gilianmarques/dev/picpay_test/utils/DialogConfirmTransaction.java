package gilianmarques.dev.picpay_test.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.asyncs.ProfilePictureUtils;
import gilianmarques.dev.picpay_test.models.Contact;
import gilianmarques.dev.picpay_test.models.CreditCard;
import gilianmarques.dev.picpay_test.models.Transaction;

public class DialogConfirmTransaction extends AlertDialog implements View.OnClickListener {
    private final Activity mActivity;
    private final DialogCallback dialogCallback;
    private final Transaction mTransaction;
    private View rootView;
    private View parent;
    private final View actContainer;
    private final long ALPHA_ANIM_DURATION = 300;
    private final long TRANSLATE_ANIM_DURATION = 400;
    private final float DP;
    private TranslateAnimation floatAnim;
    private CardView mCardView;
    private SharedPreferences mPreferences;

    public DialogConfirmTransaction(@NonNull Activity mActivity, Transaction mTransaction, @NonNull DialogCallback dialogCallback, @Nullable View container) {
        super(mActivity, R.style.AppTheme_NoActionBar_Translucent);
        this.mActivity = mActivity;
        this.dialogCallback = dialogCallback;
        this.mTransaction = mTransaction;
        this.actContainer = container;
        DP = mActivity.getResources().getDisplayMetrics().density;
        init();
    }

    private void init() {

        rootView = mActivity.getLayoutInflater().inflate(R.layout.dialog_confirm_trasaction, null);
        parent = rootView.findViewById(R.id.fade_parent);
        mCardView = rootView.findViewById(R.id.card_view);
        mCardView.setOnTouchListener(new LayoutAnimation());

        mPreferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
        if (!mPreferences.getBoolean("first_transaction", true))
            rootView.findViewById(R.id.tv_hint).setVisibility(View.GONE);

        /*Animações de abertura do diálogo*/
        mCardView.post(new Runnable() {
            @Override
            public void run() {
                floatAnim = new TranslateAnimation(0, 0, -(DP * 2), (DP * 2));
                floatAnim.setRepeatCount(Animation.INFINITE);
                floatAnim.setRepeatMode(Animation.REVERSE);
                floatAnim.setDuration(800);
                floatAnim.setInterpolator(new AccelerateDecelerateInterpolator());
                //
                AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);
                alphaAnim.setDuration(ALPHA_ANIM_DURATION);
                rootView.findViewById(R.id.fade_parent_black).startAnimation(alphaAnim);

                TranslateAnimation transAnim = new TranslateAnimation(0f, 0f, 250 * DP, 0f);
                transAnim.setDuration(TRANSLATE_ANIM_DURATION);
                transAnim.setInterpolator(new OvershootInterpolator());

                AnimationSet set = new AnimationSet(false);
                set.addAnimation(alphaAnim);
                set.addAnimation(transAnim);
                mCardView.startAnimation(set);

                Runnable mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        mCardView.startAnimation(floatAnim);
                    }
                };
                new Handler().postDelayed(mRunnable, set.getDuration() / 10 * 9);

            }
        });

        ProfilePictureUtils.getPicAsync(mTransaction.getContact(), new ProfilePictureUtils.Callback() {
            @Override
            public void result(Drawable mDrawable) {
                ((ImageView) rootView.findViewById(R.id.iv_contact_pic)).setImageDrawable(mDrawable);
            }
        });

        rootView.findViewById(R.id.btn_alterar_dados).setOnClickListener(this);

        applyStyles();

        Window mWindow = getWindow();
        if (mWindow != null) {
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        }

        setView(rootView);
        setCancelable(false);
    }

    /**
     * Customiza o texto que será exibido na tela
     */
    private void applyStyles() {
        CreditCard mCard = mTransaction.getCard();
        Contact mContact = mTransaction.getContact();

        String payment = String.format(Locale.getDefault(), mActivity.getString(R.string.cartao_info), mCard.getBrandName(), mCard.getLastFourDigits());
        Spannable span1 = new SpannableString(payment);

        int start1 = 0;
        int end1 = mCard.getBrandName().length();
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        int start2 = payment.length() - 4;
        int end2 = payment.length();
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        ((TextView) rootView.findViewById(R.id.tv_payment_info)).setText(span1);
        ((TextView) rootView.findViewById(R.id.tv_id)).setText(String.format(Locale.getDefault(), mActivity.getString(R.string.id), mContact.getId()));
        ((TextView) rootView.findViewById(R.id.tv_user_name)).setText(mContact.getUserName());
        ((TextView) rootView.findViewById(R.id.tv_contact_id)).setText(mContact.getName());
        ((TextView) rootView.findViewById(R.id.tv_amount)).setText(AppPatterns.convertCurrency(String.valueOf(mTransaction.getAmount())));


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_alterar_dados) {


            AlphaAnimation anim2 = new AlphaAnimation(1, 0);
            anim2.setDuration(ALPHA_ANIM_DURATION);
            anim2.setFillAfter(true);
            rootView.findViewById(R.id.fade_parent_black).startAnimation(anim2);
            //


            TranslateAnimation transAnim = new TranslateAnimation(0, 0, 0, 400);
            transAnim.setDuration(ALPHA_ANIM_DURATION);
            transAnim.setFillAfter(true);
            transAnim.setInterpolator(new AccelerateDecelerateInterpolator());

            AnimationSet set = new AnimationSet(false);
            set.addAnimation(anim2);
            set.addAnimation(transAnim);

            rootView.findViewById(R.id.card_view).startAnimation(set);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, set.getDuration() / 10 * 8);

        }
    }


    public interface DialogCallback {
        void trasactionConfirmed();
        //  void viewMoved(int percentValue);
    }

    private void finish(long duration) {
        Runnable mRunnableCallback = new Runnable() {
            @Override
            public void run() {
                mPreferences.edit().putBoolean("first_transaction", false).apply();
                dialogCallback.trasactionConfirmed();
            }
        };

        new Handler().postDelayed(mRunnableCallback, (duration / 10) * 6);
    }


    class LayoutAnimation implements View.OnTouchListener {
        private final long MIN_ANIMATION_TIME_MILLIS = 500;
        private final long MAX_ANIMATION_TIME_MILLIS = 1000;

        /*O valor contido nessa constante indica o quanto % a view pode se mecher com base em seu tamanho
         * EX: se a view tem 100px e o valor da constante é 120 a view podera se mecher  100+120% do seu tamanho, nesse caso, 120 mesmo,
         * o valor de movimento em px será de 220px pra cima ou baixo*/
        private final int PERCENT_TO_MOVE = 120;

        /*Aqui eu digo a disatancia que a view tem que se mecher em relação a sua posição atual para
         * ser lançada*/
        private int pxMovedToLaunch;

        /*usado pra rastrear qts px o dedo do usuario percorreu na tela pra dps somar esse valor com o valor Y da view para move-la suavemente*/
        private float initialTouchY;

        /*mantem salva a posição inicial da view*/
        private float initialViewY = -1, initialActivityViewY;
        private float viewHeigth;

        /*maxMovimentDown e maxMovimentUp mantem salvos a distancia em px que a view pode ser arrastada
         * com base na porcentagem definida em PERCENT_TO_MOVE */
        private float maxMovimentDown;
        private float maxMovimentUp;

        private float maxMovimentActContainer;


        private float tension = 1;
        private final float TENSION_INCREMENTER = 5;
        private boolean viewIsUping;


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.performClick();
                    initialTouchY = event.getY();
                    if (initialViewY == -1) initVars(v);
                    break;
                case MotionEvent.ACTION_MOVE:
                    /*para a nimação que balança suavemente a vie dando a impressão de que esta flutuando no ar*/
                    mCardView.clearAnimation();
                    viewIsUping = v.getY() - initialViewY < 0;
                    moveY(event.getY(), v);

                    break;
                case MotionEvent.ACTION_UP:

                    tension = 1;
                    animate(v);
                    break;
            }


            return false;
        }

        private void moveY(float y, View v) {
            float movementPercent = getViewMovimentPercent(v);
            float yRunnedByFinger = initialTouchY - y;
            float yToRun = (v.getY() - yRunnedByFinger) - tension;

            //impede que a tensão fique tão forte a ponto de fazer a view andar pra trás
            if ((tension + TENSION_INCREMENTER) < yToRun)
                // modifica a tensão para qd a view suir e descer
                if (viewIsUping) tension -= TENSION_INCREMENTER;
                else tension += TENSION_INCREMENTER;


            if (yToRun >= maxMovimentUp && yToRun <= maxMovimentDown) {
                v.setY(yToRun);
            }

            if (actContainer != null) {
                /*descubro qts % o dialogo se moveu e uso esse valor para obter a quantidade proporcional de pixeis da view
                 * da activity compradando sua posição com o seu movimento maximo permitido*/
                float movPercent = getViewMovimentPercent(v);
                float moveFinal = initialActivityViewY + percentValue(maxMovimentActContainer, (int) movPercent);
                actContainer.setY(moveFinal);

            }


            /* limita  percentValue a 0.1, 0.2, 0.3... para setar o alpha na view*/
            if (!viewIsUping) movementPercent = movementPercent * -1;
            parent.setAlpha(movementPercent / 100);


        }

        /**
         * @param v Determina se a view foi arrastada na posição correte e pela distância suficiente para ser lançada
         *          e faz as animções necessárias
         */
        private void animate(final View v) {
            float y = v.getY();
            float movimentPercent = getViewMovimentPercent(v);

            long duration = (long) (viewIsUping ? (initialViewY - y) : (y - initialViewY));

            if (duration > MAX_ANIMATION_TIME_MILLIS) duration = MAX_ANIMATION_TIME_MILLIS;
            if (duration < MIN_ANIMATION_TIME_MILLIS) duration = MIN_ANIMATION_TIME_MILLIS;
            if (movimentPercent > 70 && viewIsUping || movimentPercent <= 70 && !viewIsUping)
                duration = ((duration / 10) * 7);

            /* alphaAnim modifica o alpha que só é alterado quando o usuario arrasta  o dedo na tela
             * isso é necessário pq nem sempre a porcentagem do movimento chega a 100 ou 0% e qd o usuario larga a view
             * o alpha já n é mais alterado e precisa voltar ao seu valor original*/
            ValueAnimator moveAnim, moveAnimActivityView, alphaAnim;


            // view será lançada
            if (viewIsUping && y <= pxMovedToLaunch) {
                moveAnim = ValueAnimator.ofFloat(y, -viewHeigth);
                alphaAnim = ValueAnimator.ofFloat(parent.getAlpha(), 1);
                moveAnim.setDuration(duration);
                alphaAnim.setDuration(duration / 3);
                finish(duration);

            } else {
                moveAnim = ValueAnimator.ofFloat(y, initialViewY);
                alphaAnim = ValueAnimator.ofFloat(parent.getAlpha(), 0);
                /*diminuo em 1/3 a velocidade da animação*/
                duration = (duration / 3) * 2;
                moveAnim.setDuration(duration);
                alphaAnim.setDuration(duration);
                mCardView.startAnimation(floatAnim);

            }


            ValueAnimator.AnimatorUpdateListener mValueAnimator = new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    parent.setAlpha((Float) animation.getAnimatedValue());
                }
            };

            ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float val = (float) valueAnimator.getAnimatedValue();
                    v.setY(val);
                }
            };


            alphaAnim.addUpdateListener(mValueAnimator);
            moveAnim.addUpdateListener(mUpdateListener);


            /*Aplico a tensão corerspondete a distância percorrida pela view.
            * Que a minha professora de física me perdoe!*/
            float tension = movimentPercent / 15;
            tension = tension < 0 ? tension * -1 : tension;
            moveAnim.setInterpolator(new OvershootInterpolator(tension));

            // A view da activity precisa voltar pro lugar tbm
            if (actContainer != null) {

                ValueAnimator.AnimatorUpdateListener mActivityViewUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float val = (float) valueAnimator.getAnimatedValue();
                        actContainer.setY(val);
                    }
                };

                moveAnimActivityView = ValueAnimator.ofFloat(actContainer.getY(), initialActivityViewY);
                moveAnimActivityView.setDuration(duration);
                moveAnimActivityView.setInterpolator(new OvershootInterpolator(tension));
                moveAnimActivityView.addUpdateListener(mActivityViewUpdateListener);
                moveAnimActivityView.start();
            }


            moveAnim.start();
            alphaAnim.start();

        }

        private void initVars(View v) {

            initialViewY = v.getY();
            viewHeigth = v.getMeasuredHeight();
            maxMovimentDown = initialViewY + percentValue(v.getMeasuredHeight(), PERCENT_TO_MOVE);
            maxMovimentUp = initialViewY - percentValue(v.getMeasuredHeight(), PERCENT_TO_MOVE);
            pxMovedToLaunch = (int) percentValue((initialViewY - v.getMeasuredHeight()), 60);

            if (actContainer != null) {
                maxMovimentActContainer = actContainer.getY() - percentValue(v.getMeasuredHeight(), 30);
                initialActivityViewY = actContainer.getY();
            }


        }

        /**
         * @param target t
         * @param value  v
         * @return quantos % value é de target.
         */
        private float percent(float value, float target) {
            return new BigDecimal(value).divide(new BigDecimal(target), 2, RoundingMode.HALF_DOWN).multiply(new BigDecimal(100)).floatValue();
        }

        /**
         * @param v v
         * @return a porcentegem de movimento que a view fez do seu ponto inicial até o ponto maximo de movimento permitido
         */
        private float getViewMovimentPercent(View v) {
            /*ao remover initialViewY de v.gety() descubro o quanto a view andou. Valores >0 indicam que a view subiu
             * ao remover initialViewY de maxMovimentUp descubro quantos px a view tem que andar da sua posição inicial até a posição
             * maxima de animação, assim descubro em quantos % a view andou apartir de seu ponto de origem até o ponto de movimento final
             *
             * A VARIAVEL maxMovimentUp  foi usada como referencia pq reotnou uma porcentagem melhor
             * do que a maxMovimentDown mesmo q na lógica elas tenha um valor bem parecido*/
            return percent(initialViewY - v.getY(), initialViewY - maxMovimentUp);

        }

        /**
         * @param target  t
         * @param percent p
         * @return o qual o valor correspondente a porcentagem recebida
         * <p>
         * EX: target =150 & percentValue = 50 return será 75
         */
        private float percentValue(float target, int percent) {
            return new BigDecimal(percent).multiply(new BigDecimal(target)).divide(new BigDecimal(100), 2, RoundingMode.HALF_DOWN).floatValue();
        }
    }


}
