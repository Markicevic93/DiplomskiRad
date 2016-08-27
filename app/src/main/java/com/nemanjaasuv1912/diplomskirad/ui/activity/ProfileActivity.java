package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.MyRealm;
import com.nemanjaasuv1912.diplomskirad.helper.validator.UniversityValidator;
import com.nemanjaasuv1912.diplomskirad.helper.validator.YearValidator;
import com.nemanjaasuv1912.diplomskirad.model.Comment;
import com.nemanjaasuv1912.diplomskirad.model.Post;
import com.nemanjaasuv1912.diplomskirad.model.Student;
import com.nemanjaasuv1912.diplomskirad.model.Subject;
import com.nemanjaasuv1912.diplomskirad.model.University;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity{

    private CircleImageView civProfile;
    private TextInputLayout tilFullName, tilUniversity, tilYear, tilBirthday, tilAboutMe;
    private EditText etFullName, etUniversity, etYear, etBirthday, etAboutMe;
    private TextView tvUsername, tvEmail;

    private Student student;
    private boolean editing;
    private Menu menu;
    private HashMap<Integer,String> oldValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setToolbar(R.id.profile_toolbar);

        tilFullName = (TextInputLayout) findViewById(R.id.profile_til_fullname);
        tilUniversity = (TextInputLayout) findViewById(R.id.profile_til_university);
        tilYear = (TextInputLayout) findViewById(R.id.profile_til_year);
        tilBirthday = (TextInputLayout) findViewById(R.id.profile_til_birthday);
        tilAboutMe = (TextInputLayout) findViewById(R.id.profile_til_about_me);

        etFullName = (EditText) findViewById(R.id.profile_et_fullname);
        etUniversity = (EditText) findViewById(R.id.profile_et_university);
        etYear = (EditText) findViewById(R.id.profile_et_year);
        etBirthday = (EditText) findViewById(R.id.profile_et_birthday);
        etAboutMe = (EditText) findViewById(R.id.profile_et_about_me);
        tvUsername = (TextView) findViewById(R.id.profile_tv_username);
        tvEmail = (TextView) findViewById(R.id.profile_tv_email);

        student = Student.getStudentFromDatabase();

        etFullName.setText(student.getFullname() != null ? student.getFullname() : "");
        etUniversity.setText(student.getUniversity() != null ? student.getUniversity().getName() : "" );
        etBirthday.setText(student.getBirtday() != null ? student.getBirtday() : "");
        etAboutMe.setText(student.getAboutme()!= null ? student.getAboutme() : "");
        etYear.setText(student.getYear()!= null ? student.getYear() : "");
        tvUsername.setText(student.getUsername()!= null ? student.getUsername() : "");
        tvEmail.setText(student.getEmail()!= null ? student.getEmail() : "");

        setEditTextsEnabled(false);

        //addData();

    }
    private void addData() {

        String postText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ut volutpat ante. Sed at sem vel nunc tincidunt molestie. Vestibulum vitae mi erat. Vivamus fermentum, ante ac luctus congue, enim neque pretium tortor, vitae vulputate purus eros at magna. Maecenas efficitur ipsum et tristique suscipit.";
        University university = University.getUniversityFromDatabase();
        Post subjectPost;
        Subject subject;
        Subject subject1;
        Subject subject2;
        Subject subject3;
        ArrayList<Post> subjectItems;

        subjectPost = new Post("Neki post", postText);
        subjectPost.setTagExam(true);
        subjectPost.setTagHomework(true);
        subjectPost.setTagTest(true);

        Comment subjectPostComment = new Comment("Neki Text za komentar", student.getUsername(), student.getUsername());

        subjectPost.addComment(subjectPostComment);
        subjectItems = new ArrayList<>();
        subjectItems.add(subjectPost);
        subject = new Subject("TRV", "Tehnologije Racunara Vnesto", false, 1, subjectItems);


        subjectPost = new Post("Domaci post",postText);
        subjectItems = new ArrayList<>();
        subjectItems.add(subjectPost);
        subject1 = new Subject("OOP", "Objektno orijentisano projektovanje", false, 2, subjectItems);


        subjectPost = new Post("Test postx",postText);
        subjectItems = new ArrayList<>();
        subjectItems.add(subjectPost);
        subject2 = new Subject("TTT", "Tehnologije Tehnologije Tehnologije", false, 3, subjectItems);


        subjectPost = new Post("Neki item",postText);
        subjectItems = new ArrayList<>();
        subjectItems.add(subjectPost);
        subject3 = new Subject("R", "Racunara", false, 4, subjectItems);

        MyRealm.getRealm().beginTransaction();
        university.addSubject(subject);
        university.addSubject(subject1);
        university.addSubject(subject2);
        university.addSubject(subject3);
        MyRealm.getRealm  ().commitTransaction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cancel) {
            putOldValues();
            setEditTextsEnabled(false);
            item.setVisible(false);
            editing = false;
            setIconForMenuItem(menu.findItem(R.id.action_edit_confirm), android.R.drawable.ic_menu_edit);
            clearErrors();

            return true;
        }else if (id == R.id.action_edit_confirm) {
            if(editing && saveProfile()){
                editing = false;
                setEditTextsEnabled(false);
                menu.findItem(R.id.action_cancel).setVisible(false);
                setIconForMenuItem(item, android.R.drawable.ic_menu_edit);
            }else{
                editing = true;
                setEditTextsEnabled(true);
                saveOldValues();
                menu.findItem(R.id.action_cancel).setVisible(true);
                setIconForMenuItem(item, android.R.drawable.ic_menu_save);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearErrors() {
        tilYear.setErrorEnabled(false);
        tilBirthday.setErrorEnabled(false);
        tilUniversity.setErrorEnabled(false);
        tilFullName.setErrorEnabled(false);
        tilAboutMe.setErrorEnabled(false);
    }

    private void saveOldValues() {
        oldValues = new HashMap<>();

        oldValues.put(etFullName.getId(),etFullName.getText().toString());
        oldValues.put(etYear.getId(),etYear.getText().toString());
        oldValues.put(etBirthday.getId(),etBirthday.getText().toString());
        oldValues.put(etAboutMe.getId(),etAboutMe.getText().toString());
        oldValues.put(etUniversity.getId(),etUniversity.getText().toString());
    }

    private void putOldValues(){
        etFullName.setText(oldValues.get(etFullName.getId()));
        etYear.setText(oldValues.get(etYear.getId()));
        etBirthday.setText(oldValues.get(etBirthday.getId()));
        etAboutMe.setText(oldValues.get(etAboutMe.getId()));
        etUniversity.setText(oldValues.get(etUniversity.getId()));
    }

    private void setEditTextsEnabled(boolean enabled) {
        etFullName.setEnabled(enabled);
        etYear.setEnabled(enabled);
        etBirthday.setEnabled(enabled);
        etAboutMe.setEnabled(enabled);
        etUniversity.setEnabled(enabled);
    }

    private boolean saveProfile() {
        boolean fullnameValid = UniversityValidator.isValid(etFullName.getText().toString(), tilFullName);
        boolean birthdayValid = YearValidator.isValid(etBirthday.getText().toString(), tilBirthday);
        boolean universityValid = UniversityValidator.isValid(etUniversity.getText().toString(), tilUniversity);
        boolean yearValid = YearValidator.isValid(etYear.getText().toString(), tilYear);
        boolean aboutMeValid = YearValidator.isValid(etAboutMe.getText().toString(), tilAboutMe);

        if(fullnameValid && birthdayValid && universityValid && yearValid && aboutMeValid){
            MyRealm.getRealm().beginTransaction();

            student.setFullname(etFullName.getText().toString());
            student.setBirtday(etBirthday.getText().toString());
            student.setUniversityName(etUniversity.getText().toString());
            student.setYear(etYear.getText().toString());
            student.setAboutme(etAboutMe.getText().toString());

            return true;
        }

        return false;
    }

    public void imageOnClick(View view) {
    }

}
