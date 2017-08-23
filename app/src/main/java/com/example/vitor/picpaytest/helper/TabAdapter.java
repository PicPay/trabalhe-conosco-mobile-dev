package com.example.vitor.picpaytest.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.vitor.picpaytest.fragment.ListFragment;
import com.example.vitor.picpaytest.fragment.CartoesFragment;

/**
 * Created by Vitor on 19/08/2017.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    private String[] tituloAbas = {"CONTATOS", "CARTÕES"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new ListFragment();
                break;
            case 1:
                fragment = new CartoesFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }
}
