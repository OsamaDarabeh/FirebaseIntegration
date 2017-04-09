package com.osdar.firebaseintegration.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Osama on 4/6/2017 --- 2:24 PM.
 */

// [START comment_class]
@IgnoreExtraProperties
public class Comment {

    public String uid;
    public String author;
    public String text;

    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Comment(String uid, String author, String text) {
        this.uid = uid;
        this.author = author;
        this.text = text;
    }

}
