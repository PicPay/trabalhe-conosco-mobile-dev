package com.example.vitor.picpaytest.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vitor.picpaytest.R;
import com.example.vitor.picpaytest.activity.TransferenciaActivity;
import com.example.vitor.picpaytest.dominio.Usuario;
import com.example.vitor.picpaytest.helper.HttpHandler;
import com.example.vitor.picpaytest.helper.ListaAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

//Fragmento dentro de MainActivity que lista contatos
public class ListFragment extends Fragment{

    private boolean listado = false;

    private ListaAdapter adapter;

    private ProgressDialog pDialog;
    private ListView lv;

    //URL que leva o JSON
    private static String url = "http://careers.picpay.com/tests/mobdev/users";

    List<Usuario> usuarioList;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        lv = (ListView) view.findViewById(R.id.ListView);

        lv.setDividerHeight(5);

        enventoClickLista();

            listado = testaConexao(this.getContext());

            if(listado){
                GetContacts getContacts = new GetContacts(this.getContext());
                getContacts.execute();
            }else{
                Toast.makeText(this.getContext(), "Erro de conexão - Verifique sua Conexão ou Tente novamente mais tarde", Toast.LENGTH_LONG).show();
                onDestroy();
            }

        return view;
    }

    private void enventoClickLista() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Usuario usuarioSelecionado = (Usuario) adapterView.getAdapter().getItem(i);
                String nome = usuarioSelecionado.getName();
                int id = usuarioSelecionado.getId();
                String username = usuarioSelecionado.getUsername();
                String img = usuarioSelecionado.getImg();
                Bitmap imagem = usuarioSelecionado.getImagem();


                Intent intent = new Intent(view.getContext(), TransferenciaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("name", nome);
                bundle.putString("username", username);
                bundle.putString("img", img);

                intent.putExtras(bundle);
                intent.putExtra("imagem", imagem);
                startActivity(intent);
            }
        });
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        private Context context;

        public GetContacts(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Carregando...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.buscaUrl(url);

            Gson gson = new Gson();

            Type usuarioListType = new TypeToken<ArrayList<Usuario>>(){}.getType();

            usuarioList = gson.fromJson(jsonStr, usuarioListType);

            for(int i = 0; i < usuarioList.size(); i++) {
                URL url = null;
                Bitmap bitmap = null;
                try {
                    url = new URL(usuarioList.get(i).getImg());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(input);

                    usuarioList.get(i).setImagem(bitmap);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

                return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }

            adapter = new ListaAdapter(usuarioList, context);
            lv.setAdapter(adapter);

        }
    }

    private boolean testaConexao(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
