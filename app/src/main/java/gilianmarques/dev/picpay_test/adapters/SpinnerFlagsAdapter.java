package gilianmarques.dev.picpay_test.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.utils.MyApp;


public class SpinnerFlagsAdapter extends BaseAdapter {

    private ArrayList<String> mFlags;

    public SpinnerFlagsAdapter(ArrayList<String> mFlags) {
        this.mFlags = mFlags;
    }

    @Override
    public int getCount() {
        return mFlags.size();
    }

    @Override
    public Object getItem(int position) {
        return mFlags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(MyApp.getContext(), R.layout.view_flag, null);
        }

        TextView tvName = convertView.findViewById(R.id.tv_name);
        ImageView iv = convertView.findViewById(R.id.imageView);

        tvName.setText(mFlags.get(position));
        iv.setImageResource(R.drawable.vec_credit_card);

        if (position == 0) {

           iv.setVisibility(View.GONE);
            tvName.setTextColor(ContextCompat.getColor(MyApp.getContext(), R.color.text_secundary));

        } else iv.setVisibility(View.VISIBLE);

        return convertView;
    }
}
