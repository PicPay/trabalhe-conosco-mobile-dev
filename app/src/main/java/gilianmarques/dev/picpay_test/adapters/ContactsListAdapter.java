package gilianmarques.dev.picpay_test.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.asyncs.ProfilePictureUtils;
import gilianmarques.dev.picpay_test.models.Contact;

public class ContactsListAdapter extends AnimatedRecyclerView {
    private final List<Contact> mContacts;
    private final ItemClickCallback mItemClickCallback;
    private final Context mContext;

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

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        final Contact mContact = mContacts.get(position);

        myViewHolder.tvNome.setText(mContact.getName());
        myViewHolder.tvId.setText(String.format(Locale.getDefault(), mContext.getString(R.string.id), mContact.getId()));
        myViewHolder.tvUsername.setText(mContact.getUserName());

        ProfilePictureUtils.getPicAsync(mContact, new ProfilePictureUtils.Callback() {
            @Override
            public void result(Drawable mDrawable) {
                myViewHolder.ivProfileImage.setImageDrawable(mDrawable);
            }
        });

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickCallback.onContactClick(mContact);
            }
        });

        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView tvNome, tvId,tvUsername;
        final ImageView ivProfileImage;

        MyViewHolder(View itemView) {
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
