package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.validator.PasswordValidator;
import com.nemanjaasuv1912.diplomskirad.helper.validator.UsernameValidator;
import com.nemanjaasuv1912.diplomskirad.model.Student;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;
public class LoginActivity extends BaseActivity {

    private TextInputLayout tilUsername, tilPassword;
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilUsername = (TextInputLayout) findViewById(R.id.login_til_username);
        tilPassword = (TextInputLayout) findViewById(R.id.login_til_password);
        etUsername = (EditText) findViewById(R.id.login_et_username);
        etPassword = (EditText) findViewById(R.id.login_et_password);

        autoLogin();
    }

    private void autoLogin() {
        etUsername.setText("markicevic");
        etPassword.setText("sifra");
        signinOnClick(null);
    }


    public void signinOnClick(View view) {
        if (credentialsValid()) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private boolean credentialsValid() {
        boolean usernameValid = UsernameValidator.isValid(etUsername.getText().toString(), tilUsername);
        boolean passwordValid = PasswordValidator.isValid(etPassword.getText().toString(), tilPassword);

        if (usernameValid && passwordValid) {
            //// TODO: 8/9/16  check this with rest api
            Student student = Student.getStudentFromDatabase();
            usernameValid = student != null && (student.getUsername().compareTo(etUsername.getText().toString()) == 0);
            passwordValid = student != null && (student.getPassword().compareTo(etPassword.getText().toString()) == 0);

            if (usernameValid) {
                tilUsername.setErrorEnabled(false);

                if (!passwordValid) {
                    tilPassword.setError(this.getString(R.string.password_wrong));
                }
            } else {
                tilPassword.setErrorEnabled(false);
                tilUsername.setError(this.getString(R.string.username_wrong));
            }
        }

        return usernameValid && passwordValid;
    }

    public void signupOnClick(View view) {
        startActivity(SignupActivity.class);
    }
}
