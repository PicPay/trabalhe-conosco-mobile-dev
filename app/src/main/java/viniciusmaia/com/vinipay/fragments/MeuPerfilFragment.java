package viniciusmaia.com.vinipay.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import viniciusmaia.com.vinipay.R;
import viniciusmaia.com.vinipay.activities.CadastroActivity;
import viniciusmaia.com.vinipay.activities.MainActivity;
import viniciusmaia.com.vinipay.modelo.Usuario;
import viniciusmaia.com.vinipay.util.ControleSessao;

public class MeuPerfilFragment extends Fragment {

    private View view;
    private EditText editNomeCompleto;
    private EditText editUsuario;
    private Realm realm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.meu_perfil, container, false);

        editNomeCompleto = (EditText) view.findViewById(R.id.editNomeCompleto);
        editUsuario = (EditText) view.findViewById(R.id.editUsuario);

        if (realm == null){
            realm = Realm.getDefaultInstance();
        }

        Button botaoSalvar = (Button) view.findViewById(R.id.botaoSalvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdicaoValida()){
                    ControleSessao controleSessao = new ControleSessao(getActivity());

                    RealmResults<Usuario> usuarios = realm.where(Usuario.class).equalTo("id", controleSessao.getIdUsuario()).findAll();

                    Usuario usuario = usuarios.get(0);

                    usuario.setNome(editNomeCompleto.getText().toString());
                    usuario.setUsuario("@" + editUsuario.getText().toString());

                    try{
                        realm.beginTransaction();
                        realm.copyToRealm(usuario);
                        realm.commitTransaction();

                        Toast.makeText(getActivity(), "Edição realizada com sucesso!", Toast.LENGTH_LONG).show();

                    }
                    catch (Exception e){
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
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
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.text_meu_perfil);
    }

    private boolean isEdicaoValida(){
        StringBuilder mensagemErroBuilder = new StringBuilder();

        String nome = editNomeCompleto.getText().toString();

        if (nome == null || nome.equals("")){
            mensagemErroBuilder.append("- Preencha o campo 'Nome Completo'. \n");
        }

        String usuario = editUsuario.getText().toString();

        if (usuario == null || usuario.equals("")){
            mensagemErroBuilder.append("- Preencha o campo 'Usuário'. \n");
        }

        if (!isUsuarioValido(usuario)){
            mensagemErroBuilder.append("- Já existe um cadastro com o usuário informado :( \n");
        }

        String mensagemErro = mensagemErroBuilder.toString();

        if (mensagemErro != null && !mensagemErro.equals("")){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setTitle("Atenção");
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

    private boolean isUsuarioValido(String usuario){
        usuario = "@" + usuario;
        RealmResults<Usuario> usuarios = realm.where(Usuario.class).equalTo("usuario", usuario).findAll();

        if (usuarios == null || usuarios.size() == 0){
            return true;
        }
        return false;
    }
}
