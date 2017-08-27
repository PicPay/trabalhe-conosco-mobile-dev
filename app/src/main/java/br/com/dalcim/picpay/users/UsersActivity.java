package br.com.dalcim.picpay.users;

import android.os.Bundle;

import br.com.dalcim.picpay.BaseActivity;
import br.com.dalcim.picpay.R;
import br.com.dalcim.picpay.data.remote.RepositoryRemoteImpl;
import butterknife.ButterKnife;

/**
 * @author Wiliam
 * @since 27/08/2017
 */
public class UsersActivity extends BaseActivity implements UsersContract.View {

    private UsersContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);

        presenter = new UsersPresenter(this, new RepositoryRemoteImpl());
    }
}
