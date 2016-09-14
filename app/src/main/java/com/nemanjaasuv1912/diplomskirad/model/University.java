package com.nemanjaasuv1912.diplomskirad.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class University {

    public static University sharedUniversity;

    public static final String ID_KEY = "id";
    public static final String NAME_KEY = "name";
    public static final String EMAIL_SUFIX_KEY = "email_sufix";
    public static final String IMAGE_URL_KEY = "image_url";
    public static final String ADDRESS_KEY = "address";

    private int id;
    private String name;
    private ArrayList<Group> groups;
    private String emailSufix;
    private String imageUrl;
    private String address;


    public static void parse(String jsonString) {
        University.sharedUniversity = new University(jsonString);
    }

    public University(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            id          = jsonObject.getInt(ID_KEY);
            name        = jsonObject.getString(NAME_KEY);
            emailSufix  = jsonObject.getString(EMAIL_SUFIX_KEY);
            imageUrl    = jsonObject.getString(IMAGE_URL_KEY);
            address     = jsonObject.getString(ADDRESS_KEY);
            groups = new ArrayList<>();
        } catch (JSONException ignored) {}

        University.sharedUniversity = this;
    }

    public void parseSubjects(String jsonString){
        groups = new ArrayList<>();

        try {
            JSONArray jsonarray = new JSONArray(jsonString);

            for (int i = 0; i < jsonarray.length(); i++) {
                addSubject(Group.parse(jsonarray.getJSONObject(i).toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Collections.sort(groups, new Comparator<Group>(){
            @Override
            public int compare(Group lhs, Group rhs) {
                if(lhs.getYear() == rhs.getYear()){
                    return  0;
                }else if(lhs.getYear() < rhs.getYear()){
                    return  -1;
                }else{
                    return  1;
                }
            }
        });
    }

    public ArrayList<Group> getGroup() {
        return groups;
    }

    public void addSubject(Group group){
        this.groups.add(group);
    }

    public ArrayList<Group> getSelectedGroups() {
        ArrayList<Group> selectedGroups = new ArrayList<>();

        for(Group group : groups){
            if(group.isSelected()){
                selectedGroups.add(group);
            }
        }

        return selectedGroups;
    }

    public String getName() {
        return name;
    }

    public String getEmailSufix() {
        return emailSufix;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Group getGroup(int groupId) {
        for (Group group : groups){
            if(group.getId() == groupId){
                return group;
            }
        }

        return null;
    }
}
