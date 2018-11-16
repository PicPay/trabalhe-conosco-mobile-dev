package project.picpay.test.home.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import project.picpay.test.R;
import project.picpay.test.creditcard.view.ListCardsActivity;
import project.picpay.test.databinding.ActivityHomeBinding;
import project.picpay.test.home.model.UserModel;
import project.picpay.test.home.viewmodel.UserViewModel;
import project.picpay.test.transaction.TransactionActivity;

public class HomeActivity extends AppCompatActivity implements ListUserAdapter.OnActionListerner {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private ActivityHomeBinding binding;
    private ListUserAdapter pullAdapter;
    private OnClickListerners listerners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        configRecycleView();
        configListerners();
        configViewModel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSelectUser(UserModel user) {
        Intent i = new Intent(HomeActivity.this, TransactionActivity.class);
        i.putExtra("user_model", user);
        startActivity(i);
    }

    private void configViewModel() {
        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        viewModel.dataLoadStatus().observe(this, loadStatus -> {
            assert loadStatus != null;
            switch (loadStatus) {
                case LOADING:
                    //Toast.makeText(this, getString(R.string.s_message_loading_users), Toast.LENGTH_SHORT).show();
                    break;
                case LOADED:
                    Toast.makeText(this, getString(R.string.s_message_load_success_users), Toast.LENGTH_SHORT).show();
                    break;
                case FAILED:
                    showErrorList();
                    break;
            }
        });

        viewModel.getAvailableUsers().observe(HomeActivity.this, userModel -> {
            if (userModel == null) {
                return;
            }
            if (userModel.size() > 0) {
                pullAdapter.addItems(userModel);
                showRvImport();
            } else {
                showEmptyList();
            }
        });
    }

    private void configRecycleView() {
        pullAdapter = new ListUserAdapter(new ArrayList<>(), this);
        binding.rvMain.rvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMain.rvList.setAdapter(pullAdapter);
    }

    private void configListerners() {
        listerners = new OnClickListerners(this);
        binding.setListerners(listerners);
    }

    private void showRvImport() {
        binding.loadMain.pbLoad.setVisibility(View.GONE);
        binding.tvMain.llEmptyValues.setVisibility(View.GONE);
        binding.rvMain.rvList.setVisibility(View.VISIBLE);
    }

    private void showEmptyList() {
        binding.loadMain.pbLoad.setVisibility(View.GONE);
        binding.rvMain.rvList.setVisibility(View.GONE);
        binding.tvMain.tvNoValues.setText(getString(R.string.s_message_without_users));
        binding.tvMain.llEmptyValues.setVisibility(View.VISIBLE);
    }

    private void showErrorList() {
        binding.loadMain.pbLoad.setVisibility(View.GONE);
        binding.rvMain.rvList.setVisibility(View.GONE);
        binding.tvMain.tvNoValues.setText(getString(R.string.s_message_error_users));
        binding.tvMain.llEmptyValues.setVisibility(View.VISIBLE);
    }

    public class OnClickListerners {

        Context context;

        OnClickListerners(Context context) {
            this.context = context;
        }

        public void newCreditCard(View view) {

        }

        public void listCreditCards(View view) {
            startActivity(new Intent(HomeActivity.this, ListCardsActivity.class));
        }

    }

    /*private void showDialogTransaction(UserModel user) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        DialogTransactionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_transaction, null, false);
        binding.setListerners(listerners);
        binding.setPost(user);
        dialog.setContentView(binding.getRoot());
        dialog.show();
    }*/

}