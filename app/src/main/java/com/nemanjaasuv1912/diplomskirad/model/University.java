package com.nemanjaasuv1912.diplomskirad.model;

import com.nemanjaasuv1912.diplomskirad.model.base.modelKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class University implements modelKeys {

    public static University sharedUniversity;

    private int id;
    private String name;
    private ArrayList<Group> groups;

    public static void parse(String jsonString) {
        University.sharedUniversity = new University(jsonString);
    }

    public University(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            id = jsonObject.getInt(ID_KEY);
            name = jsonObject.getString(NAME_KEY);
            groups = new ArrayList<>();
        } catch (JSONException ignored) {
        }

        University.sharedUniversity = this;
    }

    public void parseSubjects(String jsonString) {
        groups = new ArrayList<>();

        try {
            JSONArray jsonarray = new JSONArray(jsonString);

            for (int i = 0; i < jsonarray.length(); i++) {
                addSubject(Group.parse(jsonarray.getJSONObject(i).toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Collections.sort(groups, new Comparator<Group>() {
            @Override
            public int compare(Group lhs, Group rhs) {
                if (lhs.getYear() == rhs.getYear()) {
                    return 0;
                } else if (lhs.getYear() < rhs.getYear()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }

    public ArrayList<Group> getGroup() {
        return groups;
    }

    public void addSubject(Group group) {
        this.groups.add(group);
    }

    public ArrayList<Group> getSelectedGroups() {
        ArrayList<Group> selectedGroups = new ArrayList<>();

        for (Group group : groups) {
            if (group.isSelected()) {
                selectedGroups.add(group);
            }
        }

        return selectedGroups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Group getGroup(int groupId) {
        for (Group group : groups) {
            if (group.getId() == groupId) {
                return group;
            }
        }

        return null;
    }
}
