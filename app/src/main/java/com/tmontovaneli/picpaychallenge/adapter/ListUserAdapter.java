package com.tmontovaneli.picpaychallenge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.tmontovaneli.picpaychallenge.R;
import com.tmontovaneli.picpaychallenge.model.User;
import com.tmontovaneli.picpaychallenge.util.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmontovaneli on 21/08/17.
 */

public class ListUserAdapter extends BaseAdapter implements Filterable {

    private final Context context;
    private List<User> users;
    private List<User> usersBackup;
    private ViewHolder holder;

    public ListUserAdapter(Context context, List<User> users) {
        this.users = users;
        this.usersBackup = users;
        this.context = context;

    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ((User) getItem(i)).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);

        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.ly_item_user, viewGroup, false);
            holder = new ViewHolder();
            holder.name = view.findViewById(R.id.name);
            holder.nickName = view.findViewById(R.id.nick_name);
            holder.image = view.findViewById(R.id.img_user);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        AQuery aq = new AQuery(view);


        holder.position = i;

        User item = users.get(i);
        aq.id(holder.image).image(item.getImg(), true, true, 0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);

        holder.name.setText(item.getName());
        holder.nickName.setText(item.getUsername());

        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence texto) {
                FilterResults result = new FilterResults();
                if (texto == null || texto.length() == 0) {
                    result.values = usersBackup;
                    return result;
                }


                List<User> userTemp = new ArrayList<User>();


                if (!StringHelper.isEmpty(texto.toString())) {
                    String key = texto.toString().toUpperCase();
                    for (User user : usersBackup) {
                        if (user.getName().toUpperCase().contains(key)
                                || user.getUsername().toUpperCase().contains(key)) {
                            userTemp.add(user);
                        }
                    }
                }


                result.values = userTemp;
                return result;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                users = (List<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    private static class ViewHolder {
        public TextView name;
        public TextView nickName;
        public ImageView image;
        public int position;
    }


}
