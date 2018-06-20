package br.com.gsas.app.picpay.Feed;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import br.com.gsas.app.picpay.Connection.MyCallback;
import br.com.gsas.app.picpay.Connection.ServiceFeed;
import br.com.gsas.app.picpay.Domain.Feed;
import br.com.gsas.app.picpay.R;
import br.com.gsas.app.picpay.Util.ErroFragment;
import br.com.gsas.app.picpay.Util.VazioFragment;

public class FeedFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Feed> feeds;
    private ProgressBar load;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        toolbar = view.findViewById(R.id.toolbar_home);

        load = view.findViewById(R.id.load);

        recyclerView = view.findViewById(R.id.lista_feed);

        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        setToolbar(toolbar);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        taskFeed();
    }

    private void taskFeed() {

        ServiceFeed service = new ServiceFeed(getContext());

        service.getAllBD(atualizaFeed());

    }

    private MyCallback<Feed> atualizaFeed() {
        return new MyCallback<Feed>() {
            @Override
            public void sucess(List<Feed> list) {

                load.setVisibility(View.GONE);
                FeedFragment.this.feeds = list;
                recyclerView.setAdapter(new FeedAdapter(getContext(), feeds));
            }

            @Override
            public void empty() {

                load.setVisibility(View.GONE);

                VazioFragment vazioFragment = new VazioFragment();
                getChildFragmentManager().beginTransaction().replace(R.id.frame_feed, vazioFragment, "Vazio").commitAllowingStateLoss();
            }

            @Override
            public void failure(String msg) {
                load.setVisibility(View.GONE);

                ErroFragment erroFragment = new ErroFragment();
                getChildFragmentManager().beginTransaction().replace(R.id.frame_feed, erroFragment, "Erro").commitAllowingStateLoss();

            }
        };
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    void setToolbar(Toolbar toolbar){

        if(getActivity() != null){

            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            getActivity().setTitle(getString(R.string.app_name));

        }
    }

}
