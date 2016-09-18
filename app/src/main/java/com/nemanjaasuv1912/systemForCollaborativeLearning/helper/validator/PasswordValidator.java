package com.nemanjaasuv1912.systemForCollaborativeLearning.helper.validator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;

import com.nemanjaasuv1912.systemForCollaborativeLearning.MyApplication;
import com.nemanjaasuv1912.systemForCollaborativeLearning.R;

/**
 * Created by nemanjamarkicevic on 8/6/16.
 */
public class PasswordValidator {

    private static final int PASSWORD_MIN_LENGHT = 5;
    private static final int PASSWORD_MAX_LENGHT = 15;

    public static boolean isValid(final String password, final TextInputLayout tilPassword) {
        Context context = MyApplication.getContext();

        if (password.trim().length() == 0) {
            tilPassword.setError(context.getString(R.string.password_empty));

            return false;
        }

        if (password.length() < PASSWORD_MIN_LENGHT) {
            tilPassword.setError(context.getString(R.string.password_short));

            return false;
        }

        if (password.length() > PASSWORD_MAX_LENGHT) {
            tilPassword.setError(context.getString(R.string.password_long));

            return false;
        }

        tilPassword.setErrorEnabled(false);

        return true;
    }
}
