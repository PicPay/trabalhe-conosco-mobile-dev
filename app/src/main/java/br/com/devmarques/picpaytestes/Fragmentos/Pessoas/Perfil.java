package br.com.devmarques.picpaytestes.Fragmentos.Pessoas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.devmarques.turismo.picpaytestes.R;

/**
 * Created by Roger on 08/11/2017.
 */

public class Perfil extends Fragment {

    View root;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.perfil, container, false);


        return root;
    }
}
