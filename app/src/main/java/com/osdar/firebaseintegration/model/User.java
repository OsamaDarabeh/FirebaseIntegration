package com.osdar.firebaseintegration.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Osama on 4/5/2017 --- 4:26 PM.
 */

@IgnoreExtraProperties
public class User {

    public String username;
    public String password;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return username + " " + password;
    }
}