package com.teachme.teachmeclient.Entities;

/**
 * Created by andyshon on 27.02.2018.
 */

public class CachedUser {
    private String id;
    private String userName;
    private String authToken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
