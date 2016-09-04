package com.nemanjaasuv1912.diplomskirad.model;

import com.nemanjaasuv1912.diplomskirad.helper.MyRealm;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class Subject extends RealmObject {

    private String name;
    private String shortName;
    private boolean selected;
    private int year;
    private RealmList<Post> subjectPosts;

    public Subject(){
        name = "";
        shortName = "";
        selected = false;
        year = 1;
        subjectPosts = new RealmList<>();
    }

    public Subject(Subject subject){
        name = subject.name;
        shortName = subject.shortName;
        selected = subject.selected;
        year = subject.year;

        for (Post item : subject.subjectPosts){
            subjectPosts.add(new Post(item));
        }
    }

    public Subject(String name, String shortName, boolean selected, int year, ArrayList<Post> subjectPosts){
        this.name = name;
        this.shortName = shortName;
        this.selected = selected;
        this.year = year;
        this.subjectPosts = new RealmList<>();

        for (Post item : subjectPosts){
            this.subjectPosts.add(new Post(item));
        }
    }

    public static Subject getSubjectFromDatabase(String name){
        return getSubjectFromDatabase(MyRealm.getRealm(),name);
    }


    private static Subject getSubjectFromDatabase(Realm realm, String name){
        Subject subject = realm.where(Subject.class).equalTo("name",name).findFirst();

        if(subject == null){
            realm.beginTransaction();
            subject =realm.createObject(Subject.class);
            realm.commitTransaction();
        }

        return subject;
    }

    public static void updateSubjectInDatabaseAsync(final Subject newSubject) {
        MyRealm.getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Subject subject = getSubjectFromDatabase(realm, newSubject.name);

                subject.name = newSubject.name;
                subject.shortName = newSubject.shortName;
                subject.selected = newSubject.selected;
                subject.year = newSubject.year;


                for (Post subjectPost : newSubject.subjectPosts){
                    Post.updatePostInDatabaseAsync(subjectPost);
                }
            }
        });
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public int getNewItemsCount() {
        return subjectPosts.size();
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public RealmList<Post> getSubjectPosts(){
        return subjectPosts;
    }

    public void addPost(Post post){
        subjectPosts.add(post);
    }
}
