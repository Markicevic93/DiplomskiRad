package com.nemanjaasuv1912.systemForCollaborativeLearning.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nemanjaasuv1912.systemForCollaborativeLearning.R;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.alert.AlerDialog;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.alert.AlertType;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.api.RequestManager;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.validator.EmptyEditTextValidator;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.validator.FullnameValidator;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.validator.YearValidator;
import com.nemanjaasuv1912.systemForCollaborativeLearning.model.Student;
import com.nemanjaasuv1912.systemForCollaborativeLearning.ui.activity.base.ProgressBarActivity;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Response;

public class ProfileActivity extends ProgressBarActivity {

    private Student student;
    private boolean editing;
    private Menu menu;
    private HashMap<Integer, String> oldValues;

    private TextInputLayout tilFullName, tilYear, tilBirthday, tilAboutMe;
    private TextInputEditText etFullName, etYear, etBirthday, etAboutMe;
    private TextView tvUsername, tvEmail, tvUniversity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        student = Student.sharedStudent;

        setToolbar(R.id.profile_toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        tilFullName = (TextInputLayout) findViewById(R.id.profile_til_fullname);
        tilBirthday = (TextInputLayout) findViewById(R.id.profile_til_birthday);
        tilAboutMe = (TextInputLayout) findViewById(R.id.profile_til_about_me);
        tilYear = (TextInputLayout) findViewById(R.id.profile_til_year);
        etFullName = (TextInputEditText) findViewById(R.id.profile_et_fullname);
        etBirthday = (TextInputEditText) findViewById(R.id.profile_et_birthday);
        etAboutMe = (TextInputEditText) findViewById(R.id.profile_et_about_me);
        etYear = (TextInputEditText) findViewById(R.id.profile_et_year);
        tvUniversity = (TextView) findViewById(R.id.profile_tv_university);
        tvUsername = (TextView) findViewById(R.id.profile_tv_username);
        tvEmail = (TextView) findViewById(R.id.profile_tv_email);

        tvUniversity.setText(student.getUniversityName());
        tvUsername.setText(student.getUsername());
        tvEmail.setText(student.getEmail());
        etBirthday.setText(student.getBirthdate());
        etFullName.setText(student.getFullname());
        etYear.setText(student.getYearAsString());
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
        } else if (id == R.id.action_edit_confirm) {
            if (editing && saveProfile()) {
                editing = false;
                setEditTextsEnabled(false);
                menu.findItem(R.id.action_cancel).setVisible(false);
                setIconForMenuItem(item, android.R.drawable.ic_menu_edit);
            } else {
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
        tilBirthday.setErrorEnabled(false);
        tilFullName.setErrorEnabled(false);
        tilAboutMe.setErrorEnabled(false);
        tilYear.setErrorEnabled(false);
    }

    private void saveOldValues() {
        oldValues = new HashMap<>();
        oldValues.put(etFullName.getId(), etFullName.getText().toString());
        oldValues.put(etYear.getId(), etYear.getText().toString());
        oldValues.put(etBirthday.getId(), etBirthday.getText().toString());
        oldValues.put(etAboutMe.getId(), etAboutMe.getText().toString());
    }

    private void putOldValues() {
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
        boolean fullnameValid = FullnameValidator.isValid(etFullName.getText().toString(), tilFullName);
        boolean birthdayValid = EmptyEditTextValidator.isValid(etBirthday.getText().toString(), tilBirthday, getString(R.string.birthday_text_empty));
        boolean aboutMeValid = EmptyEditTextValidator.isValid(etAboutMe.getText().toString(), tilAboutMe, getString(R.string.about_me_text_empty));
        boolean yearValid = YearValidator.isValid(etYear.getText().toString(), tilYear);

        if (fullnameValid && birthdayValid && yearValid && aboutMeValid) {
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
        showProgressBar();
        new RequestManager() {

            @Override
            protected void onResponse(boolean isSuccessful, Response response) {
                hideProgressBar();
                if (isSuccessful) {
                    try {
                        Student.sharedStudent = new Student(response.body().string());
                    } catch (IOException ignored) {
                    }
                }
            }

            @Override
            protected void onFailure() {
                hideProgressBar();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlerDialog.showAlert(context, AlertType.REQUEST_ERROR);
                    }
                });
            }
        }.updateStudent(student);
    }

    public void logoutOnClick(View view) {
        startActivity(LoginActivity.class);
    }
}
