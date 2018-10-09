package br.com.picpay.picpay.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import br.com.picpay.picpay.R;
import br.com.picpay.picpay.adapter.RecyclerViewAdapter;
import br.com.picpay.picpay.contract.CardsContract;
import br.com.picpay.picpay.listerner.TransactionListerner;
import br.com.picpay.picpay.model.Card;
import br.com.picpay.picpay.model.ChangeCard;
import br.com.picpay.picpay.model.DeleteCard;
import br.com.picpay.picpay.model.User;
import br.com.picpay.picpay.presenter.CardsPresenter;
import br.com.picpay.picpay.view.fragment.dialog.TransactionFragment;
import br.com.picpay.picpay.viewholder.CardViewHolder;
import io.realm.Realm;

@EActivity(R.layout.activity_cards)
public class CardsActivity extends BaseSubscribeActivity implements CardsContract.CardsView {

    private final int REQUEST_REGISTER_CARD = 10;
    private final int REQUEST_CHANGE_CARD = 11;

    @Bean
    CardsPresenter presenter;

    @Extra
    User user;

    @ViewById
    RecyclerView rvCards;

    @ViewById
    TextView warningRegisterCard;

    @InstanceState
    ArrayList<Card> listCards = new ArrayList<>();

    private RecyclerViewAdapter<Card> adapter;

    @Override
    public void init() {
        super.init();
        presenter.setView(this);
        adapter = new RecyclerViewAdapter<>(CardViewHolder.class, listCards);
        updateList(false);
        rvCards.setAdapter(adapter);
        Fragment fragmentTransaction = getSupportFragmentManager().findFragmentByTag(TransactionFragment.class.getName());
        if (fragmentTransaction instanceof TransactionFragment) {
            ((TransactionFragment) fragmentTransaction).setTransactionListerner(transactionListerner);
        }
    }

    @Click(R.id.btn_register_card)
    void onBtnRegisterCard() {
        AddCardActivity_.intent(this).startForResult(REQUEST_REGISTER_CARD);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeCard(@NonNull ChangeCard changeCard) {
        AddCardActivity_.intent(this).card(changeCard.getCard()).startForResult(REQUEST_CHANGE_CARD);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteCard(@NonNull DeleteCard deleteCard) {
        showDialogDeleteCard(deleteCard.getCard());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCardSelected(@NonNull Card card) {
        showDialogTransaction(card);
    }

    @Override
    public void onCardDeleted(@NonNull Card card) {
        forceUpdateList();
    }

    @OnActivityResult(REQUEST_REGISTER_CARD)
    void onResultRegisterCard(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Card card = data.getParcelableExtra(Card.class.getName());
            forceUpdateList();
            showDialogTransaction(card);
        }
    }

    @OnActivityResult(REQUEST_CHANGE_CARD)
    void onResultChangeCard(int resultCode) {
        if (resultCode == RESULT_OK) {
            forceUpdateList();
        }
    }

    private void forceUpdateList() {
        updateList(true);
        adapter.notifyDataSetChanged();
    }

    private void updateList(boolean force) {
        if (listCards.isEmpty() || force) {
            listCards.clear();
            listCards.addAll(Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(Card.class).findAll()));
        }
        warningRegisterCard.setVisibility(listCards.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void showDialogDeleteCard(@NonNull Card card) {
        presenter.deleteCard(card);
    }

    private void showDialogTransaction(@NonNull Card card) {
        TransactionFragment transactionFragment = new TransactionFragment(user, card);
        transactionFragment.setTransactionListerner(transactionListerner);
        transactionFragment.show(getSupportFragmentManager(), TransactionFragment.class.getName());
    }

    private TransactionListerner transactionListerner = new TransactionListerner() {
        @Override
        public void execute(@NonNull User user, @NonNull Card card, float value) {
            showLoading(getString(R.string.finishing_transaction));
            presenter.executeTransaction(user, card, value);
        }
    };

    @Override
    public void onSucessTransaction() {
        hiddenLoading();
        showDialogMessage(getString(R.string.transaction_sucess), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CardsActivity.this.finish();
            }
        });
    }

    @Override
    public void onErrorTransaction(String error) {
        hiddenLoading();
        showDialogMessage(error);
    }
}
