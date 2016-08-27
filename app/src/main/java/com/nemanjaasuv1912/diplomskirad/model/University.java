package com.nemanjaasuv1912.diplomskirad.model;

import com.nemanjaasuv1912.diplomskirad.helper.MyRealm;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class University extends RealmObject {

    @PrimaryKey
    private String name;
    private RealmList<Subject> subjects;

    public University(){
        this.name = "";
        this.subjects = new RealmList<>();
    }

    public University(String name) {
        this.name = name;
        this.subjects = new RealmList<>();
    }

    public University(String name, ArrayList<Subject> subjects){
        this.name = name;
        this.subjects = new RealmList<>();
        Collections.copy(this.subjects,subjects);
    }

    public University(University university) {
        this.name = university.getName();
        this.subjects = new RealmList<>();
        Collections.copy(this.subjects,university.getSubjects());
    }

    public static University getUniversityFromDatabase(){
        return MyRealm.getRealm().where(University.class).findFirst();
    }

    public static void updateUniveristyInDatabaseAsync(final University newUniversity){
        MyRealm.getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                University university = realm.where(University.class).findFirst();

                if(university == null){
                    university = realm.createObject(University.class);
                }

                university.name = newUniversity.name;

                for (Subject subject : newUniversity.subjects){
                    Subject.updateSubjectInDatabaseAsync(subject);
                }
            }
        });
    }

    public RealmList<Subject> getSubjects() {
        return subjects;
    }

    public String getName() {
        return name;
    }

    public void setSubjects(RealmList<Subject> subjects){
        this.subjects = new RealmList<>();
        Collections.copy(this.subjects,subjects);
    }

    public void addSubject(Subject subject){
        this.subjects.add(subject);
    }

    public void setName(String name){
        this.name = name;
    }

    public RealmList<Subject> getSelectedSubjects() {
        RealmList<Subject> selectedSubjects = new RealmList<>();

        for(Subject subject : subjects){
            if(subject.isSelected()){
                selectedSubjects.add(subject);
            }
        }
        return selectedSubjects;
    }
}
