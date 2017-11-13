package br.com.devmarques.picpaytestes.Fragmentos.Pessoas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import br.com.devmarques.picpaytestes.Adapter.AdapterLista;
import br.com.devmarques.picpaytestes.Dados.Pessoas;
import br.com.devmarques.turismo.picpaytestes.R;

/**
 * Created by Roger on 08/11/2017.
 */

public class FragmentoListaPessoas extends Fragment {

    View root;
    String TextJson;
    ProgressBar p;
    ArrayList<Pessoas> pessoasrecebe = new ArrayList<Pessoas>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.lista, container, false);
        p = root.findViewById(R.id.progressBar);
        p.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        // carregando json:
        if (haveNetworkConnection()) {
            new DownloadFileFromURL().execute("http://careers.picpay.com/tests/mobdev/users");
        }else{
            if (Existe("Lista")){
                JsonOFF("Lista");
            }else {
                Toast.makeText(getContext(), "Por favor conecte a internet!", Toast.LENGTH_SHORT).show();
            }
        }

        return root;
    }

    public boolean Existe(String nome){

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root);
        boolean exists = false;


        exists = new File(getContext().getFilesDir() + "/" + nome + ".json").exists();

        return exists;
    }

    // verifica se tem internet
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public void ListaRecycler(){
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclelista);
        recyclerView.setAdapter(new AdapterLista(pessoasrecebe ,getContext() ));
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    public void JsonOFF(String marca){

        try {

            JSONArray array = new JSONArray(read_file(getContext(),marca));

            for (int i=0; i < array.length(); i++ ){
                JSONObject obj = array.getJSONObject(i);
                //{"id":1001,"name":"Eduardo Santos","img":"https://randomuser.me/api/portraits/men/9.jpg","username":"@eduardo.santos"}
                pessoasrecebe.add(new Pessoas( obj.getInt("id"),obj.getString("name") ,obj.getString("img") ,obj.getString("username")));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // listando
        ListaRecycler();
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread
         * */
        @Override
        protected void onPreExecute() {

            //Toast.makeText(getContext(), "Aguarde Carregando....",Toast.LENGTH_SHORT).show();
            super.onPreExecute();

        }
        /**
         * Downloading file in background thread
         * */
        protected String doInBackground(String... f_url) {
            int count;
            try {
                String root = Environment.getExternalStorageDirectory().toString();

                System.out.println("Downloading");
                URL url = new URL(f_url[0]);

                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                //====================================================================================

                FileOutputStream output = null;
                output = getContext().openFileOutput(("Lista.json"), Context.MODE_PRIVATE);
                //===============================
                byte data[] = new byte[1024];

                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    // writing data to file
                    output.write(data, 0, count);
                }


                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }
        /**
         * After completing background task
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            p.setVisibility(View.GONE);
            //Toast.makeText(getContext(), "Sucesso!",Toast.LENGTH_SHORT).show();
            System.out.println("Downloaded");
            JsonOFF("Lista");

            //progressload.setVisibility(View.GONE);
        }

    }

    public String read_file(Context context, String filename) {
        try {
            FileInputStream fis = context.openFileInput(filename + ".json");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }

}
