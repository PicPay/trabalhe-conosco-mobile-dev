package mobile.picpay.com.br.picpaymobile.infra;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import mobile.picpay.com.br.picpaymobile.dao.PessoaDAO;
import mobile.picpay.com.br.picpaymobile.entity.Pessoa;
import mobile.picpay.com.br.picpaymobile.deserializer.PessoaDec;
import mobile.picpay.com.br.picpaymobile.entity.RootTransacao;
import mobile.picpay.com.br.picpaymobile.entity.Transacao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by johonatan on 06/10/2017.
 */

public class Conexao {

    public void conectarWsAsync(final AppCompatActivity activity) {

        final ProgressDialog progress = ProgressDialog.show(activity, "Aguarde...", "Baixando Usuários...", false);

        final Gson g = new GsonBuilder().registerTypeAdapter(Pessoa.class, new PessoaDec()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://careers.picpay.com/tests/mobdev/")
                .addConverterFactory(GsonConverterFactory.create(g))
                .build();


        IacessoWs iacessoWs = retrofit.create(IacessoWs.class);

        final Call<List<Pessoa>> call = iacessoWs.getPessoas();

        call.enqueue(new Callback<List<Pessoa>>() {
            @Override
            public void onResponse(Call<List<Pessoa>> call, Response<List<Pessoa>> response) {
                if (response.isSuccessful()) {
                    PessoaDAO pessoaDAO = new PessoaDAO(activity);
                    pessoaDAO.saveAll(response.body());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Erro!");
                    builder.setMessage("Erro ao baixar usuários!");
                    builder.setPositiveButton("OK",null);
                    builder.show();
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<Pessoa>> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Erro!");
                builder.setMessage("Erro ao baixar usuários!");
                builder.setPositiveButton("OK",null);
                builder.show();
                progress.dismiss();
            }
        });

    }

    public void enviarWsAsync(final AppCompatActivity activity, Transacao t) {
        final ProgressDialog progress = ProgressDialog.show(activity, "Aguarde...", "Enviando Transação...", false);

          Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://careers.picpay.com/tests/mobdev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IacessoWs iacessoWs = retrofit.create(IacessoWs.class);

        final Call<RootTransacao> call = iacessoWs.putTransacao(t);
        call.enqueue(new Callback<RootTransacao>() {
            @Override
            public void onResponse(Call<RootTransacao> call, Response<RootTransacao> response) {
                if (response.isSuccessful()) {
                    RootTransacao res = response.body();
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Resultado");
                    builder.setMessage("Sua Transação foi " + res.getTransaction().getStatus() + "!");
                    builder.setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                        }
                    });
                    builder.setPositiveButton("OK", null);
                    builder.show();


                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Erro!");
                    builder.setMessage("Erro na transação");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
                progress.dismiss();
            }


            @Override
            public void onFailure(Call<RootTransacao> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Erro!");
                builder.setMessage("Erro na transação");
                builder.setPositiveButton("OK",null);
                builder.show();
                progress.dismiss();
            }
        });



    }



}
