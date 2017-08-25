package com.example.vitor.picpaytest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.vitor.picpaytest.R;
import com.example.vitor.picpaytest.dominio.Historico;
import com.example.vitor.picpaytest.helper.HistoricoAdapter;
import com.example.vitor.picpaytest.persistencia.PicPayDB;

import java.util.List;

public class HistoricoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private List<Historico> hist;
    private Toolbar toolbar;
    private ListView listView;
    private HistoricoAdapter adapter;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        setupToolbar();
        setupMenuLateral();
        setupComponents();

        PicPayDB db = new PicPayDB(HistoricoActivity.this);
        try{
            hist = db.listaHistorico();
        }finally {
            db.close();
        }

        adapter = new HistoricoAdapter(hist, this);
        listView.setAdapter(adapter);

        for(int i = 0; i < hist.size(); i++){
            Log.e("TESTE", hist.get(i).getUsername());
        }

    }

    private void setupComponents(){
        img = (ImageView) findViewById(R.id.img_historico);
        listView = (ListView) findViewById(R.id.lista_historico);
    }

    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Histórico");
        setSupportActionBar(toolbar);

    }

    private void setupMenuLateral(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_item_historico);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_item_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
