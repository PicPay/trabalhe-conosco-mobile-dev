package br.com.devmarques.picpaytestes.Fragmentos.Transacao;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import br.com.devmarques.picpaytestes.DAO.HistoryTransaction.TransacoesDAO;
import br.com.devmarques.picpaytestes.Dados.Transacoes;
import br.com.devmarques.picpaytestes.Fragmentos.Pessoas.FragmentoListaPessoas;
import br.com.devmarques.turismo.picpaytestes.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Roger on 08/11/2017.
 */

public class Transacaos extends Fragment {

    View root;

    String resultado;
    TransacoesDAO transacoesDAO;
    String id;
    String timestamp;
    String usuarioDestinatarioNome, img,username , status;
    String aprovada;
    Button salvar;
    Transacoes transacoes;
    String idUSER;
    String cardnumero, datavalidade, cvv, valortransacao, Bandeira;
    ImageView cartao, deletar;
    TextView numeroCartao , nome, validade, nomeDestinatario;
    EditText valueTransaction, cvv_confirm;
    CircleImageView circleImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.transacao, container, false);
        deletar = root.findViewById(R.id.deletarouselecionar);
        cartao = root.findViewById(R.id.cartaoimgTR);
        numeroCartao = root.findViewById(R.id.numCardTR);
        nome = root.findViewById(R.id.nomedocardTR);
        validade = root.findViewById(R.id.validadeTR);
        salvar = root.findViewById(R.id.salvartr);
        nomeDestinatario = root.findViewById(R.id.username);
        circleImageView = root.findViewById(R.id.profile_imagedestinatario);
        valueTransaction = root.findViewById(R.id.valortransacao);
        cvv_confirm = root.findViewById(R.id.cvvcartaoTR);

        InstanciandoBanco();

        // recebendo dados de outro fragmento
        idUSER = getArguments().getString("userID");
        usuarioDestinatarioNome = getArguments().getString("Nome");
        img = getArguments().getString("Img");
        username =  getArguments().getString("Usuario");

        nomeDestinatario.setText(usuarioDestinatarioNome);

        Picasso.with(getContext())
                .load(img)
                .resize(300, 300)
                .centerInside()
                .into(circleImageView);


        //Toast.makeText(getContext(), usuarioDestinatarioNome, Toast.LENGTH_SHORT).show();

        // pegando dados do cartão
        cardnumero = getArguments().getString("cardnumber");
        datavalidade =getArguments().getString("dt");
        cvv =getArguments().getString("cvv");
        Bandeira = getArguments().getString("Bandeira");


        numeroCartao.setText("XXXX-XXXX-XXXX-" + cardnumero.substring(12,16));
        nome.setText(Bandeira);
        validade.setText("Validade: "+datavalidade);

        if (Bandeira.equals("MasterCard")){
            Picasso.with(getContext())
                    .load(R.drawable.master)
                    .resize(300, 300)
                    .centerInside()
                    .into(cartao);
        }else{
            Picasso.with(getContext())
                    .load(R.drawable.visa)
                    .resize(300, 300)
                    .centerCrop()
                    .into(cartao);
        }

        salvar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                if (valueTransaction.getText().toString().length()>1){
                    if (cvv_confirm.getText().toString().equals(cvv)){
                        valortransacao= valueTransaction.getText().toString();
                        new SendPostRequest().execute();
                    }else{
                        Toast.makeText(getContext(), "CVV Incorreto digite novamente", Toast.LENGTH_SHORT).show();
                    }
                }else{
                        Toast.makeText(getContext(), "Por favor digite algum valor", Toast.LENGTH_SHORT).show();
                }

           }
       });

        return root;
    }


    public class SendPostRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://careers.picpay.com/tests/mobdev/transaction"); // here is your URL path

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("card_number", cardnumero);
                postDataParams.put("cvv", cvv);
                postDataParams.put("value", valortransacao);
                postDataParams.put("expiry_date", datavalidade);
                postDataParams.put("destination_user_id", idUSER);

                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(postDataParams.toString());

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);

                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }


        }

        @Override
        protected void onPostExecute(String result) {
            setResultado(result);
            //Toast.makeText(getContext(), result,Toast.LENGTH_LONG).show();
            SalvarTransacoes();
        }
    }


    public void InstanciandoBanco(){
        transacoesDAO = new TransacoesDAO(getContext());
    }

    public void SalvarTransacoes(){

        if (resultado.equals("400")) {
            Toast.makeText(getContext(), "Desculpe algo errado aconteceu...", Toast.LENGTH_SHORT).show();
        }else {
            try {

                JSONObject obj = new JSONObject(resultado).getJSONObject("transaction");
//
                id = obj.getString("id");
                timestamp = obj.getString("timestamp");
                valortransacao = obj.getString("value");
                aprovada = obj.getString("success");
                status = obj.getString("status");

                // nome,  fotoperfil,  user,  cardNameandNumber,  transacaoNum,  valor,  aprovada,  status)
                transacoes = new Transacoes(usuarioDestinatarioNome, img, username,(cardnumero.substring(12,16) +"-"+Bandeira),id,valortransacao,status,aprovada);
                inserir(transacoes);

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
}


    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    private void inserir(Transacoes transacoes) {
        if (transacoesDAO.insere(transacoes) > 0){
            Toast.makeText(getContext(), "Concluído!", Toast.LENGTH_SHORT).show();

            FragmentoListaPessoas Frag_lista = new FragmentoListaPessoas();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.conteinerf, Frag_lista);
            fragmentTransaction.commit();
        }else {
            Toast.makeText(getContext(), "Ocorreu algum erro ao salvar...", Toast.LENGTH_SHORT).show();

        }
    }
}
