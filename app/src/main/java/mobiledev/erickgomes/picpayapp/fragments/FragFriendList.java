package mobiledev.erickgomes.picpayapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import mobiledev.erickgomes.picpayapp.FriendList;
import mobiledev.erickgomes.picpayapp.R;
import mobiledev.erickgomes.picpayapp.adapters.FriendAdapter;
import mobiledev.erickgomes.picpayapp.models.Friend;
import mobiledev.erickgomes.picpayapp.utils.NetworkUtil;

/**
 * Created by erickgomes on 23/03/2018.
 */

public class FragFriendList extends Fragment {

    public static final String TAG = FriendList.class.getSimpleName();

    private FriendAdapter mFriendAdapter;
    private FrameLayout contentLoading;
    private CompositeDisposable mSubscriptions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);
        initRecyclerView(view);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceStance) {
        super.onActivityCreated(savedInstanceStance);

        contentLoading.setVisibility(View.VISIBLE);
        getFriendsList();
    }

    private void initRecyclerView(View view) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.friends_list); // muda o titulo da toolbar da Activity

        mFriendAdapter = new FriendAdapter(getActivity());
        contentLoading = view.findViewById(R.id.content_loading);
        RecyclerView mRecyclerView = view.findViewById(R.id.friend_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mFriendAdapter);
    }

    private void getFriendsList() {
        mSubscriptions = new CompositeDisposable();

        mSubscriptions.add(NetworkUtil.getRetrofit().getListFriends()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(List<Friend> friends) {
        contentLoading.setVisibility(View.GONE);
        mFriendAdapter.setList(friends); //Seta a lista obtida pela API ao adapter

    }

    private void handleError(Throwable error) {

        contentLoading.setVisibility(View.GONE);

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                showSnackBarMessage(errorBody);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Erro de conexão!");
        }
    }

    private void showSnackBarMessage(String message) {
        if (getActivity() == null) {
            return;
        }
        Snackbar.make(getActivity().findViewById(R.id.activity_friend_list), message, Snackbar.LENGTH_SHORT).show();

    }


    @Override
    public void onPause() {
        super.onPause();
        if (mSubscriptions != null) {
            mSubscriptions.dispose();
        }
    }

}
