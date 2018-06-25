package com.teachme.teachmeclient.Models;

import android.content.Context;
import android.widget.Toast;

import com.teachme.teachmeclient.Interfaces.BackendService;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.RestClient;
import com.teachme.teachmeclient.Entities.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andyshon on 04.12.17.
 */

public class UserDetailModel {
    private Context context;
    private BackendService restClient;

    public UserDetailModel(Context context) {
        this.context = context;
        restClient = RestClient.getService();
    }

    public void getUserDetail(DatabaseQuery callback, String _User_Id) {
        Call<User> call = restClient.getUserById(_User_Id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    callback.getUserResult(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteUserById(DatabaseQuery callback, String _User_id) {
        Call<Void> call = restClient.deleteUserById(_User_id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "User deleted", Toast.LENGTH_LONG).show();
                    callback.getDeletedUserResult();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addUser(DatabaseQuery callback, User user) {
        restClient.addUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 201) {
                    Toast.makeText(context, "New user registered", Toast.LENGTH_LONG).show();
                    callback.getAddUserResult();
                } else {
                    Toast.makeText(context, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateUserBuId(DatabaseQuery callback, User user, String _User_id) {
        Call<User> call = restClient.getUserById(_User_id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User existingUser = response.body();
                existingUser.setEmail(user.getEmail());
                existingUser.setFullName(user.getFullName());
                existingUser.setLogin(user.getLogin());
                System.out.println(existingUser.getFullName() + "    " + existingUser.getEmail());

                // Use Backend API to update user
                restClient.updateUserById(_User_id, existingUser).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "User updated", Toast.LENGTH_LONG).show();
                            callback.getUpdateUserByIdResult();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
