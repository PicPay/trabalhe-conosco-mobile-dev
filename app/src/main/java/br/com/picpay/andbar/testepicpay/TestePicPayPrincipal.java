package br.com.picpay.andbar.testepicpay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.*;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestePicPayPrincipal extends AppCompatActivity {

    Button btnTeste;
    ListView listaPessoas;
    String urlPessoas = "http://careers.picpay.com/tests/mobdev/users";
    static List<Pessoa> pessoasNoWebservice = new ArrayList<>();
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        listaPessoas = (ListView) findViewById(R.id.lvwPessoas);

        // check if you are connected or not
        if (isConnected())
        {
            adapter = new CategoryAdapter(this);
            listaPessoas.setAdapter(adapter);
            new HttpAsyncTask().execute(urlPessoas);
        } else
            {
                Toast.makeText(this, "Conexão indisponível", Toast.LENGTH_LONG).show();
        }

        listaPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                Pessoa.PessoaSingleton = (Pessoa) adapter.getItem(myItemInt);

                Intent detalhe = new Intent(TestePicPayPrincipal.this, Transferencia.class);
                startActivityForResult(detalhe,0);
            }
        });


    }

    private void Pesquisar(View view) {

        //new ObtemPessoas().execute(urlPessoas);

    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "";

            JSONArray todosOsDados = new JSONArray(result);

           for(int i =0; i<todosOsDados.length();i++ )
           {
               JSONObject itemPessoa = todosOsDados.getJSONObject(i);
               Pessoa p = new Pessoa();

               p.setId(itemPessoa.getInt("id"));
               p.setUsername(itemPessoa.getString("username"));
               p.setName(itemPessoa.getString("name"));
               p.setImg(itemPessoa.getString("img"));
               URL url_img = new URL(p.getImg());
               p.setImagem(BitmapFactory.decodeStream(url_img.openConnection().getInputStream()));

               pessoasNoWebservice.add(p);
           }



        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            for(Pessoa p:pessoasNoWebservice)
            {
                adapter.add(p);
            }
            adapter.notifyDataSetChanged();
        }
    }

    public class CategoryAdapter extends ArrayAdapter<Pessoa> {

        private final LayoutInflater inflater;

        public CategoryAdapter(Context context) {
            super(context, 0);
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_pessoa_celula, parent, false);
            }
            final TextView txv = (TextView) convertView.findViewById(R.id.textView);
            final ImageView image= (ImageView) convertView.findViewById(R.id.image);

            txv.setText(getItem(position).getName());

             image.setImageBitmap(getItem(position).getImagem());


            return convertView;

        }

    }

}

