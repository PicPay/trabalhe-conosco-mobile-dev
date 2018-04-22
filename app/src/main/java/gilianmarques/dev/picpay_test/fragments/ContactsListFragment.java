package gilianmarques.dev.picpay_test.fragments;


import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
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

    /**
     * @param callback um callback para notificar a activity que um contato foi selecionado
     * @return a atual instancia deste fragmento. O truque serve para chamar o metodo direto na
     * declaração uma vez que o construtor de um fragmento deve ser vazio.
     */
    public ContactsListFragment attachCallback(TransactionCallback callback) {
        this.callback = callback;
        return this;
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            init();
                        }
                    });
                } else {
                    AppPatterns.notifyUser(getActivity(), "Error code: ".concat(String.valueOf(errorCode)), AppPatterns.SUCCESS);
                }
            }
        };
        new ContactsDownloaderAsync(callback).execute();
    }

    public void init() {

        ContactsListAdapter mAdapter = new ContactsListAdapter(getActivity(), mContacts, this);

        Drawable mDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider);

        DividerItemDecoration decor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        if (mDrawable != null) decor.setDrawable(mDrawable);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.rv_contacts);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(decor);
        mRecyclerView.setAdapter(mAdapter);

        rootView.findViewById(R.id.loading_view).setVisibility(View.GONE);
    }


    @Override
    public void onContactClick( Contact mContact) {
     if (callback != null) callback.contactSelected(mContact);
    }
}
