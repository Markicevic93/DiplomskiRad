package com.nemanjaasuv1912.systemForCollaborativeLearning.helper.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.nemanjaasuv1912.systemForCollaborativeLearning.MyApplication;
import com.nemanjaasuv1912.systemForCollaborativeLearning.model.Student;

/**
 * Created by nemanjamarkicevic on 9/11/16.
 */
public class StudentSharedPref {

    protected static SharedPreferences getSharedPref() {
        return MyApplication.getContext().getSharedPreferences(Student.sharedStudent.getEmail(), Context.MODE_PRIVATE);
    }

    public static Boolean getIsSubjectSelected(String groupId) {
        return getSharedPref().getBoolean(groupId, false);
    }

    public static void putIsSubjectSelected(String groupId, boolean isSelected) {
        SharedPreferences.Editor editor = getSharedPref().edit();
        editor.putBoolean(groupId, isSelected);
        editor.apply();
    }
}
