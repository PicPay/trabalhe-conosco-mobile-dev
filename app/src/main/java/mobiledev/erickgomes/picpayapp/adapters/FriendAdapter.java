package mobiledev.erickgomes.picpayapp.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mobiledev.erickgomes.picpayapp.Payment;
import mobiledev.erickgomes.picpayapp.R;
import mobiledev.erickgomes.picpayapp.models.Friend;
import mobiledev.erickgomes.picpayapp.utils.MoneyTextWatcher;
import mobiledev.erickgomes.picpayapp.utils.Validation;


public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private List<Friend> mAndroidList;
    private Context context;
    private Dialog specifyAmount;

    public FriendAdapter(Context context) {
        this.context = context;
        this.mAndroidList = Collections.EMPTY_LIST; //Inicio um fragmnet com uma lista vazia
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Friend friendItem = mAndroidList.get(position);            //Preenchimento basico do friend_item através dos itens obtidos pela lista

        holder.mFriendName.setText(friendItem.getName());
        holder.mFriendUsername.setText(friendItem.getUserName());
        Picasso.with(context).load(friendItem.getImg()).into(holder.mFriendPerfilImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(friendItem); //Ao clicar, é aberta uma custom pop up com os dados do amigo e uma editText para inserir o valor
            }
        });

    }

    private void showDialog(Friend friendSelected) {
        CircleImageView mDialogPerfilImg;
        TextView mDialogName, mDialogUserName;
        EditText mEtAmount;
        Button btnConfirmar;

        specifyAmount = new Dialog(context);
        specifyAmount.setContentView(R.layout.dialog_specify_value);
        mDialogPerfilImg = specifyAmount.findViewById(R.id.dialog_friend_perfil_img);
        mDialogName = specifyAmount.findViewById(R.id.dialog_friend_name);
        mDialogUserName = specifyAmount.findViewById(R.id.dialog_friend_username);
        mEtAmount = specifyAmount.findViewById(R.id.et_amount);
        btnConfirmar = specifyAmount.findViewById(R.id.btn_confirmar);

        Picasso.with(context).load(friendSelected.getImg()).into(mDialogPerfilImg);
        mDialogName.setText(friendSelected.getName());
        mDialogUserName.setText(friendSelected.getUserName());


        mEtAmount.addTextChangedListener(new MoneyTextWatcher(mEtAmount));


        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation.validateEmptyField(mEtAmount.getText().toString()))
                    goToPayment(friendSelected, mEtAmount.getText().toString());
            }
        });
        specifyAmount.show();
    }

    private void goToPayment(Friend friend, String amountSelected) { // aqui mando os dados do amigo, e o valor selecionado para a activity payment
        Intent i = new Intent(context, Payment.class);
        Bundle extras = new Bundle();
        extras.putParcelable("friendSelected", friend);
        extras.putString("amountSelected", amountSelected);
        i.putExtras(extras);
        context.startActivity(i);
    }


    @Override
    public int getItemCount() {
        return mAndroidList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mFriendName, mFriendUsername;
        private EditText mEtAmount;
        private CircleImageView mFriendPerfilImg;

        private ViewHolder(View view) {
            super(view);

            mFriendName = view.findViewById(R.id.friend_name);
            mFriendUsername = view.findViewById(R.id.friend_username);
            mFriendPerfilImg = view.findViewById(R.id.friend_perfil_img);

        }

    }

    public void setList(List<Friend> friends) {
        this.mAndroidList = friends;
        notifyDataSetChanged();
    }

    public void addItem(Friend friend) {
        mAndroidList.add(0, friend);
        notifyItemInserted(0);
    }

    public List<Friend> getList() {
        return mAndroidList;
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}

