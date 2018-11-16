package project.picpay.test.creditcard.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import project.picpay.test.R;
import project.picpay.test.creditcard.model.CreditCardModel;
import project.picpay.test.creditcard.viewmodel.CreditCardViewModel;
import project.picpay.test.databinding.ActivityListCardsBinding;
import project.picpay.test.transaction.TransactionActivity;

public class ListCardsActivity extends AppCompatActivity implements ListCardAdapter.OnActionListerner {

    private static final String TAG = ListCardsActivity.class.getSimpleName();
    private ActivityListCardsBinding binding;
    private CreditCardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_cards);

        configToolbar();
        configViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_credit_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_new_card:
                startActivity(new Intent(ListCardsActivity.this, NewCreditCardActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        configViewModel();
    }

    private void configToolbar() {
        setSupportActionBar(binding.toolbarMain.toolbar);
        setTitle(R.string.s_title_list_cards);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private void configViewModel() {
        viewModel = ViewModelProviders.of(this).get(CreditCardViewModel.class);

        viewModel.getListCards().observe(ListCardsActivity.this, creditCardModels -> {
            if (creditCardModels != null && creditCardModels.size() > 0) {
                ListCardAdapter obsAdapter = new ListCardAdapter(creditCardModels, this);
                binding.rvMain.rvList.setAdapter(obsAdapter);
                showRvImport();
            } else {
                showEmptyList();
            }
        });
    }

    private void showRvImport() {
        binding.loadMain.pbLoad.setVisibility(View.GONE);
        binding.tvMain.llEmptyValues.setVisibility(View.GONE);
        binding.rvMain.rvList.setVisibility(View.VISIBLE);
    }

    private void showEmptyList() {
        binding.loadMain.pbLoad.setVisibility(View.GONE);
        binding.rvMain.rvList.setVisibility(View.GONE);
        binding.tvMain.tvNoValues.setText(getString(R.string.s_message_without_cards));
        binding.tvMain.llEmptyValues.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetCreditCard(CreditCardModel card) {
        Intent i = new Intent(ListCardsActivity.this, TransactionActivity.class);
        i.putExtra("card_model", card);
        setResult(1, i);
        finish();
    }

}