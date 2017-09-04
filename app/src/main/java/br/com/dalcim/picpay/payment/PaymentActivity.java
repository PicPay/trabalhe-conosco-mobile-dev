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
import android.widget.Toast;

import com.github.florent37.tutoshowcase.TutoShowcase;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.dalcim.picpay.BaseActivity;
import br.com.dalcim.picpay.R;
import br.com.dalcim.picpay.adapter.CreditCardAdapter;
import br.com.dalcim.picpay.adapter.MarginDecoration;
import br.com.dalcim.picpay.creditcard.CreditCardActivity;
import br.com.dalcim.picpay.data.CreditCard;
import br.com.dalcim.picpay.data.Transaction;
import br.com.dalcim.picpay.data.User;
import br.com.dalcim.picpay.data.local.RepositoryLocaImpl;
import br.com.dalcim.picpay.data.remote.RepositoryRemoteImpl;
import br.com.dalcim.picpay.utils.DecimalTextWatcher;
import br.com.dalcim.picpay.utils.ModelUtils;
import br.com.dalcim.picpay.utils.TrasformationUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private PaymentContract.Presenter presenter;
    private CreditCard selectedCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);

        presenter = new PaymentPresenter(this, new RepositoryRemoteImpl(), new RepositoryLocaImpl(this));

        user = ModelUtils.intentToUser(getIntent());

        Picasso.with(this).load(user.getImg()).transform(TrasformationUtils.circleTransform).into(imgImg);
        txtName.setText(user.getName());
        txtUsername.setText(user.getUsername());
        edtValue.addTextChangedListener(new DecimalTextWatcher(edtValue));
        edtValue.setText("0");

        recCards.setLayoutManager(new LinearLayoutManager(this));
        recCards.addItemDecoration(new MarginDecoration(this));

        presenter.loadCreditCards();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CreditCardActivity.REQUEST_CODE && resultCode == RESULT_OK){
            presenter.loadCreditCards();
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.pay_btn_send)
    public void sendOnClick(View view){
        double value = Double.valueOf(edtValue.getText().toString().replace("R$", "").replace(",","."));

        presenter.send(selectedCard, value, user.getId());
    }

    @OnClick(R.id.pay_btn_add_card)
    public void addCardOnClick(View view){
        startActivityForResult(new Intent(this, CreditCardActivity.class), CreditCardActivity.REQUEST_CODE);
    }

    @Override
    public void loadCards(List<CreditCard> creditCards) {
        selectedCard = creditCards.get(0);
        recCards.setAdapter(new CreditCardAdapter(recCards, creditCards, selectedCard, new CreditCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CreditCard creditCard) {
                selectedCard = creditCard;
            }
        }));
    }

    @Override
    public void showNoCards() {
        TutoShowcase.from(this)
                .setContentView(R.layout.add_card_showcase)
                .on(R.id.pay_btn_add_card)
                .addCircle()
                .show();
    }

    @Override
    public void paymentOnSucess(Transaction transaction) {
        Toast.makeText(this, "Pagamento Efetuado!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showError(String error) {
        showConfirmDialog(null, error);
    }
}
