package com.teachme.teachmeclient.Models;

import android.content.Context;
import android.widget.Toast;

import com.teachme.teachmeclient.Interfaces.BackendService;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.RestClient;
import com.teachme.teachmeclient.Entities.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andyshon on 04.12.17.
 */

public class UsersModel {
    private Context context;
    private BackendService restClient;

    public UsersModel(Context context) {
        this.context = context;
        restClient = RestClient.getService();
    }

    public void getUsers(DatabaseQuery callback, boolean isOnlyTeachers) {
        Call<List<User>> call = restClient.getUser();
        call.enqueue(new Callback<List<User>>() {
                         @Override
                         public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                             List<User> userList = new ArrayList<>();
                             for (int i = 0; i < response.body().size(); i++) {
                                 GlobalConstants.UserRole userRole = response.body().get(i).getUserRole();
                                 User user = new User();
                                 user.setId(String.valueOf(response.body().get(i).getId()));
                                 user.setFullName(String.valueOf(response.body().get(i).getFullName()));
                                 if (isOnlyTeachers) {
                                     if (userRole == GlobalConstants.UserRole.TEACHER) {
                                         userList.add(user);
                                     }
                                 }
                                 else {
                                     userList.add(user);
                                 }
                             }
                             callback.getUsersResult(userList);
                         }

                         @Override
                         public void onFailure(Call<List<User>> call, Throwable t) {
                             Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }
}
