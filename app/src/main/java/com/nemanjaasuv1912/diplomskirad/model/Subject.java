package com.nemanjaasuv1912.diplomskirad.model;

import com.nemanjaasuv1912.diplomskirad.helper.MyRealm;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class Subject extends RealmObject {

    @PrimaryKey
    private String name;
    private String fullName;
    private boolean selected;
    private RealmList<SubjectPost> subjectPosts;

    public Subject(){
        this.name = "";
        this.fullName = "";
        this.selected = false;
        this.subjectPosts = new RealmList<>();
    }

    public Subject(String name, String fullName, boolean selected, ArrayList<SubjectPost> subjectPosts){
        this.name = name;
        this.fullName = fullName;
        this.selected = selected;
        this.subjectPosts = new RealmList<>();

        for (SubjectPost item : subjectPosts){
            this.subjectPosts.add(new SubjectPost(item));
        }
    }

    public static Subject getSubjectFromDatabase(String name){
        return MyRealm.getRealm().where(Subject.class).equalTo("name",name).findFirst();
    }

    public static void updateSubjectInDatabaseAsync(final Subject newSubject) {
        MyRealm.getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Subject subject = realm.where(Subject.class).equalTo("name",newSubject.name).findFirst();

                if(subject == null){
                    subject = realm.createObject(Subject.class);
                }

                subject.name = newSubject.name;
                subject.fullName = newSubject.fullName;
                subject.selected = newSubject.selected;

                for (SubjectPost subjectPost : newSubject.subjectPosts){
                    SubjectPost.updateSubjectItemInDatabaseAsync(subjectPost);
                }
            }
        });
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public int getNewItemsCount() {
        return subjectPosts.size();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public RealmList<SubjectPost> getSubjectPosts(){
        return subjectPosts;
    }
}
