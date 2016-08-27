package com.nemanjaasuv1912.diplomskirad.model;

import com.nemanjaasuv1912.diplomskirad.helper.MyRealm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class SubjectPost extends RealmObject {

    @PrimaryKey
    private String name;

    public SubjectPost(){
        this.name = "";
    }

    public SubjectPost(String name){
        this.name = name;
    }

    public SubjectPost(SubjectPost item){
        this.name = item.getName();
    }

    public String getName() {
        return name;
    }

    public static Subject getSubjectItemFromDatabase(String name){
        return MyRealm.getRealm().where(Subject.class).equalTo("name",name).findFirst();
    }

    public static void updateSubjectItemInDatabaseAsync(final SubjectPost newSubjectPost) {
        MyRealm.getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SubjectPost subjectPost = realm.where(SubjectPost.class).equalTo("name", newSubjectPost.name).findFirst();

                if(subjectPost == null){
                    subjectPost = realm.createObject(SubjectPost.class);
                }

                subjectPost.name = newSubjectPost.name;

            }
        });
    }
}
