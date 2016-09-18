package com.nemanjaasuv1912.systemForCollaborativeLearning.model;

import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.sharedPreferences.StudentSharedPref;
import com.nemanjaasuv1912.systemForCollaborativeLearning.model.base.modelKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class Group implements modelKeys {

    private int id;
    private int year;
    private String name;
    private String shortName;
    private ArrayList<Post> posts;

    public static Group parse(String jsonString) {
        return new Group(jsonString);
    }

    public void parsePosts(String jsonString) {
        posts = new ArrayList<>();

        try {
            JSONArray jsonarray = new JSONArray(jsonString);

            for (int i = 0; i < jsonarray.length(); i++) {
                addPost(Post.parse(jsonarray.getJSONObject(i).toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Collections.sort(posts, new Comparator<Post>() {
            @Override
            public int compare(Post lhs, Post rhs) {
                return lhs.getUpdated().compareTo(rhs.getUpdated());
            }
        });
    }

    private Group(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            id = jsonObject.getInt(ID_KEY);
            year = jsonObject.getInt(YEAR_KEY);
            name = jsonObject.getString(NAME_KEY);
            shortName = jsonObject.getString(SHORT_NAME_KEY);
            posts = new ArrayList<>();

        } catch (JSONException ignored) {
        }
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return StudentSharedPref.getIsSubjectSelected(id + "");
    }

    public void setSelected(boolean selected) {
        StudentSharedPref.putIsSubjectSelected(id + "", selected);
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public int getYear() {
        return year;
    }

    public String getYearAsString() {
        return year + "";
    }

    public int getId() {
        return id;
    }

    public Post getPost(int postId) {
        for (Post post : posts) {
            if (post.getId() == postId) {
                return post;
            }
        }

        return null;
    }
}
