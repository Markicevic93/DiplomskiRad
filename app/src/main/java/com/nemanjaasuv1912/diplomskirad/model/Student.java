package com.nemanjaasuv1912.diplomskirad.model;

import com.nemanjaasuv1912.diplomskirad.helper.MyRealm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nemanjamarkicevic on 8/8/16.
 */
public class Student extends RealmObject {

    @PrimaryKey
    private String username;
    private String password;
    private String fullname;
    private String universityName;
    private String year;
    private String birtday;
    private String aboutme;
    private String email;
    private String imageUrl;

    public static Student getStudentFromDatabase(){
            return getStudentFromDatabase(MyRealm.getRealm());
    }

    private static Student getStudentFromDatabase(Realm realm){
        Student student = realm.where(Student.class).findFirst();

        if(student == null){
            realm.beginTransaction();
            student = realm.createObject(Student.class);

            student.username = "";
            student.password = "";
            student.fullname = "";
            student.universityName = "";
            student.year = "";
            student.birtday = "";
            student.aboutme = "";
            student.email = "";
            student.imageUrl = "";

            realm.commitTransaction();
        }

        return student;
    }

    public static void updateStudentInDatabaseAsync(final Student newProfile){
        MyRealm.getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Student student = getStudentFromDatabase(realm);
                University university = University.getUniversityFromDatabase(realm);

                student.username = newProfile.username;
                student.password = newProfile.password;
                student.fullname = newProfile.fullname;
                student.aboutme = newProfile.aboutme;
                student.birtday = newProfile.birtday;
                student.year = newProfile.year;
                student.email = newProfile.email;
                student.universityName = newProfile.universityName;
                student.imageUrl = newProfile.imageUrl;

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

    public String getImageUrl(){
        return imageUrl;
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

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}
