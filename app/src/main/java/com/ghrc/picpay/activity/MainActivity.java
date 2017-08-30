package com.ghrc.picpay.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ghrc.picpay.R;
import com.ghrc.picpay.fragment.CreditCardFragment;
import com.ghrc.picpay.fragment.HistoryFragment;
import com.ghrc.picpay.fragment.UserFragment;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


/**
 * Created by Guilherme on 27/08/2017.
 */


public class MainActivity extends AppCompatActivity {
    private Drawer result = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withRootView(R.id.drawer_container)
                .withSavedInstance(savedInstanceState)
                .withDisplayBelowStatusBar(false)
                .withTranslucentStatusBar(false)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Usuários").withIcon(GoogleMaterial.Icon.gmd_account_circle).withIdentifier(0),
                        new PrimaryDrawerItem().withName("Meus Cartões").withIcon(GoogleMaterial.Icon.gmd_credit_card).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Meus Pagamentos").withIcon(GoogleMaterial.Icon.gmd_history).withIdentifier(2)
                )
                .withActionBarDrawerToggleAnimated(true)
                .withSavedInstance(savedInstanceState)
                .withDrawerLayout(R.layout.material_drawer_fits_not)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switchAction(drawerItem.getIdentifier());
                        return false;
                    }
                }).withSelectedItem(0)
                .build();
        if (savedInstanceState == null){
            switchAction(0);
        }

    }
    private void switchAction(long i){
        Fragment fragment = null;
        String title ="";
        switch ((int) i){
            case 0:
                fragment = new UserFragment();
                title = "Usuários";
                break;
            case 1:
                fragment = new CreditCardFragment();
                title = "Meus cartões";
                break;
            case 2:
                fragment = new HistoryFragment();
                title = "Meu Histórico de Pagamentos";
                break;
        }
        if(fragment!= null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            transaction.commit();
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

}
