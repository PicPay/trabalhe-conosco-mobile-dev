package gilianmarques.dev.picpay_test.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.models.Contact;
import gilianmarques.dev.picpay_test.asyncs.RoundImageAsync;

public class ContactsListAdapter extends AnimatedRecyclerView {
    private List<Contact> mContacts;
    private ItemClickCallback mItemClickCallback;
    private Context mContext;

    public ContactsListAdapter(Context mContext, List<Contact> mContacts, ItemClickCallback mItemClickCallback) {
        this.mContacts = mContacts;
        this.mItemClickCallback = mItemClickCallback;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.item_contact, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        final Contact mContact = mContacts.get(position);
        myViewHolder.tvNome.setText(mContact.getName());
        myViewHolder.tvId.setText(String.format(Locale.getDefault(), mContext.getString(R.string.id), mContact.getId()));
        myViewHolder.tvUsername.setText(mContact.getUserName());
        myViewHolder.ivProfileImage.setImageDrawable(mContact.getPhotoDrawable());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickCallback.onContactClick(mContact);
            }
        });


        /*anima as views um vez por view*/
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNome, tvId, tvUsername;
        public ImageView ivProfileImage;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tv_name);
            tvId = itemView.findViewById(R.id.tv_id);
            tvUsername = itemView.findViewById(R.id.tv_user_name);
            ivProfileImage = itemView.findViewById(R.id.iv_profile_image);
        }
    }


    /**
     * Ajuda a detectar  o clique na view do contato selecionado
     */
    public interface ItemClickCallback {

        void onContactClick(Contact mContact);
    }
}
