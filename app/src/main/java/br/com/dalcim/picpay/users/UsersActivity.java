package br.com.dalcim.picpay.users;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.dalcim.picpay.BaseActivity;
import br.com.dalcim.picpay.R;
import br.com.dalcim.picpay.data.User;
import br.com.dalcim.picpay.data.remote.RepositoryRemoteImpl;
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
    }

    @Override
    public void loadUsers(List<User> users) {
    }

    @Override
    public void setLoadingIndicator(boolean active) {
    }

    @Override
    public void showNoUsers() {
    }

    @Override
    public void showFailureLoadUsers(String failure) {
    }

    @Override
    public void showPaymentActivity(User user) {
    }
}
