package br.com.picpay.picpay.viewholder;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

public abstract class ViewHolderBind<M> extends RecyclerView.ViewHolder {

    public ViewHolderBind(ViewGroup parent, @NonNull @LayoutRes Integer layout) {
        super(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    public abstract void onBindViewHolder(final M model, int position);

    public void post(Object event) {
        EventBus.getDefault().post(event);
    }
}