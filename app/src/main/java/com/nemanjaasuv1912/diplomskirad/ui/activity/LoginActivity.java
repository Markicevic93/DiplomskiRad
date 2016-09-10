package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.RequestManager;
import com.nemanjaasuv1912.diplomskirad.helper.validator.EmailValidator;
import com.nemanjaasuv1912.diplomskirad.helper.validator.PasswordValidator;
import com.nemanjaasuv1912.diplomskirad.model.Student;
import com.nemanjaasuv1912.diplomskirad.model.University;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    private TextInputLayout tilEmail, tilPassword;
    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilEmail = (TextInputLayout) findViewById(R.id.login_til_email);
        tilPassword = (TextInputLayout) findViewById(R.id.login_til_password);
        etEmail = (EditText) findViewById(R.id.login_et_email);
        etPassword = (EditText) findViewById(R.id.login_et_password);
    }

    public void signinOnClick(View view) {
        etEmail.setText("nemanja@raf.edu.rs");
        etPassword.setText("sifra");

        if (credentialsValid()) {
            new RequestManager(){
                @Override
                protected void onResponse(boolean isSuccessful, Response response) {
                    if (isSuccessful){
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

                        } catch (IOException ignored) {} catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                protected void onFailure() {

                }
            }.login(etEmail.getText().toString(), etPassword.getText().toString());
        }
    }

    private boolean credentialsValid() {
        boolean emailValid = EmailValidator.isValid(etEmail.getText().toString(), tilEmail);
        boolean passwordValid = PasswordValidator.isValid(etPassword.getText().toString(), tilPassword);

        return emailValid && passwordValid;
    }

    public void signupOnClick(View view) {
        startActivity(SignupActivity.class);
    }

    @Override
    public void onBackPressed() {}
}
