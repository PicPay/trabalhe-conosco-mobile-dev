package mobile.picpay.com.br.picpaymobile.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import mobile.picpay.com.br.picpaymobile.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PessoasActivityFragment extends Fragment  {



    public PessoasActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pessoas, container, false);

    }




}
