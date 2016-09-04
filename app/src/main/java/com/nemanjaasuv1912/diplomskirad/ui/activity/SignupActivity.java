package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.validator.EmailValidator;
import com.nemanjaasuv1912.diplomskirad.helper.validator.PasswordValidator;
import com.nemanjaasuv1912.diplomskirad.helper.validator.UniversityValidator;
import com.nemanjaasuv1912.diplomskirad.helper.validator.UsernameValidator;
import com.nemanjaasuv1912.diplomskirad.helper.validator.YearValidator;
import com.nemanjaasuv1912.diplomskirad.model.Student;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends BaseActivity{

    private final int SELECT_PHOTO = 1;

    private TextInputLayout tilUsername, tilPassword, tilEmail, tilUniversity, tilYear;
    private EditText etUsername, etPassword, etEmail, etUniversity, etYear;
    private CircleImageView civProfile;
    private ImageView ivCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setToolbar(R.id.signup_toolbar, getString(R.string.signup_toolbar_title));

        tilUsername = (TextInputLayout) findViewById(R.id.signup_til_username);
        tilPassword = (TextInputLayout) findViewById(R.id.signup_til_password);
        tilEmail = (TextInputLayout) findViewById(R.id.signup_til_email);
        tilUniversity = (TextInputLayout) findViewById(R.id.signup_til_university);
        tilYear = (TextInputLayout) findViewById(R.id.signup_til_year);

        etUsername = (EditText) findViewById(R.id.signup_et_username);
        etPassword = (EditText) findViewById(R.id.signup_et_password);
        etEmail = (EditText) findViewById(R.id.signup_et_email);
        etUniversity = (EditText) findViewById(R.id.signup_et_university);
        etYear = (EditText) findViewById(R.id.signup_et_year);

        civProfile = (CircleImageView) findViewById(R.id.signup_civ_profile_picture);
        ivCamera = (ImageView) findViewById(R.id.signup_iv_camera);
    }



    public void nextOnClick(View view) {
        if(credentialsValid()){

            //// TODO: 8/9/16 send request and write to database on response
            Student profile = new Student();
            profile.setUsername(etUsername.getText().toString());
            profile.setPassword(etPassword.getText().toString()); // todo encript password
            profile.setEmail(etEmail.getText().toString());
            profile.setYear(etYear.getText().toString());
            profile.setUniversityName(etUniversity.getText().toString());
            Student.updateStudentInDatabaseAsync(profile);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(MainActivity.class);
                }
            }, 1000);

        }
    }

    private boolean credentialsValid() {
        boolean usernameValid = UsernameValidator.isValid(etUsername.getText().toString(), tilUsername);
        boolean passwordValid = PasswordValidator.isValid(etPassword.getText().toString(), tilPassword);
        boolean emailValid = EmailValidator.isValid(etEmail.getText().toString(), tilEmail);
        boolean universityValid = UniversityValidator.isValid(etUniversity.getText().toString(), tilUniversity);
        boolean yearValid = YearValidator.isValid(etYear.getText().toString(), tilYear);

        return usernameValid && passwordValid && emailValid && universityValid && yearValid;
    }

    public void cameraOnClick(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        civProfile.setImageBitmap(selectedImage);
                        ivCamera.setImageDrawable(null);
                    } catch (FileNotFoundException ignore) {}

                }
        }
    }
}
