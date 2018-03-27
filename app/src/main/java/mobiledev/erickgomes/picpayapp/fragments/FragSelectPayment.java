package mobiledev.erickgomes.picpayapp.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import mobiledev.erickgomes.picpayapp.FriendList;
import mobiledev.erickgomes.picpayapp.Payment;
import mobiledev.erickgomes.picpayapp.R;
import mobiledev.erickgomes.picpayapp.adapters.CreditCardAdapter;
import mobiledev.erickgomes.picpayapp.common.Common;
import mobiledev.erickgomes.picpayapp.database.AppDatabase;
import mobiledev.erickgomes.picpayapp.models.CreditCard;
import mobiledev.erickgomes.picpayapp.models.Friend;
import mobiledev.erickgomes.picpayapp.models.TransactionResponse;
import mobiledev.erickgomes.picpayapp.models.TransactionSender;
import mobiledev.erickgomes.picpayapp.utils.DividerItemDecoration;
import mobiledev.erickgomes.picpayapp.utils.NetworkUtil;

/**
 * Created by erickgomes on 25/03/2018.
 */

public class FragSelectPayment extends Fragment {

    public static final String TAG = FragSelectPayment.class.getSimpleName();
    private TextView tvFriendName, tvFriendusername, tvAmountToSend;
    private CircleImageView mFriendPerfilImage;
    private CardView mCardAddCreditcard;
    private CompositeDisposable mSubscriptions;
    private Button btnPay;
    private FrameLayout contentLoading;
    private CreditCardAdapter mCreditCardAdapter;
    private AppDatabase db;
    private Friend friendSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_payment, container, false);
        initViews(view);
        initListeners();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceStance) {
        super.onActivityCreated(savedInstanceStance);
        initConfigs();

    }


    private void initListeners() {
        mCardAddCreditcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddCreditCard();
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareTransaction();
            }
        });
    }

    private void prepareTransaction() {
        if (Common.selectedCreditCard == null) // Se for null, nenhum cartão de crédito foi selecionado
            showSnackBarMessage("Selecione um cartão de crédito para pagamento");
        else {
            String amountString = tvAmountToSend.getText().toString().replaceAll("[R$\\s()-]", ""); //Retira os caracteres especiais da STRING
            double amontToSend = Double.parseDouble(amountString);       //Até a linha 107, simplismente crio um objeto transacão e
            CreditCard selectedCard = Common.selectedCreditCard;         // envio um POST para a API
            TransactionSender mSender = new TransactionSender();
            mSender.setCard_number(selectedCard.getCard_number());
            mSender.setCvv(selectedCard.getCvv());
            mSender.setValue(amontToSend);
            mSender.setExpiry_date(selectedCard.getExpiry_date());
            mSender.setDestination_user_id(friendSelected.getId());
            sendPayment(mSender);
            contentLoading.setVisibility(View.VISIBLE);
        }

    }

    private void sendPayment(TransactionSender sender) {
         mSubscriptions = new CompositeDisposable();

        mSubscriptions.add(NetworkUtil.getRetrofit().sendPayment(sender)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(TransactionResponse response) {
        contentLoading.setVisibility(View.GONE);

        if (response.getTransaction().isSuccess()) {
            showSnackBarMessage("Transferência completa com sucesso!"); //Se o campo sucess do objeto retornado == true,
            Common.selectedCreditCard = null;                           //transação completa com sucesso, volta para a lista de amigos
            goToFriendList();
        } else {
            showSnackBarMessage("Transferência não concluída, revise o modo de pagamento"); // Caso contrario, pede para inserir um novo metodo de pagamento
        }

    }

    private void goToFriendList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Transação Concluída!")
                .setMessage("Transacão concluída com sucesso, você será redirecionado")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), FriendList.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void handleError(Throwable error) {
        contentLoading.setVisibility(View.GONE);

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                showSnackBarMessage(errorBody);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Erro de conexão!");
        }
    }


    private void goToAddCreditCard() {
        FragAddCreditcard mAddCreditCard = new FragAddCreditcard();
        getFragmentManager().beginTransaction()
                .replace(R.id.layout_container, mAddCreditCard, FragFriendList.TAG)
                .addToBackStack(null)
                .commit();
    }

    private void initViews(View view) {


        ((Payment) getActivity()).getSupportActionBar().setTitle(R.string.new_transaction); //Muda o titulo da toolbar da Activity


        tvFriendName = view.findViewById(R.id.friend_name);
        tvFriendusername = view.findViewById(R.id.friend_username);
        tvAmountToSend = view.findViewById(R.id.amount_to_send);
        mFriendPerfilImage = view.findViewById(R.id.friend_perfil_img);
        btnPay = view.findViewById(R.id.btn_pay);
        contentLoading = view.findViewById(R.id.content_loading);
        mCardAddCreditcard = view.findViewById(R.id.card_add_creditcard);
        RecyclerView mRecyclerView = view.findViewById(R.id.creditcard_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mCreditCardAdapter = new CreditCardAdapter(getActivity());
        mRecyclerView.setAdapter(mCreditCardAdapter);
    }

    private void initConfigs() {

        if (getArguments() != null) { // Aqui resgato os dados do amigo selecionado para enviar a transação, verificação feita somente por segurança
            Bundle extras = getArguments();
            friendSelected = extras != null ? extras.getParcelable("friendSelected") : null;
            Picasso.with(getActivity()).load(friendSelected != null ? friendSelected.getImg() : null).into(mFriendPerfilImage);
            preencheTextViews(friendSelected, extras); //preenche as texts views com os dados do amigo.
        }

        initDatabase(); // inicia o banco de dados
        getCreditCards(); // apos iniciado, resgato os cartoes de credito salvo do banco de dados local.
    }

    private void getCreditCards() {
        mCreditCardAdapter.setList(db.creditCardDao().getAllCreditCards());
    }

    private void initDatabase() {
            db = Room.databaseBuilder(getActivity(), AppDatabase.class, "production")
                    .allowMainThreadQueries()
                    .build();

    }

    private void preencheTextViews(Friend friendSelected, Bundle extras) {
        tvFriendName.setText(friendSelected.getName());
        tvFriendusername.setText(friendSelected.getUserName());
        tvAmountToSend.setText(String.format("R$ %s", extras.getString("amountSelected")));
    }

    private void showSnackBarMessage(String message) {
        if (getActivity() == null) {
            return;
        }
        Snackbar.make(getActivity().findViewById(R.id.activity_payment), message, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        db.close();
    }

    @Override
    public void onPause(){
        super.onPause();
        if(mSubscriptions != null){
            mSubscriptions.dispose();
        }
    }

}
