package viniciusmaia.com.vinipay.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import viniciusmaia.com.vinipay.R;
import viniciusmaia.com.vinipay.activities.NovaSenhaActivity;
import viniciusmaia.com.vinipay.modelo.Usuario;
import viniciusmaia.com.vinipay.util.ControleSessao;

public class MeuPerfilFragment extends Fragment {

    private View view;
    private EditText editNomeCompleto;
    private EditText editUsuario;
    private Realm realm;
    private ControleSessao controleSessao;
    private Usuario usuario;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.meu_perfil, container, false);
        setHasOptionsMenu(true);

        editNomeCompleto = (EditText) view.findViewById(R.id.editNomeCompleto);
        editUsuario = (EditText) view.findViewById(R.id.editUsuario);

        controleSessao = new ControleSessao(getActivity());

        if (realm == null){
            realm = Realm.getDefaultInstance();
        }

        RealmResults<Usuario> usuarios = realm.where(Usuario.class).equalTo("id", controleSessao.getIdUsuario()).findAll();

        usuario = usuarios.get(0);

        editNomeCompleto.setText(usuario.getNome());
        editUsuario.setText(usuario.getUsuario().replace("@",""));

        Button botaoSalvar = (Button) view.findViewById(R.id.botaoSalvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdicaoValida()){
                    try{
                        realm.beginTransaction();

                        usuario.setNome(editNomeCompleto.getText().toString());
                        usuario.setUsuario("@" + editUsuario.getText().toString());
                        
                        realm.copyToRealm(usuario);
                        realm.commitTransaction();

                        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
                        View view = navigationView.getHeaderView(0);

                        TextView textUsuario = (TextView) view.findViewById(R.id.textUsuario);
                        textUsuario.setText(usuario.getUsuario());

                        TextView textNomeCompleto = (TextView) view.findViewById(R.id.textNomeCompleto);
                        textNomeCompleto.setText(usuario.getNome());

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



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_nova_senha, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_alterar_senha:
                Intent novaSenhaIntent = new Intent(getActivity(), NovaSenhaActivity.class);
                startActivity(novaSenhaIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
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
        RealmResults<Usuario> usuarios = realm.where(Usuario.class).
                equalTo("usuario", usuario).
                notEqualTo("id", controleSessao.getIdUsuario()).
                findAll();

        if (usuarios == null || usuarios.size() == 0){
            return true;
        }
        return false;
    }
}
