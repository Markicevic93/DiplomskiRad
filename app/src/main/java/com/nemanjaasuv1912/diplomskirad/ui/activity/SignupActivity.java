package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.View;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.RequestManager;
import com.nemanjaasuv1912.diplomskirad.helper.validator.EmailValidator;
import com.nemanjaasuv1912.diplomskirad.helper.validator.PasswordValidator;
import com.nemanjaasuv1912.diplomskirad.helper.validator.UsernameValidator;
import com.nemanjaasuv1912.diplomskirad.helper.validator.YearValidator;
import com.nemanjaasuv1912.diplomskirad.model.Student;
import com.nemanjaasuv1912.diplomskirad.model.University;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class SignupActivity extends BaseActivity{

    private TextInputLayout tilUsername, tilPassword, tilEmail, tilYear;
    private TextInputEditText etUsername, etPassword, etEmail, etYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setToolbar(R.id.signup_toolbar, getString(R.string.signup_toolbar_title));

        tilUsername = (TextInputLayout) findViewById(R.id.signup_til_username);
        tilPassword = (TextInputLayout) findViewById(R.id.signup_til_password);
        tilEmail = (TextInputLayout) findViewById(R.id.signup_til_email);
        tilYear = (TextInputLayout) findViewById(R.id.signup_til_year);

        etUsername = (TextInputEditText) findViewById(R.id.signup_et_username);
        etPassword = (TextInputEditText) findViewById(R.id.signup_et_password);
        etEmail = (TextInputEditText) findViewById(R.id.signup_et_email);
        etYear = (TextInputEditText) findViewById(R.id.signup_et_year);
    }

    public void nextOnClick(View view) {
        if(credentialsValid()){
            getUniversity();
        }
    }

    private void getUniversity() {
        new RequestManager() {
            @Override
            protected void onResponse(boolean isSuccessful, Response response) {
                if(isSuccessful){
                    try {
                        new University(response.body().string());
                        signupStudent();
                    } catch (IOException ignored) {}
                }else{
                    showBadEmailError();
                }
            }

            @Override
            protected void onFailure() {
                showBadEmailError();
            }

        }.getUniversity(etEmail.getText().toString());
    }

    private void signupStudent(){
        new RequestManager(){
            @Override
            protected void onResponse(boolean isSuccessful, Response response) {
                if(isSuccessful) {
                    try {
                        String body = response.body().string();
                        Student.parse(body);
                        Student.sharedStudent.setPassword(etPassword.getText().toString());
                        University.parse(new JSONObject(body).getJSONObject(Student.UNIVERSITY_KEY).toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(MainActivity.class);
                            }
                        });
                    } catch (IOException ignored) {

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    showUsernameExistError();
                }
            }

            @Override
            protected void onFailure() {
                showUsernameExistError();
            }
        }.register(etUsername.getText().toString(), etPassword.getText().toString(),
                etEmail.getText().toString(), etYear.getText().toString());
    }

    private void showBadEmailError(){
        final SignupActivity signupActivity = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tilEmail.setErrorEnabled(false);
                tilEmail.setErrorEnabled(true);
                tilEmail.setError(signupActivity.getString(R.string.email_bad));
            }
        });
    }

    private void showUsernameExistError(){
        final SignupActivity signupActivity = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tilUsername.setErrorEnabled(false);
                tilUsername.setErrorEnabled(true);
                tilUsername.setError(signupActivity.getString(R.string.username_exists));
            }
        });
    }

    private boolean credentialsValid() {
        boolean usernameValid = UsernameValidator.isValid(etUsername.getText().toString(), tilUsername);
        boolean passwordValid = PasswordValidator.isValid(etPassword.getText().toString(), tilPassword);
        boolean emailValid = EmailValidator.isValid(etEmail.getText().toString(), tilEmail);
        boolean yearValid = YearValidator.isValid(etYear.getText().toString(), tilYear);

        return usernameValid && passwordValid && emailValid && yearValid;
    }
}
