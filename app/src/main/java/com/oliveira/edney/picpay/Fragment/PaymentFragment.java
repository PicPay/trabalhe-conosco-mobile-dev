package com.oliveira.edney.picpay.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.oliveira.edney.picpay.Class.Person;
import com.oliveira.edney.picpay.ImageURL;
import com.oliveira.edney.picpay.R;

/* Fragmento que realiza pagamentos quando não houver
   cartões cadastrados */
public class PaymentFragment extends Fragment {

    private View v;
    private Context c;
    private Person person;
    private ImageView ivFoto;
    private RelativeLayout voltar;
    private TextView tvNome;
    private TextView tvId;
    private TextView tvUsername;
    private RelativeLayout addCard;
    private Bundle bundle;
    private EditText etValor;

    public PaymentFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_payment, container, false);
        c = v.getContext();

        /* Views */
        voltar = v.findViewById(R.id.rl_payment_voltar);
        tvNome = v.findViewById(R.id.tv_payment_nome);
        tvId = v.findViewById(R.id.tv_payment_id);
        tvUsername = v.findViewById(R.id.tv_payment_username);
        ivFoto = v.findViewById(R.id.iv_payment_foto);
        addCard = v.findViewById(R.id.rl_payment_addCard);
        etValor = v.findViewById(R.id.et_payment_valor);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        bundle = getArguments();

        if (bundle != null) {
            person = (Person) bundle.getSerializable("person");

            /* Limpa o EditText do valor a ser pago para facilitar o uso */
            etValor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    etValor.setText("");

                    InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null)
                        imm.showSoftInput(etValor, InputMethodManager.SHOW_IMPLICIT);

                }
            });

            /* View para adicionar o cartão de crédito */
            addCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AddCardFragment addCardFragment = new AddCardFragment();
                    addCardFragment.setArguments(bundle);

                    if (getFragmentManager() != null) {

                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_frame, addCardFragment)
                                .commit();
                    }
                }
            });

            /* View para voltar ao fragmento anterior */
            voltar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MainFragment mainFragment = new MainFragment();

                    if (getFragmentManager() != null) {

                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_frame, mainFragment)
                                .commit();
                    }
                }
            });

            /* Carrega os dados da pessoa */
            new ImageURL(new ImageURL.Callback() {
                @Override
                public void onLoaded(Bitmap bitmap) {

                    ivFoto.setImageBitmap(bitmap);
                }
            }).execute(person.getFoto());
            tvNome.setText(person.getNome());
            tvId.setText(String.valueOf(person.getId()));
            tvUsername.setText(person.getUsername());
        }
    }
}
