package br.com.dalcim.picpay.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.com.dalcim.picpay.BaseActivity;
import br.com.dalcim.picpay.R;
import br.com.dalcim.picpay.adapter.UserAdapter;
import br.com.dalcim.picpay.data.User;
import br.com.dalcim.picpay.data.remote.RepositoryRemoteImpl;
import br.com.dalcim.picpay.payment.PaymentActivity;
import br.com.dalcim.picpay.utils.DialogUtils;
import br.com.dalcim.picpay.utils.ModelUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Wiliam
 * @since 27/08/2017
 */
public class UsersActivity extends BaseActivity implements UsersContract.View {

    @BindView(R.id.users_rec_users)
    RecyclerView recUsers;

    @BindView(R.id.users_txt_message)
    TextView txtMessage;

    private UsersContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);

        presenter = new UsersPresenter(this, new RepositoryRemoteImpl());
        presenter.getUsers();
    }

    @Override
    public void loadUsers(List<User> users) {
        txtMessage.setVisibility(View.GONE);

        recUsers.setVisibility(View.VISIBLE);
        recUsers.setLayoutManager(new LinearLayoutManager(this));
        recUsers.setAdapter(new UserAdapter(recUsers, users, new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                presenter.userSelected(user);
            }
        }));
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if(active){
            this.showLoadDialog();
        }else{
            this.hideLoadDialog();
        }
    }

    @Override
    public void showNoUsers() {
        recUsers.setVisibility(View.GONE);

        txtMessage.setVisibility(View.VISIBLE);
        txtMessage.setText(R.string.not_found);
    }

    @Override
    public void showFailureLoadUsers(String failure) {
        recUsers.setVisibility(View.GONE);

        DialogUtils.showConfirmDialog(this, "Erro", failure);
        txtMessage.setVisibility(View.VISIBLE);
        txtMessage.setText(R.string.not_found);
    }

    @Override
    public void showPaymentActivity(User user) {
        Intent intent = new Intent(this, PaymentActivity.class);
        ModelUtils.populeIntent(intent, user);
        startActivity(intent);
    }
}
