package picpayteste.devmarques.com.picpay_teste.activitys;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import picpayteste.devmarques.com.picpay_teste.R;
import picpayteste.devmarques.com.picpay_teste.adapters.ListagemUsuarios;
import picpayteste.devmarques.com.picpay_teste.dados.lista.usuario.Usuario;
import picpayteste.devmarques.com.picpay_teste.dao.cartao.CartaoDaoDB;
import picpayteste.devmarques.com.picpay_teste.utils.ConvertJsonListObject;

public class ListaAmigosActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private  List<Usuario> usuarioListOrg;
    private EditText pesquisa;
    private ListagemUsuarios listagemUsuarioAdapter;
    private AppBarLayout appbar;
    private CartaoDaoDB cartaoDaoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);
        // instanciando banco.
        cartaoDaoDB = new CartaoDaoDB(getApplicationContext());

        //Inicializando IDS
        inicializar_ids();

        //Carregando lista
        new HttpService().execute();


        pesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter_pesquisa(s.toString());
            }
        });

        pesquisa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pesquisa.setCursorVisible(true);
                if (hasFocus) {
                    appbar.setExpanded(false);
                    pesquisa.setBackground(getResources().getDrawable(R.drawable.busca_selecionada));
                }else {
                    pesquisa.setBackground(getResources().getDrawable(R.drawable.busca));
                }
            }
        });

        pesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesquisa.setBackground(getResources().getDrawable(R.drawable.busca_selecionada));
                pesquisa.setCursorVisible(true);
                appbar.setExpanded(false);

            }
        });

        pesquisa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (pesquisa.getRight() - pesquisa.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        pesquisa.setText("");

                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);

                        pesquisa.setCursorVisible(false);

                        pesquisa.setBackground(getResources().getDrawable(R.drawable.busca));

                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (pesquisa.isCursorVisible()) {
            pesquisa.moveCursorToVisibleOffset();
            appbar.setExpanded(true);
            pesquisa.setBackground(getResources().getDrawable(R.drawable.busca));
        }else{
            onBackPressed();
        }

    }


    public void inicializar_ids(){
        progressBar = findViewById(R.id.carregando);
        pesquisa = findViewById(R.id.et_search);
        appbar = (AppBarLayout) findViewById(R.id.app_bar);
    }

    private void filter_pesquisa(String param){
        List<Usuario> usuarioList = new ArrayList<>();
        String semacento;
        for (Usuario usuario : usuarioListOrg){
            semacento = remove_acentos(usuario.getName());
            if (semacento.toLowerCase().contains(remove_acentos(param.toLowerCase()))) {
                usuarioList.add(usuario);
            }
        }
        listagemUsuarioAdapter.filterList(usuarioList);
    }

    public static String remove_acentos(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    public class HttpService extends AsyncTask<String, String, List<Usuario>> {


        @Override
        protected List<Usuario> doInBackground(String... voids) {
            StringBuilder resposta = new StringBuilder();

            ConvertJsonListObject jsonResposta = new ConvertJsonListObject();

            try {
                URL url = new URL("http://careers.picpay.com/tests/mobdev/users");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/text");
                connection.setRequestProperty("Accept", "application/text");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.connect();

                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    resposta.append(scanner.nextLine());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (resposta.length() > 0) {
                return jsonResposta.getList(resposta.toString(), Usuario.class);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Usuario> usuarios) {
            progressBar.setVisibility(View.GONE);

            if ((usuarios != null) &&(!usuarios.isEmpty())){
                // lista original.
                usuarioListOrg = usuarios;

                ListaRecycler(usuarios);
            }
        }
    }

    public void ListaRecycler(List<Usuario> usuarioList) {
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.my_recyvlerview);

        StaggeredGridLayoutManager mStaggeredVerticalLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
        recyclerView.setLayoutManager(layoutManager);

        listagemUsuarioAdapter = new ListagemUsuarios(usuarioList, getApplicationContext());

        recyclerView.setLayoutManager(mStaggeredVerticalLayoutManager);
        recyclerView.setAdapter(listagemUsuarioAdapter);
        recyclerView.setVisibility(View.VISIBLE);
    }

}
