package com.nemanjaasuv1912.diplomskirad.model;

import io.realm.RealmObject;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class Comment extends RealmObject {

    private String text;
    private String username;
    private String imageUrl;

    public Comment(){
        this.text = "";
        this.username = "";
        this.imageUrl = "";
    }

    public Comment(String text, String username, String imageUrl){
        this.text = text;
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public Comment(Comment item){
        this.text = item.getText();
        this.username = item.getUsername();
        this.imageUrl = item.getImageUrl();
    }

    public String getText() {
        return text;
    }

    public String getUsername() {
        return username;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
