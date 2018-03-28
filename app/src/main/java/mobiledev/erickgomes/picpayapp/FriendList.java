package mobiledev.erickgomes.picpayapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import mobiledev.erickgomes.picpayapp.fragments.FragFriendList;

public class FriendList extends AppCompatActivity {

    private FragFriendList mListaAmigos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        initToolbar(); //Seta as configuracoes da custom toolbar
        initFragment();
    }

    private void initToolbar() {
        Toolbar datenPayToolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(datenPayToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void initFragment() {
        if (mListaAmigos == null) {
            mListaAmigos = new FragFriendList();
        }

        getFragmentManager().beginTransaction().replace(R.id.layout_container, mListaAmigos, FragFriendList.TAG).commit();

    }

}
