package com.nemanjaasuv1912.systemForCollaborativeLearning.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ProgressBar;

import com.nemanjaasuv1912.systemForCollaborativeLearning.R;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.alert.AlerDialog;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.alert.AlertType;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.api.RequestManager;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.validator.EmailValidator;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.validator.EmptyEditTextValidator;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.validator.PasswordValidator;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.validator.UsernameValidator;
import com.nemanjaasuv1912.systemForCollaborativeLearning.model.Student;
import com.nemanjaasuv1912.systemForCollaborativeLearning.model.University;
import com.nemanjaasuv1912.systemForCollaborativeLearning.ui.activity.base.ProgressBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class SignupActivity extends ProgressBarActivity {

    private TextInputLayout tilUsername, tilPassword, tilEmail, tilYear;
    private TextInputEditText etUsername, etPassword, etEmail, etYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setToolbar(R.id.signup_toolbar, getString(R.string.signup_toolbar_title));

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
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
        tilEmail.setErrorEnabled(false);

        if (credentialsValid() && !progressBarVisible()) {
            getUniversity();
        }
    }

    private void getUniversity() {
        showProgressBar();
        new RequestManager() {

            @Override
            protected void onResponse(boolean isSuccessful, Response response) {
                hideProgressBar();
                if (isSuccessful) {
                    try {
                        new University(response.body().string());
                        signupStudent();
                    } catch (IOException ignored) {
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showBadEmailError();
                        }
                    });
                }
            }

            @Override
            protected void onFailure() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressBar();
                        AlerDialog.showAlert(context, AlertType.REQUEST_ERROR);
                    }
                });
            }

        }.getUniversity(etEmail.getText().toString());
    }

    private void signupStudent() {
        showProgressBar();
        new RequestManager() {

            @Override
            protected void onResponse(boolean isSuccessful, Response response) {
                hideProgressBar();
                if (isSuccessful) {
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
                    } catch (IOException | JSONException ignored) {
                    }
                } else {
                    hideProgressBar();
                    showEmailExistError();
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
        }.register(etUsername.getText().toString(), etPassword.getText().toString(),
                etEmail.getText().toString(), etYear.getText().toString());
    }

    private void showBadEmailError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tilEmail.setError(context.getString(R.string.email_bad));
            }
        });
    }

    private void showEmailExistError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tilEmail.setError(context.getString(R.string.email_exists));
            }
        });
    }

    private boolean credentialsValid() {
        boolean usernameValid = UsernameValidator.isValid(etUsername.getText().toString(), tilUsername);
        boolean passwordValid = PasswordValidator.isValid(etPassword.getText().toString(), tilPassword);
        boolean emailValid = EmailValidator.isValid(etEmail.getText().toString(), tilEmail);
        boolean yearValid = EmptyEditTextValidator.isValid(etYear.getText().toString(), tilYear, getString(R.string.year_empty));

        return usernameValid && passwordValid && emailValid && yearValid;
    }
}
