package br.com.dalcim.picpay.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.dalcim.picpay.BaseActivity;
import br.com.dalcim.picpay.R;
import br.com.dalcim.picpay.adapter.CreditCardAdapter;
import br.com.dalcim.picpay.adapter.MarginDecoration;
import br.com.dalcim.picpay.creditcard.CreditCardActivity;
import br.com.dalcim.picpay.data.CreditCard;
import br.com.dalcim.picpay.data.Payment;
import br.com.dalcim.picpay.data.Transaction;
import br.com.dalcim.picpay.data.User;
import br.com.dalcim.picpay.data.local.RepositoryLocaImpl;
import br.com.dalcim.picpay.data.remote.RepositoryRemote;
import br.com.dalcim.picpay.data.remote.RepositoryRemoteImpl;
import br.com.dalcim.picpay.utils.DecimalTextWatcher;
import br.com.dalcim.picpay.utils.ModelUtils;
import br.com.dalcim.picpay.utils.TrasformationUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Wiliam
 * @since 27/08/2017
 */
public class PaymentActivity extends BaseActivity implements PaymentContract.View{

    @BindView(R.id.pay_img_img)
    ImageView imgImg;

    @BindView(R.id.pay_txt_name)
    TextView txtName;

    @BindView(R.id.pay_txt_username)
    TextView txtUsername;

    @BindView(R.id.pay_edt_value)
    EditText edtValue;

    @BindView(R.id.pay_rec_cards)
    RecyclerView recCards;

    private User user;
    private RepositoryRemote repositoryRemote;
    private PaymentContract.Presenter presenter;
    private CreditCard selectedCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);

        presenter = new PaymentPresenter(this, new RepositoryRemoteImpl(), new RepositoryLocaImpl(this));

        user = ModelUtils.intentToUser(getIntent());
        repositoryRemote = new RepositoryRemoteImpl();

        Picasso.with(this).load(user.getImg()).transform(TrasformationUtils.circleTransform).into(imgImg);
        txtName.setText(user.getName());
        txtUsername.setText(user.getUsername());
        edtValue.addTextChangedListener(new DecimalTextWatcher(edtValue));
        edtValue.setText("0");

        recCards.setLayoutManager(new LinearLayoutManager(this));
        recCards.addItemDecoration(new MarginDecoration(this));

        presenter.loadCreditCards();
    }

    @OnClick(R.id.pay_btn_send)
    public void sendOnClick(View view){
        presenter.send(new Payment());
    }

    @OnClick(R.id.pay_btn_add_card)
    public void addCardOnClick(View view){
        startActivity(new Intent(this, CreditCardActivity.class));
    }

    @Override
    public void loadCards(List<CreditCard> creditCards) {
        recCards.setAdapter(new CreditCardAdapter(recCards, creditCards, null, new CreditCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CreditCard creditCard) {
                selectedCard = creditCard;
            }
        }));
    }

    @Override
    public void paymentOnSucess(Transaction transaction) {

    }

    @Override
    public void paymentNotApproved(Transaction transaction) {

    }

    @Override
    public void paymentOnFailure(String failure) {

    }
}
