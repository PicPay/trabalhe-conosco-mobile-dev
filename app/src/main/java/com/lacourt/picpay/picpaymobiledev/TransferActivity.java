package com.lacourt.picpay.picpaymobiledev;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class TransferActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<User>> {

    public static final String REQUEST_URL = "http://careers.picpay.com/tests/mobdev/users";
    public static final int LOADER_ID = 1;

    private UserAdapter adapter = null;
    private ListView usersListView;

    //Para o caso de não carregar a lista.
    private TextView emptyStateTextView;
    private ProgressBar progressBar;

    LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_activity);

        emptyStateTextView = (TextView)findViewById(R.id.empty_state_textview);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        usersListView = (ListView)findViewById(R.id.list);
        usersListView.setEmptyView(emptyStateTextView);

        checkInternetConnection();
//        LoaderManager loaderManager = getLoaderManager();
//        loaderManager.initLoader(LOADER_ID, null, this);

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User)parent.getAdapter().getItem(position);
                Intent intent = new Intent(TransferActivity.this, RegisterCard.class);
                intent.putExtra("UserId", user.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {


        return new UserLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> users) {
        progressBar.setVisibility(View.GONE);
        emptyStateTextView.setText(R.string.no_results);

        if(adapter != null){
            adapter.clear();
        }

        if(users != null && adapter != null) {
            adapter.addAll(users);
        }

        if(users == null){
            return;
        }

        // Update the information displayed to the user.
        updateUi(users);
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {
        adapter.clear();
    }

    private void checkInternetConnection(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected) {

            loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);

        } else {

            progressBar.setVisibility(View.GONE);
            emptyStateTextView.setText("Sem conexão com a internet.");
        }
    }

    private void updateUi(List<User> users){
        //adapter
        adapter = new UserAdapter(TransferActivity.this, 0, users);

        emptyStateTextView.setText(R.string.no_results);

        usersListView.setAdapter(adapter);
    }
}
