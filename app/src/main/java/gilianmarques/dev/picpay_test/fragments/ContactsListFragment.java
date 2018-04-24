package gilianmarques.dev.picpay_test.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.adapters.ContactsListAdapter;
import gilianmarques.dev.picpay_test.asyncs.ContactsDownloaderAsync;
import gilianmarques.dev.picpay_test.models.Contact;
import gilianmarques.dev.picpay_test.utils.AppPatterns;
import gilianmarques.dev.picpay_test.utils.TransactionCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsListFragment extends Fragment implements ContactsListAdapter.ItemClickCallback {
    private List<Contact> mContacts;
    private View rootView;
    private TransactionCallback callback;
    private Activity mActivity;

    public static ContactsListFragment newInstance(TransactionCallback callback) {

        ContactsListFragment mContactsListFragment = new ContactsListFragment();
        mContactsListFragment.callback = callback;
        return mContactsListFragment;
    }

    @Override
    public void onAttach(Context context) {
        mActivity = getActivity();
        if (mActivity != null) {
            ActionBar mActionBar = ((AppCompatActivity) mActivity).getSupportActionBar();
            if (mActionBar != null) mActionBar.setTitle(getString(R.string.selecione_um_contato));
        }
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        ContactsDownloaderAsync.ContactsCallback callback = new ContactsDownloaderAsync.ContactsCallback() {
            @Override
            public void resut(List<Contact> mContacts, int errorCode) {
                if (errorCode == -1) {
                    ContactsListFragment.this.mContacts = mContacts;
                    if (mActivity != null) mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            init();
                        }
                    });
                } else {
                    AppPatterns.notifyUser(mActivity, "Error code: ".concat(String.valueOf(errorCode)), AppPatterns.SUCCESS);
                    mActivity.finish();
                }
            }
        };
        new ContactsDownloaderAsync(callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void init() {

        ContactsListAdapter mAdapter = new ContactsListAdapter(mActivity, mContacts, this);

        Drawable mDrawable = ContextCompat.getDrawable(mActivity, R.drawable.divider);

        DividerItemDecoration decor = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
        if (mDrawable != null) decor.setDrawable(mDrawable);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.rv_contacts);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(decor);
        mRecyclerView.setAdapter(mAdapter);

        rootView.findViewById(R.id.loading_view).setVisibility(View.GONE);
    }

    @Override
    public void onContactClick(Contact mContact) {
        if (callback != null) callback.contactSelected(mContact);
    }


}
