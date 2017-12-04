package viniciusmaia.com.vinipay.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import viniciusmaia.com.vinipay.R;
import viniciusmaia.com.vinipay.modelo.CartaoCredito;

/**
 * Created by User on 04/12/2017.
 */

public class MeuCartaoActivity extends AppCompatActivity {

    private Realm realm;
    private Spinner mSpinnerAno;
    private Spinner mSpinnerMes;
    private EditText mEditCodigoSeguranca;
    private EditText mEditNumeroCartao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meu_cartao);

        inflateToolbar();

        Button botaoSalvar = (Button)findViewById(R.id.botaoSalvar);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botaoSalvar_click(v);
            }
        });

        mSpinnerAno = (Spinner)findViewById(R.id.spinnerAno);

        List<String> listaAnos = getListaAnos();
        ArrayAdapter<String> anosAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaAnos);
        anosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerAno.setAdapter(anosAdapter);

        mEditNumeroCartao = (EditText)findViewById(R.id.editNumeroCartao);
        mSpinnerMes = (Spinner)findViewById(R.id.spinnerMes);
        mEditCodigoSeguranca = (EditText)findViewById(R.id.editCodigoSeguranca);

        realm = realm.getDefaultInstance();

        RealmResults<CartaoCredito> cartoes = realm.where(CartaoCredito.class).equalTo("idUsuario", 1).findAll();

        if (cartoes != null && cartoes.size() > 0){
            CartaoCredito cartao = cartoes.get(0);

            mEditNumeroCartao.setText(cartao.getNumero());
            mEditCodigoSeguranca.setText(cartao.getCodigoSeguranca());

            String mes = cartao.getValidade().split("/")[0];
            String ano = cartao.getValidade().split("/")[1];

            for (int i = 0; i < mSpinnerMes.getAdapter().getCount(); i++){
                if (mSpinnerMes.getAdapter().getItem(i).toString().contains(mes)){
                    mSpinnerMes.setSelection(i);
                    break;
                }
            }

            for (int i = 0; i < mSpinnerAno.getAdapter().getCount(); i++){
                if (mSpinnerAno.getAdapter().getItem(i).toString().contains(ano)){
                    mSpinnerAno.setSelection(i);
                    break;
                }
            }
        }
    }

    private void inflateToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.text_cartoes);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void botaoSalvar_click(View view){
        if (isFormularioValido()){
            CartaoCredito cartao = new CartaoCredito();
            RealmResults<CartaoCredito> cartoes = realm.where(CartaoCredito.class).equalTo("idUsuario", 1).findAll();

            if (cartoes != null && cartoes.size() > 0){
                cartao = cartoes.get(0);
            }
            else{
                cartao = new CartaoCredito();
                Number maiorId = realm.where(CartaoCredito.class).max("id");
                int proximoId;

                if (maiorId == null){
                    proximoId = 1;
                }
                else{
                    proximoId = maiorId.intValue() + 1;
                }

                cartao.setId(proximoId);
            }

            realm.beginTransaction();

            String numeroCartao = mEditNumeroCartao.getText().toString();

            cartao.setCodigoSeguranca(mEditCodigoSeguranca.getText().toString());
            cartao.setIdUsuario(1);
            cartao.setNumero(numeroCartao);

            StringBuilder validadeBuilder = new StringBuilder(mSpinnerMes.getSelectedItem().toString());
            validadeBuilder.append("/");
            validadeBuilder.append(mSpinnerAno.getSelectedItem().toString());

            cartao.setValidade(validadeBuilder.toString());

            try{
                realm.copyToRealm(cartao);
                realm.commitTransaction();

                Toast.makeText(this, "Cartão salvo com sucesso", Toast.LENGTH_LONG).show();

                finish();
            }
            catch (Exception e){
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setTitle("Erro fatal");
                dialogBuilder.setMessage("O aplicativo apresentou uma falha e precisou ser finalizado.");
                dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }

    private boolean isFormularioValido(){
        StringBuilder mensagemErroBuilder = new StringBuilder();

        String numeroCartao = mEditNumeroCartao.getText().toString();

        if (numeroCartao == null || numeroCartao.replaceAll("\\s+","").equals("")){
            mensagemErroBuilder.append("- Preencha o campo 'Número do Cartão' \n");
        }

        String textMesSelecionado = mSpinnerMes.getSelectedItem().toString();

        if (textMesSelecionado == null){
            mensagemErroBuilder.append("- Selecione um mês válido \n");
        }

        String textAnoSelecionado = mSpinnerAno.getSelectedItem().toString();

        if (textAnoSelecionado == null){
            mensagemErroBuilder.append("- Selecione um ano válido \n");
        }

        if (textAnoSelecionado != null && textMesSelecionado != null){
            int ano = Integer.parseInt(textAnoSelecionado);
            int mes = Integer.parseInt(textMesSelecionado);

            Calendar dataAtual = Calendar.getInstance();

            if (ano == dataAtual.get(Calendar.YEAR) && mes < dataAtual.get(Calendar.MONTH)){
                mensagemErroBuilder.append("- Cartão expirado \n");
            }
        }

        String codigo = mEditCodigoSeguranca.getText().toString();

        if (codigo == null || codigo.replaceAll("\\s+","").equals("")){
            mensagemErroBuilder.append("- Preencha o campo 'Código de Segurança'");
        }

        String mensagemErro = mensagemErroBuilder.toString();

        if (mensagemErro != null && !mensagemErro.equals("")){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Erro");
            dialogBuilder.setMessage(mensagemErro);
            dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialogBuilder.show();

            return false;
        }

        return true;
    }

    private List<String> getListaAnos(){
        List<String> anos = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        int ano = calendar.get(Calendar.YEAR);

        for (int i = 0; i <= 10; i++, ano++){
            anos.add(String.valueOf(ano));
        }

        return anos;
    }
}
