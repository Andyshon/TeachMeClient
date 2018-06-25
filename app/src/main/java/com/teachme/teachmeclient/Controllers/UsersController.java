package com.teachme.teachmeclient.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Models.UsersModel;
import com.teachme.teachmeclient.R;
import com.teachme.teachmeclient.Entities.User;

import java.util.List;

/**
 * Created by andyshon on 23.01.18.
 */

public class UsersController extends ArrayAdapter<User>{

    private UsersModel model;

    public UsersController(Context context){
        super(context, 0);
        model = new UsersModel(context);
    }

    public void getUsers(DatabaseQuery callback, boolean isOnlyTeachers) {
        model.getUsers(new DatabaseQuery() {
            @Override
            public void getUsersResult(List<User> teachers) {
                callback.getUsersResult(teachers);
            }
        }, isOnlyTeachers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.entry_item, parent, false);

        User user = getItem(position);
        TextView tvId = convertView.findViewById(R.id.user_id);
        tvId.setText(user != null ? user.getId() : "");
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(user != null ? user.getFullName() : "");

        return convertView;
    }
}
