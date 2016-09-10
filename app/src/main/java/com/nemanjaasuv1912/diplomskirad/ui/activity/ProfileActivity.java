package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.RequestManager;
import com.nemanjaasuv1912.diplomskirad.helper.validator.UniversityValidator;
import com.nemanjaasuv1912.diplomskirad.helper.validator.YearValidator;
import com.nemanjaasuv1912.diplomskirad.model.Student;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;

import java.util.HashMap;

import okhttp3.Response;

public class ProfileActivity extends BaseActivity{

    private TextInputLayout tilFullName, tilYear, tilBirthday, tilAboutMe;
    private TextInputEditText etFullName, etYear, etBirthday, etAboutMe;
    private TextView tvUsername, tvEmail, tvUniversity;

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
        tilYear = (TextInputLayout) findViewById(R.id.profile_til_year);
        tilBirthday = (TextInputLayout) findViewById(R.id.profile_til_birthday);
        tilAboutMe = (TextInputLayout) findViewById(R.id.profile_til_about_me);

        etFullName = (TextInputEditText) findViewById(R.id.profile_et_fullname);
        etYear = (TextInputEditText) findViewById(R.id.profile_et_year);
        etBirthday = (TextInputEditText) findViewById(R.id.profile_et_birthday);
        etAboutMe = (TextInputEditText) findViewById(R.id.profile_et_about_me);

        tvUsername = (TextView) findViewById(R.id.profile_tv_username);
        tvEmail = (TextView) findViewById(R.id.profile_tv_email);
        tvUniversity = (TextView) findViewById(R.id.profile_tv_university);

        student = Student.sharedStudent;

        tvUsername.setText(student.getUsername());
        etYear.setText(student.getYearAsString());
        tvEmail.setText(student.getEmail());
        tvUniversity.setText(student.getUniversityName());
        etFullName.setText(student.getFullname());
        etBirthday.setText(student.getBirthdate());
        etAboutMe.setText(student.getAboutMe());

        setEditTextsEnabled(false);
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
        tilFullName.setErrorEnabled(false);
        tilAboutMe.setErrorEnabled(false);
    }

    private void saveOldValues() {
        oldValues = new HashMap<>();

        oldValues.put(etFullName.getId(),etFullName.getText().toString());
        oldValues.put(etYear.getId(),etYear.getText().toString());
        oldValues.put(etBirthday.getId(),etBirthday.getText().toString());
        oldValues.put(etAboutMe.getId(),etAboutMe.getText().toString());
    }

    private void putOldValues(){
        etFullName.setText(oldValues.get(etFullName.getId()));
        etYear.setText(oldValues.get(etYear.getId()));
        etBirthday.setText(oldValues.get(etBirthday.getId()));
        etAboutMe.setText(oldValues.get(etAboutMe.getId()));
    }

    private void setEditTextsEnabled(boolean enabled) {
        etFullName.setEnabled(enabled);
        etYear.setEnabled(enabled);
        etBirthday.setEnabled(enabled);
        etAboutMe.setEnabled(enabled);
    }

    private boolean saveProfile() {
        boolean fullnameValid = UniversityValidator.isValid(etFullName.getText().toString(), tilFullName);
        boolean birthdayValid = YearValidator.isValid(etBirthday.getText().toString(), tilBirthday);
        boolean yearValid = YearValidator.isValid(etYear.getText().toString(), tilYear);
        boolean aboutMeValid = YearValidator.isValid(etAboutMe.getText().toString(), tilAboutMe);

        if(fullnameValid && birthdayValid && yearValid && aboutMeValid){

            student.setFullname(etFullName.getText().toString());
            student.setBirthdate(etBirthday.getText().toString());
            student.setYear(Integer.parseInt(etYear.getText().toString()));
            student.setAboutMe(etAboutMe.getText().toString());

            updateStudent();

            return true;
        }

        return false;
    }

    public void updateStudent() {
        new RequestManager(){

            @Override
            protected void onResponse(boolean isSuccessful, Response response) {
                // TODO: 9/14/16 Update student here
            }

            @Override
            protected void onFailure() {

            }
        }.updateStudent(student);
    }

    public void logoutOnClick(View view) {
        startActivity(LoginActivity.class);
    }
}
