package gilianmarques.dev.picpay_test.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.crud.Database;
import gilianmarques.dev.picpay_test.models.CreditCard;

public class DialogSelectCreditCard extends AlertDialog implements View.OnClickListener {
    private final Activity mActivity;
    private FloatingActionButton fab;
    private CreditCard cardSelected;
    private boolean fabDrawableIsACheck;
    private AnimatedVectorDrawable mAddDrawable, mCheckDrawable;
    private final DialogCallback dialogCallback;
    private View rootView;
    private final CardsAdapter mAdapter;

    public DialogSelectCreditCard(@NonNull Activity mActivity, @NonNull DialogCallback dialogCallback) {
        super(mActivity, R.style.AppTheme_NoActionBar_Translucent);
        this.mActivity = mActivity;
        mAdapter = new CardsAdapter();
        this.dialogCallback = dialogCallback;
        init();
    }

    private void init() {
        rootView = mActivity.getLayoutInflater().inflate(R.layout.dialog_select_credit_card, null);

        mAddDrawable = (AnimatedVectorDrawable) mActivity.getDrawable(R.drawable.ic_add_animatable);
        mCheckDrawable = (AnimatedVectorDrawable) mActivity.getDrawable(R.drawable.ic_check_animatable);

        ListView mListView = rootView.findViewById(R.id.listview);
        fab = rootView.findViewById(R.id.fab);

        fab.setOnClickListener(this);
        mListView.setAdapter(mAdapter);

        setView(rootView);
        setOnShowListener(mShowListener);
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


    public void update(CreditCard creditCard) {
        /*marca o cartão recem adicionado (o ultimo da lista) como selecionado*/
        cardSelected = creditCard;
        mAdapter.update();
    }


    private final OnShowListener mShowListener = new OnShowListener() {
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


    class CardsAdapter extends BaseAdapter {
        private int indexRunned;
        private ArrayList<CreditCard> mCards;
        private final LayoutInflater mInflater;
        private View lastViewSelected;
        private final int selectionColor;


        CardsAdapter() {
            this.mInflater = mActivity.getLayoutInflater();
            selectionColor = ContextCompat.getColor(mActivity, R.color.primary_t);
            update();
        }


        @Override
        public int getCount() {
            return mCards.size();
        }

        @Override
        public CreditCard getItem(int position) {
            return mCards.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.view_credit_card_selection, null);
            }

            final CreditCard mCard = mCards.get(position);
            ((TextView) convertView.findViewById(R.id.tv_number)).setText(mCard.getSpacedNumber());
            ((TextView) convertView.findViewById(R.id.tv_brand)).setText(mCard.getBrandName());
            ((TextView) convertView.findViewById(R.id.tv_owner_name)).setText(mCard.getOwnerName());
            ((TextView) convertView.findViewById(R.id.tv_expity_date)).setText(mCard.getExpireDate());

            if (position == getCount() - 1) {
                convertView.findViewById(R.id.divider).setVisibility(View.INVISIBLE);
            }


            View.OnClickListener mListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    markViewAsSelected(v, mCard);
                }
            };


            /* Se houver apenas um cartão o marco como selecionado */
            if (getCount() == 1) markViewAsSelected(convertView, mCard);

            /*se o ccartão encontrado no array for o mesmo marcado como selecionado, marco a view*/
            if (cardSelected!=null&&cardSelected.getId()==mCard.getId()) {

                /*mudo o valor de cardSelected pq markViewAsSelected vai verificar se o cartão que passei e o cartão
                 * selecionado são os mesmos e caso sejam vai desselecionar a view e marcar cardSelected como null*/
                 cardSelected = null;
                markViewAsSelected(convertView, mCard);
            }

            convertView.setOnClickListener(mListener);

            animateView(convertView, position);

            return convertView;
        }

        private void animateView(View convertView, int position) {
            if (position > indexRunned) {
                indexRunned = position;
                TranslateAnimation transAnim = new TranslateAnimation(0f, 0f, 300f, 0f);
                AlphaAnimation fadeAnim = new AlphaAnimation(0, 1);

                fadeAnim.setDuration(400);
                transAnim.setDuration(400);

                //noinspection UnusedAssignment
                transAnim.setStartOffset((position++) * 75);
                fadeAnim.setStartOffset(transAnim.getStartOffset());

                AnimationSet set = new AnimationSet(true);
                set.addAnimation(transAnim);
                set.addAnimation(fadeAnim);

                convertView.startAnimation(set);
            }
        }


        /**
         * @param itemView a view correspondente ao cartão
         *                 <p>
         *                 Atualiza a interface de acordo com a seleção do usuário
         * @param mCard    o cartão selecionado
         */
        private void markViewAsSelected(View itemView, CreditCard mCard) {
            if (!Objects.equals(cardSelected, mCard)) {
                /*se esse cartão não é o cartão atualmente selecionado desmarco a view selecionada  */

                if (lastViewSelected != null) {
                    lastViewSelected.findViewById(R.id.fade_parent).setBackgroundColor(0);
                    lastViewSelected.findViewById(R.id.iv_check).setVisibility(View.INVISIBLE);
                }

                /* marco essa view e seto o cartao como selecionado*/
                itemView.findViewById(R.id.fade_parent).setBackgroundColor(selectionColor);
                itemView.findViewById(R.id.iv_check).setVisibility(View.VISIBLE);

                /*Se n tinha cartão selecionado eu mudo o drawable do fab
                 * de + para ✓ */
                if (cardSelected == null) {
                    cardSelected = mCard;
                    animateFab();
                } else cardSelected = mCard;

                lastViewSelected = itemView;

            } else {

                /*se esse for o cartão selecionado então o desmarco permitindo o usuario adicionar um novo cartão*/
                itemView.findViewById(R.id.fade_parent).setBackgroundColor(0);
                itemView.findViewById(R.id.iv_check).setVisibility(View.INVISIBLE);
                cardSelected = null;
                lastViewSelected = null;
                /*  mudo o drawable do fab de ✓  para +  */
                animateFab();
            }
        }


        void update() {
            mCards = Database.getInstance().getCards();
            indexRunned = -1;
            notifyDataSetChanged();
        }


    }
}
