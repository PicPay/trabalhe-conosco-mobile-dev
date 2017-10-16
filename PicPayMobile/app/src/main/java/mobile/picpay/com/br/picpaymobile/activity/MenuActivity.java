package mobile.picpay.com.br.picpaymobile.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import mobile.picpay.com.br.picpaymobile.application.MyApplication;
import mobile.picpay.com.br.picpaymobile.dao.UsuarioDAO;
import mobile.picpay.com.br.picpaymobile.entity.Usuario;
import mobile.picpay.com.br.picpaymobile.infra.Conexao;
import mobile.picpay.com.br.picpaymobile.R;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog dialog;
    private TextView nome;
    private TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nome = (TextView) findViewById(R.id.txvNome);
        email = (TextView) findViewById(R.id.txvEmail);
        nome.setText(MyApplication.getInstance().getUsuario().getNome());
        email.setText(MyApplication.getInstance().getUsuario().getEmail());



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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mnPerfil) {
            Intent intent = new Intent(MenuActivity.this, PerfilActivity.class);
            startActivity(intent);
        } else if (id == R.id.mnPagamento) {
            Conexao c = new Conexao();
            c.conectarWsAsync(this);
            Intent intent = new Intent(MenuActivity.this, PessoasActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void VoltarMenuPrincipal(MenuItem item) {
        Toast.makeText(this, "Menu Principal", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MenuActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void SairPrograma(MenuItem item) {
        Toast.makeText(this, "Fechando", Toast.LENGTH_SHORT).show();
        System.exit(0);
    }
}
