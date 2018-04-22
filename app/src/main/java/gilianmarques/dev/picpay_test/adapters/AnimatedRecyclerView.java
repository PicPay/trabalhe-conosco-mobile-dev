package gilianmarques.dev.picpay_test.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import gilianmarques.dev.picpay_test.utils.AppPatterns;
import gilianmarques.dev.picpay_test.utils.MyApp;


/**
 * Created by Gilian Marques on 21/04/2018.
 *
 */

  class AnimatedRecyclerView<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    private int mLastPosition = -1, viewExibivesPorVez = -1;

    @NonNull
    @Override
    public T onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull T holder, int position) {
        if (viewExibivesPorVez == -1) {
            DisplayMetrics metrics = MyApp.getContext().getResources().getDisplayMetrics();
            int viewSize = (int) AppPatterns.convertDpToPx(70);
            int tamTelaAproveitavel = metrics.heightPixels;
            viewExibivesPorVez = tamTelaAproveitavel / viewSize;
        }

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    private void setAnimation(View viewToAnimate, int position) {
        if (position > mLastPosition) {
            AlphaAnimation mAnimation;

            mAnimation = new AlphaAnimation(0f, 1f);
            mAnimation.setDuration(200);
            //
            if (position < viewExibivesPorVez) mAnimation.setStartOffset(position * 50);
            else mAnimation.setStartOffset(15);
            //
            viewToAnimate.startAnimation(mAnimation);
            mLastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull T holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }

    void enableAnimations() {
        mLastPosition = -1;
    }

}