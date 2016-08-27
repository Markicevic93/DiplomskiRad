package com.nemanjaasuv1912.diplomskirad.model;

import com.nemanjaasuv1912.diplomskirad.helper.MyRealm;

import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class University extends RealmObject {

    private String name;
    private RealmList<Subject> subjects;
    private String emailSufix;
    private String imageUrl;

    public University(){
        name = "";
        emailSufix = "";
        imageUrl = "";
        subjects = new RealmList<>();
    }

    public University(University university) {
        name = university.name;
        emailSufix = university.emailSufix;
        imageUrl = university.imageUrl;

        subjects = new RealmList<>();
        Collections.copy(this.subjects,university.getSubjects());
    }

    public static University getUniversityFromDatabase(){
        return getUniversityFromDatabase(MyRealm.getRealm());
    }

    public static University getUniversityFromDatabase(Realm realm) {
        University university = realm.where(University.class).findFirst();

        if(university == null){
            realm.beginTransaction();
            university = realm.createObject(University.class);

            university.name = "";
            university.emailSufix = "";
            university.imageUrl = "";
            university.subjects = new RealmList<>();

            realm.commitTransaction();
        }

        return university;
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
                university.emailSufix = newUniversity.emailSufix;
                university.imageUrl = newUniversity.imageUrl;

                for (Subject subject : newUniversity.subjects){
                    Subject.updateSubjectInDatabaseAsync(subject);
                }
            }
        });
    }

    public RealmList<Subject> getSubjects() {
        return subjects;
    }

    public void addSubject(Subject subject){
        this.subjects.add(subject);
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
}
