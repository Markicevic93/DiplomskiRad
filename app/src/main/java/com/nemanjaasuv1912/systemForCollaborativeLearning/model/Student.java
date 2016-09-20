package com.nemanjaasuv1912.systemForCollaborativeLearning.model;

import com.nemanjaasuv1912.systemForCollaborativeLearning.model.base.ModelKeys;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nemanjamarkicevic on 8/8/16.
 */
public class Student implements ModelKeys {

    public static Student sharedStudent;

    private int id;
    private String username;
    private String password;
    private String fullname;
    private String universityName;
    private int year;
    private String birthdate;
    private String aboutMe;
    private String email;

    public static void parse(String jsonString) {
        Student.sharedStudent = new Student(jsonString);
    }

    public Student(int id, String username) {
        this.id = id;
        this.username = username;
        fullname = "";
        email = "";
        birthdate = "";
        aboutMe = "";
        year = 0;
        universityName = "";
    }

    public Student(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            id = jsonObject.getInt(ID_KEY);
            username = jsonObject.getString(USERNAME_KEY);
            fullname = jsonObject.getString(FULLNAME_KEY);
            email = jsonObject.getString(EMAIL_KEY);
            birthdate = jsonObject.getString(BIRTHDATE_KEY);
            aboutMe = jsonObject.getString(ABOUT_KEY);
            year = jsonObject.getInt(YEAR_KEY);

            JSONObject universityJsonObject = jsonObject.getJSONObject(UNIVERSITY_KEY);
            universityName = universityJsonObject.getString(University.NAME_KEY);

            Student.sharedStudent = this;

        } catch (JSONException ignored) {
        }
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public int getYear() {
        return year;
    }

    public String getYearAsString() {
        return year + "";
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setBirthdate(String birtday) {
        this.birthdate = birtday;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public int getId() {
        return id;
    }

    public String getUniversityName() {
        return universityName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
