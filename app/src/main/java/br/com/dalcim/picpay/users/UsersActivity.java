package br.com.dalcim.picpay.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.dalcim.picpay.BaseActivity;
import br.com.dalcim.picpay.R;
import br.com.dalcim.picpay.adapter.UserAdapter;
import br.com.dalcim.picpay.data.User;
import br.com.dalcim.picpay.data.remote.RepositoryRemoteImpl;
import br.com.dalcim.picpay.payment.PaymentActivity;
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

    private UsersContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);

        recUsers.setLayoutManager(new LinearLayoutManager(this));
        presenter = new UsersPresenter(this, new RepositoryRemoteImpl());
        presenter.getUsers();
    }

    @Override
    public void loadUsers(List<User> users) {
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
        //TODO
    }

    @Override
    public void showFailureLoadUsers(String failure) {
        //TODO
    }

    @Override
    public void showPaymentActivity(User user) {
        Intent intent = new Intent(this, PaymentActivity.class);
        ModelUtils.populeIntent(intent, user);
        startActivity(intent);
    }
}
