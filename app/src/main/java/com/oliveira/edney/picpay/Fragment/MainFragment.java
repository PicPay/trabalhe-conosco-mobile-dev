package com.oliveira.edney.picpay.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.oliveira.edney.picpay.Adapter.MainAdapter;
import com.oliveira.edney.picpay.Class.Card;
import com.oliveira.edney.picpay.Class.Person;
import com.oliveira.edney.picpay.GetJSON;
import com.oliveira.edney.picpay.ItemClickListener;
import com.oliveira.edney.picpay.R;
import org.json.JSONArray;

/* Fragmento principal que exibe a lista de pessoas */

public class MainFragment extends Fragment {

    private JSONArray data;
    private MainAdapter adapter;

    public MainFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        Context c = v.getContext();
        RecyclerView recyclerView = v.findViewById(R.id.rv_main);

        /* RecyclerView */
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));

        data = new JSONArray();
        adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        /* Carrega a lista de pessoas para fazer o pagamento */
        new GetJSON(new GetJSON.Callback() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                data = jsonArray;
                adapter.setData(data);
                adapter.notifyDataSetChanged();
            }
        }).execute("http://careers.picpay.com/tests/mobdev/users");

        adapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Bundle bundle = new Bundle();
                Person person = new Person();
                Fragment fragment;

                /* Caso tenha ou não cartões cadastrados */
                if(new Card().fileExists(getActivity()))
                    fragment = new PaymentCardFragment();

                else
                    fragment = new PaymentFragment();

                try{

                    person.setFoto(data.getJSONObject(position).getString("img"));
                    person.setNome(data.getJSONObject(position).getString("name"));
                    person.setId(data.getJSONObject(position).getInt("id"));
                    person.setUsername(data.getJSONObject(position).getString("username"));

                    bundle.putSerializable("person",person);
                    fragment.setArguments(bundle);

                    if (getFragmentManager() != null) {

                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_frame, fragment)
                                .commit();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
