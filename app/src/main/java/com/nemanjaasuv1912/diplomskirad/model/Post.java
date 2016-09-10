package com.nemanjaasuv1912.diplomskirad.model;

import android.util.Log;

import com.nemanjaasuv1912.diplomskirad.model.base.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class Post extends Model<Post> {

    private int id;
    private String title;
    private String text;
    private Student student;
    private Calendar created;
    private Calendar updated;
    private ArrayList<Comment> comments;

    public static Post parse(String jsonString) {
        return new Post(jsonString);
    }

    public void parseComments(String jsonString) {
        comments = new ArrayList<>();

        try {
            JSONArray jsonarray = new JSONArray(jsonString);

            for (int i = 0; i < jsonarray.length(); i++) {
                addComment(Comment.parse(jsonarray.getJSONObject(i).toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Collections.sort(comments, new Comparator<Comment>(){
            @Override
            public int compare(Comment lhs, Comment rhs) {
                return lhs.getUpdated().compareTo(rhs.getUpdated());
            }
        });
    }

    public Post(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            id          = jsonObject.getInt(ID_KEY);
            title       = jsonObject.getString(TITLE_KEY);
            text        = jsonObject.getString(TEXT_KEY);
            updated     = Calendar.getInstance();
            created     = Calendar.getInstance();
            updated.setTimeInMillis(TimeUnit.SECONDS.toMillis(jsonObject.getInt(DATE_UPADTED_KEY)));
            created.setTimeInMillis(TimeUnit.SECONDS.toMillis(jsonObject.getInt(DATE_CREATED_KEY)));
            comments    = new ArrayList<>();

            JSONObject studentJsonObject = jsonObject.getJSONObject(STUDENT_KEY);
            student = new Student(studentJsonObject.getInt(ID_KEY),studentJsonObject.getString(USERNAME_KEY));

        } catch (JSONException ignored) {}
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(0, comment);
    }

    public Calendar getUpdated() {
        return updated;
    }

    public int getId() {
        return id;
    }
}
