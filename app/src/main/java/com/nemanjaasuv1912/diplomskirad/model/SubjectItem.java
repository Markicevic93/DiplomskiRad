package com.nemanjaasuv1912.diplomskirad.model;

import com.nemanjaasuv1912.diplomskirad.helper.MyRealm;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class SubjectItem extends RealmObject {

    @PrimaryKey
    private String name;

    public SubjectItem(){
        this.name = "";
    }

    public SubjectItem(String name){
        this.name = name;
    }

    public SubjectItem(SubjectItem item){
        this.name = item.getName();
    }

    public String getName() {
        return name;
    }

    public static Subject getSubjectItemFromDatabase(String name){
        return MyRealm.getRealm().where(Subject.class).equalTo("name",name).findFirst();
    }

    public static void updateSubjectItemInDatabaseAsync(final SubjectItem newSubjectItem) {
        MyRealm.getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SubjectItem subjectItem = realm.where(SubjectItem.class).equalTo("name",newSubjectItem.name).findFirst();

                if(subjectItem == null){
                    subjectItem = realm.createObject(SubjectItem.class);
                }

                subjectItem.name = newSubjectItem.name;

            }
        });
    }
}
