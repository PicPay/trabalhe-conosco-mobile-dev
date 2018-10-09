package br.com.picpay.picpay.view.activity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import br.com.picpay.picpay.R;
import br.com.picpay.picpay.adapter.RecyclerViewAdapter;
import br.com.picpay.picpay.contract.UsersContract;
import br.com.picpay.picpay.model.User;
import br.com.picpay.picpay.presenter.UsersPresenter;
import br.com.picpay.picpay.viewholder.UserViewHolder;

@EActivity(R.layout.activity_users)
public class UsersActivity extends BaseSubscribeActivity implements UsersContract.UsersView {

    @Bean
    UsersPresenter presenter;

    @ViewById
    RecyclerView rvUsers;

    @ViewById
    SwipeRefreshLayout swipeRefresh;

    @InstanceState
    ArrayList<User> listUsers = new ArrayList<>();

    private RecyclerViewAdapter<User> adapter;

    @Override
    public void init() {
        super.init();
        presenter.setView(this);
        adapter = new RecyclerViewAdapter<>(UserViewHolder.class, listUsers);
        rvUsers.setAdapter(adapter);
        if (listUsers.isEmpty()) {
            getUsers();
        }
        swipeRefresh.setColorSchemeResources(
                R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsers();
            }
        });
    }

    private void getUsers() {
        swipeRefresh.setRefreshing(true);
        presenter.getUsers();
    }

    @Override
    public void onUsers(@NonNull ArrayList<User> users) {
        listUsers.clear();
        listUsers.addAll(users);
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onErrorUsers(@NonNull String men) {
        showDialogMessage(men, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getUsers();
            }
        });
        swipeRefresh.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserSelected(@NonNull User user) {
        CardsActivity_.intent(this).user(user).start();
    }
}
