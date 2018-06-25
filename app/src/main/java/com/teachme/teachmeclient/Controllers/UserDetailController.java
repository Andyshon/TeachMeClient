package com.teachme.teachmeclient.Controllers;

import android.content.Context;

import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Models.UserDetailModel;
import com.teachme.teachmeclient.Entities.User;

/**
 * Created by andyshon on 23.01.18.
 */

public class UserDetailController {
    private UserDetailModel model;

    public UserDetailController(Context context) {
        model = new UserDetailModel(context);
    }

    public void getUserDetail(DatabaseQuery callback, String _User_id) {
        model.getUserDetail(new DatabaseQuery() {
            @Override
            public void getUserResult(User user) {
                callback.getUserResult(user);
            }
        }, _User_id);
    }

    public void deleteUserById(DatabaseQuery callback, String _User_id) {
        model.deleteUserById(new DatabaseQuery() {
            @Override
            public void getDeletedUserResult() {
                callback.getDeletedUserResult();
            }
        }, _User_id);
    }

    public void addUser(DatabaseQuery callback, User user) {
        model.addUser(new DatabaseQuery() {
            @Override
            public void getAddUserResult() {
                callback.getAddUserResult();
            }
        }, user);
    }

    public void updateUserById(DatabaseQuery callback, User user, String _User_id) {
        model.updateUserBuId(new DatabaseQuery() {
            @Override
            public void getUpdateUserByIdResult() {
                callback.getUpdateUserByIdResult();
            }
        }, user, _User_id);
    }
}
