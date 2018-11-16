package project.picpay.test.transaction;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import project.picpay.test.R;
import project.picpay.test.creditcard.model.CreditCardModel;
import project.picpay.test.creditcard.view.ListCardsActivity;
import project.picpay.test.databinding.ActivityTransactionBinding;
import project.picpay.test.home.model.UserModel;
import project.picpay.test.home.model.transaction_received.TransactionPost;
import project.picpay.test.home.viewmodel.TransactionViewModel;

public class TransactionActivity extends AppCompatActivity {

    private static final String TAG = TransactionActivity.class.getSimpleName();
    private ActivityTransactionBinding binding;
    private TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction);

        Intent i = getIntent();
        UserModel user = (UserModel) i.getSerializableExtra("user_model");
        configToolbar();
        configUser(user);
        configViewModel();
        configListerners();
        //staticPost(user);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            CreditCardModel model = (CreditCardModel) data.getSerializableExtra("card_model");
            binding.setCard(model);
            binding.executePendingBindings();
        }
    }

    private void configToolbar() {
        setSupportActionBar(binding.toolbarMain.toolbar);
        setTitle(R.string.s_title_new_transaction);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private void configUser(UserModel u) {
        binding.setPost(u);
    }

    private void configViewModel() {
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);

        transactionViewModel.dataLoadStatus().observe(this, loadStatus -> {
            assert loadStatus != null;
            switch (loadStatus) {
                case LOADING:
                    Toast.makeText(this, "Fazendo transação", Toast.LENGTH_SHORT).show();
                    break;
                case LOADED:
                    //Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show();
                    break;
                case FAILED:
                    //Toast.makeText(this, "Falha", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

    }

    private void configListerners() {
        OnClickListerners listerners = new OnClickListerners(this);
        binding.setListerners(listerners);
    }


    private void sendRequest(TransactionPost post) {
        transactionViewModel.getReturnTransaction(post).observe(this, responseTransaction -> {
            if (responseTransaction == null) {
                return;
            }

            if(responseTransaction.getTransaction().getSuccess()){
                Toast.makeText(this, "Transação feita com sucesso.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro na transação, troque de cartão e tente novamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class OnClickListerners {

        Context context;

        OnClickListerners(Context context) {
            this.context = context;
        }

        public void selectPayment(View view) {
            startActivityForResult(new Intent(TransactionActivity.this, ListCardsActivity.class), 1);
        }

        public void newTransaction(View view) {
            if (validateFields()) {
                TransactionPost post = new TransactionPost();
                post.setCardNumber(binding.getCard().getCardNumber().replace(" ", "").trim());
                post.setExpiryDate(binding.getCard().getExpiredDate());
                post.setCvv(Integer.valueOf(binding.getCard().getCvvCode()));
                post.setDestinationUserId(binding.getPost().getId());
                post.setValue(Double.parseDouble(binding.etValueTransaction.getText().toString()));
                sendRequest(post);
            } else {
                Toast.makeText(context, getString(R.string.s_message_error_fields), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean validateFields() {
        return binding.getCard() != null
                && binding.getPost() != null
                && binding.etValueTransaction.getText().toString().length() > 0;
    }

}