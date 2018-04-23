package gilianmarques.dev.picpay_test.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.crud.Database;
import gilianmarques.dev.picpay_test.models.CreditCard;

public class DialogSelectCreditCard extends AlertDialog implements View.OnClickListener {
    private Activity mActivity;
    private ArrayList<CreditCard> mCards;
    private FloatingActionButton fab;
    private CreditCard cardSelected;
    private LinearLayout container;
    private int selectionColor;
    private boolean fabDrawableIsACheck;
    private AnimatedVectorDrawable mAddDrawable, mCheckDrawable;
    private DialogCallback dialogCallback;
    private View rootView;

    public DialogSelectCreditCard(@NonNull Activity mActivity, @NonNull ArrayList<CreditCard> mCards, @NonNull DialogCallback dialogCallback) {
        super(mActivity, R.style.AppTheme_NoActionBar_Translucent);
        this.mActivity = mActivity;
        this.mCards = mCards;
        this.dialogCallback = dialogCallback;
        init();
        populateContainer();
    }

    private void init() {
        selectionColor = ContextCompat.getColor(mActivity, R.color.almostPrimary);
        mAddDrawable = (AnimatedVectorDrawable) mActivity.getDrawable(R.drawable.ic_add_animatable);
        mCheckDrawable = (AnimatedVectorDrawable) mActivity.getDrawable(R.drawable.ic_check_animatable);

        rootView = mActivity.getLayoutInflater().inflate(R.layout.dialog_select_credit_card, null);
        container = rootView.findViewById(R.id.container);
        fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        setView(rootView);
        setOnShowListener(mShowListener);
    }


    /**
     * Cria as views de cartões e as adiciona no layout. Uma abordagem muito mais simples do que criar um {@link android.widget.ListView}
     * ou um {@link android.support.v7.widget.RecyclerView} principalmente considerando que as chances de inflar mais do que 5/6 views de
     * cartão é minima
     */
    private void populateContainer() {
        container.removeAllViews();
        int index = 0;
        for (final CreditCard mCard : mCards) {
            final View cardView = mActivity.getLayoutInflater().inflate(R.layout.view_credit_card_selection, null);
            ((TextView) cardView.findViewById(R.id.tv_number)).setText(mCard.getSpacedNumber());
            ((TextView) cardView.findViewById(R.id.tv_brand)).setText(mCard.getBrand());
            ((TextView) cardView.findViewById(R.id.tv_owner_name)).setText(mCard.getOwnerName());
            ((TextView) cardView.findViewById(R.id.tv_expity_date)).setText(mCard.getExpireDate());

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectedView(mCard, cardView);
                }
            };

            if (index == mCards.size() - 1)
                cardView.findViewById(R.id.divider).setVisibility(View.INVISIBLE);
            cardView.setOnClickListener(listener);
            container.addView(cardView);

            //

            TranslateAnimation transAnim = new TranslateAnimation(0f, 0f, 300f, 0f);
            AlphaAnimation fadeAnim = new AlphaAnimation(0, 1);

            fadeAnim.setDuration(400);
            transAnim.setDuration(400);
            //
            transAnim.setStartOffset((index++) * 75);
            fadeAnim.setStartOffset(transAnim.getStartOffset());


            AnimationSet set = new AnimationSet(true);
            set.addAnimation(transAnim);
            set.addAnimation(fadeAnim);

            cardView.startAnimation(set);
        }


    }

    /**
     * @param mCard    o cartão selecionado
     * @param cardView a view correspondente ao cartão
     *                 <p>
     *                 Atualiza a interface de acordo com a seleção do usuário
     */
    private void setSelectedView(CreditCard mCard, View cardView) {
        if (!Objects.equals(cardSelected, mCard)) {
            /*se esse cartão não é o cartão atualmente selecionado desmarco a view selecionada rodando um 'for' */

            for (int i = 0; i < container.getChildCount(); i++) {
                View mView = container.getChildAt(i);
                mView.findViewById(R.id.parent).setBackgroundColor(0);
                mView.findViewById(R.id.iv_check).setVisibility(View.INVISIBLE);
            }

            /* marco essa view e seto o cartao como selecionado*/
            cardView.findViewById(R.id.parent).setBackgroundColor(selectionColor);
            cardView.findViewById(R.id.iv_check).setVisibility(View.VISIBLE);
            cardSelected = mCard;
            animateFab();

        } else {

            /*se esse for o cartão selecionado então o desmarco permitindo o usuario adicionar um novo cartão*/
            cardView.findViewById(R.id.parent).setBackgroundColor(0);
            cardView.findViewById(R.id.iv_check).setVisibility(View.INVISIBLE);
            cardSelected = null;
            animateFab();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fab) {
            if (cardSelected == null) {
                dialogCallback.addNewCard();
            } else {
                dialogCallback.cardSelected(cardSelected);
                dismiss();
            }
        }
    }


    private void animateFab() {
        if (cardSelected == null) {
            fab.setImageDrawable(mCheckDrawable);
            mCheckDrawable.start();
            fabDrawableIsACheck = false;
        } else if (!fabDrawableIsACheck) {
            fab.setImageDrawable(mAddDrawable);
            mAddDrawable.start();
            fabDrawableIsACheck = true;
        }
    }


    public void update() {
        mCards = Database.getInstance().getCards();
        populateContainer();
        setSelectedView(mCards.get(mCards.size() - 1), container.getChildAt(container.getChildCount() - 1));
    }


    private OnShowListener mShowListener = new OnShowListener() {
        @Override
        public void onShow(DialogInterface dialog) {

            TranslateAnimation transAnim = new TranslateAnimation(0f, 0f, -100f, 0f);
            AlphaAnimation mAnimation = new AlphaAnimation(0f, 1f);

            mAnimation.setDuration(200);
            transAnim.setDuration(400);

            rootView.startAnimation(mAnimation);
            rootView.findViewById(R.id.tv_title).startAnimation(transAnim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    fab.show();
                }
            }, 500);
        }
    };


    @Override
    public void dismiss() {
        fab.hide();
        TranslateAnimation transAnim = new TranslateAnimation(0f, 0f, 0f, -100f);
        AlphaAnimation mAnimation = new AlphaAnimation(1f, 0f);

        mAnimation.setDuration(200);
        transAnim.setDuration(200);
        transAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                DialogSelectCreditCard.super.dismiss();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rootView.startAnimation(mAnimation);
        rootView.findViewById(R.id.tv_title).startAnimation(transAnim);

    }

    public interface DialogCallback {
        void cardSelected(CreditCard mCreditCard);

        /**
         * Este metodo é chamado quando há necessidade de adicionar um novo cartão. Após a adição
         * do cartão deve-se chamar update no dialogo para exibir o cartão na lista
         */
        void addNewCard();
    }
}
