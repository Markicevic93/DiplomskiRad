package com.nemanjaasuv1912.diplomskirad.model;

import com.nemanjaasuv1912.diplomskirad.model.base.modelKeys;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class Comment implements modelKeys {

    private int id;
    private String text;
    private Student student;
    private Calendar created;
    private Calendar updated;

    public static Comment parse(String jsonString) {
        return new Comment(jsonString);
    }

    public Comment(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            id = jsonObject.getInt(ID_KEY);
            text = jsonObject.getString(TEXT_KEY);
            updated = Calendar.getInstance();
            created = Calendar.getInstance();
            updated.setTimeInMillis(TimeUnit.SECONDS.toMillis(jsonObject.getInt(DATE_UPADTED_KEY)));
            created.setTimeInMillis(TimeUnit.SECONDS.toMillis(jsonObject.getInt(DATE_CREATED_KEY)));

            JSONObject studentJsonObject = jsonObject.getJSONObject(STUDENT_KEY);
            student = new Student(studentJsonObject.getInt(ID_KEY), studentJsonObject.getString(USERNAME_KEY));

        } catch (JSONException ignored) {
        }
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Calendar getCreated() {
        return created;
    }

    public Calendar getUpdated() {
        return updated;
    }

    public String getUpdatedDate() {
        return new SimpleDateFormat("dd-MM-yy").format(updated.getTime());
    }

    public String getUpdatedTime() {
        return new SimpleDateFormat("hh:mm").format(updated.getTime());
    }
}
