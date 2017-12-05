package viniciusmaia.com.vinipay.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import viniciusmaia.com.vinipay.R;
import viniciusmaia.com.vinipay.fragments.UsuariosFragment;
import viniciusmaia.com.vinipay.modelo.Usuario;
import viniciusmaia.com.vinipay.util.ControleSessao;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static int FragmentAtual;
    ControleSessao controleSessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        controleSessao = new ControleSessao(this);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Usuario> usuarios = realm.where(Usuario.class).equalTo("id", controleSessao.getIdUsuario()).findAll();

        if (usuarios != null && usuarios.size() > 0){
            Usuario usuario = usuarios.get(0);

            TextView textUsuario = (TextView) findViewById(R.id.textUsuario);
            textUsuario.setText(usuario.getUsuario());

            TextView textNome = (TextView) findViewById(R.id.textNomeUsuario);
            textNome.setText(usuario.getNome());
        }

        if (FragmentAtual == 0){
            exibeTelaSelecionada(R.id.nav_usuarios);
        }
        else{
            exibeTelaSelecionada(FragmentAtual);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void exibeTelaSelecionada(int id){
        Fragment fragment = null;

        switch (id){
            case R.id.nav_usuarios:
                fragment = new UsuariosFragment();
                FragmentAtual = R.id.nav_usuarios;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.commit();

                break;
            case  R.id.nav_cartoes:
                Intent intentMeuCartao = new Intent(this, MeuCartaoActivity.class);
                startActivity(intentMeuCartao);
                break;

            case R.id.nav_logout:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setTitle("Atenção");
                dialogBuilder.setMessage("Deseja realmente sair?");
                dialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controleSessao.encerraSessao();

                        Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                        intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentLogin);
                        dialog.dismiss();
                    }
                });
                dialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogBuilder.show();

            default:
                fragment = new UsuariosFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        exibeTelaSelecionada(id);

        return true;
    }
}
