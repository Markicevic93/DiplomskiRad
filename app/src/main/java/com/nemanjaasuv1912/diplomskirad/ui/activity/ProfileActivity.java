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
import com.nemanjaasuv1912.diplomskirad.model.Profile;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity{

    private CircleImageView civProfile;
    private TextInputLayout tilFullName, tilUniversity, tilYear, tilBirthday, tilAboutMe;
    private EditText etFullName, etUniversity, etYear, etBirthday, etAboutMe;
    private TextView tvUsername, tvEmail;

    private Profile profile;
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

        profile = Profile.getProfileFromDatabase();

        etFullName.setText(profile.getFullname() != null ? profile.getFullname() : "");
        etUniversity.setText(profile.getUniversity() != null ? profile.getUniversity().getName() : "" );
        etBirthday.setText(profile.getBirtday() != null ? profile.getBirtday() : "");
        etAboutMe.setText(profile.getAboutme()!= null ? profile.getAboutme() : "");
        etYear.setText(profile.getYear()!= null ? profile.getYear() : "");
        tvUsername.setText(profile.getUsername()!= null ? profile.getUsername() : "");
        tvEmail.setText(profile.getEmail()!= null ? profile.getEmail() : "");

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

            profile.setFullname(etFullName.getText().toString());
            profile.setBirtday(etBirthday.getText().toString());
            profile.setUniversityName(etUniversity.getText().toString());
            profile.setYear(etYear.getText().toString());
            profile.setAboutme(etAboutMe.getText().toString());

            return true;
        }

        return false;
    }

    public void imageOnClick(View view) {
    }

}
