package com.nemanjaasuv1912.diplomskirad.model;

import com.nemanjaasuv1912.diplomskirad.MyApplication;
import com.nemanjaasuv1912.diplomskirad.helper.MyRealm;
import com.nemanjaasuv1912.diplomskirad.ui.activity.MainActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nemanjamarkicevic on 8/8/16.
 */
public class Profile extends RealmObject {

    @PrimaryKey
    private String username;
    private String password;
    private String fullname;
    private String universityName;
    private String year;
    private String birtday;
    private String aboutme;
    private String email;

    public static Profile getProfileFromDatabase(){
        return MyRealm.getRealm().where(Profile.class).findFirst();
    }

    public static void updateProfileInDatabaseAsync(final Profile newProfile){
        MyRealm.getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Profile profile = realm.where(Profile.class).findFirst();
                University university = realm.where(University.class).findFirst();

                if(profile == null){
                    profile = realm.createObject(Profile.class);
                }

                if(university == null){
                    university = realm.createObject(University.class);
                }

                profile.username = newProfile.username;
                profile.password = newProfile.password;
                profile.fullname = newProfile.fullname;
                profile.aboutme = newProfile.aboutme;
                profile.birtday = newProfile.birtday;
                profile.year = newProfile.year;
                profile.email = newProfile.email;
                profile.universityName = newProfile.universityName;

                university.setName(newProfile.universityName);
            }
        });
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullname() {
        return fullname;
    }

    public University getUniversity() {
        return University.getUniversityFromDatabase();
    }

    public String getYear() {
        return year;
    }

    public String getBirtday() {
        return birtday;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setUniversity(University university) {
        this.universityName = university.getName();
        University.updateUniveristyInDatabaseAsync(university);
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setBirtday(String birtday) {
        this.birtday = birtday;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityName(){
        return this.universityName;
    }
}
