package mobiledev.erickgomes.picpayapp.fragments;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import mobiledev.erickgomes.picpayapp.R;
import mobiledev.erickgomes.picpayapp.database.AppDatabase;
import mobiledev.erickgomes.picpayapp.models.CreditCard;
import mobiledev.erickgomes.picpayapp.utils.MaskEditText;
import mobiledev.erickgomes.picpayapp.utils.Validation;

/**
 * Created by erickgomes on 24/03/2018.
 */

public class FragAddCreditcard extends Fragment {

    private TextInputLayout mTiCardNumber, mTiOwnerCard, mTiCvv, mTiExpiration;
    private EditText mEtCardNumber, mEtOwnerCard, mEtCvv, mEtExpiration;
    private Button mBtnRegisterCard;
    private AppDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_creditcard, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceStance) {
        super.onActivityCreated(savedInstanceStance);
        initConfigs();
        initDatabase();
    }


    private void initViews(View view) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.adicionar_cartao); // muda o titulo da toolbar da Activity

        mTiCardNumber = view.findViewById(R.id.ti_card_number);
        mTiOwnerCard = view.findViewById(R.id.ti_card_owner);
        mTiCvv = view.findViewById(R.id.ti_cvv);
        mTiExpiration = view.findViewById(R.id.ti_expiration_date);

        mEtCardNumber = view.findViewById(R.id.et_card_number);
        mEtOwnerCard = view.findViewById(R.id.et_card_owner);
        mEtCvv = view.findViewById(R.id.et_cvv);
        mEtExpiration = view.findViewById(R.id.et_expiration_date);

        mBtnRegisterCard = view.findViewById(R.id.btn_register_card);
    }

    private void initListeners() {
        mBtnRegisterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTiCardNumber.setError(null);
                mTiOwnerCard.setError(null);
                mTiCvv.setError(null);
                mTiExpiration.setError(null);

                registerCreditCard();
            }
        });
    }


    private void initConfigs() {
        MaskEditText maskExpiration = new MaskEditText("##/##", mEtExpiration); //mascara para o formato de data de validade
        mEtExpiration.addTextChangedListener(maskExpiration);
    }

    private void initDatabase() {
        db = Room.databaseBuilder(getActivity(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();

    }


    private void registerCreditCard() {
        int err = 0;
        String CardNumber = mEtCardNumber.getText().toString();
        String OwnerCard = mEtOwnerCard.getText().toString();
        String Cvv = mEtCvv.getText().toString();
        String Expiration = mEtExpiration.getText().toString();

        if (!Validation.validateCardNumber(CardNumber)) {
            err++;
            mTiCardNumber.setError(getString(R.string.error_card_number));
        }

        if (!Validation.validateEmptyField(OwnerCard)) {
            err++;
            mTiOwnerCard.setError(getString(R.string.error_owner_card));
        }


        if (!Validation.validateCvv(Cvv)) {
            err++;
            mTiCvv.setError(getString(R.string.error_cvv));
        }

        if (!Validation.validateExpiration(Expiration)) {
            err++;
            mTiExpiration.setError(getString(R.string.error_expiration));
        }

        if (err == 0) { // Se err = 0, tudo foi preenchido corretamente, logo, cria-se o objeto creditCard
            CreditCard newCreditCard = new CreditCard();
            newCreditCard.setCard_number(CardNumber);
            newCreditCard.setCard_owner(OwnerCard);
            newCreditCard.setCvv(Integer.parseInt(Cvv));
            newCreditCard.setExpiry_date(Expiration);
            saveToDatabase(newCreditCard);
        }
    }

    private void saveToDatabase(CreditCard newCreditCard) {
        db.creditCardDao().insertCreditCard(newCreditCard); // salva no banco de dados
        getFragmentManager().popBackStack(); //volta para o fragmento anterior
    }

    @Override
    public void onDetach() {
        super.onDetach();
        db.close();
    }

}