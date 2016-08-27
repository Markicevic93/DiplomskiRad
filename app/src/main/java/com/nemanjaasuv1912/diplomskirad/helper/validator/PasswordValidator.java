package com.nemanjaasuv1912.diplomskirad.helper.validator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;

import com.nemanjaasuv1912.diplomskirad.MyApplication;
import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.Constants;

/**
 * Created by nemanjamarkicevic on 8/6/16.
 */
public class PasswordValidator {

    public static boolean isValid(final String password, final TextInputLayout tilPassword){
        Context context = MyApplication.getContext();

        if(password.trim().length() == 0){
            tilPassword.setError(context.getString(R.string.password_empty));

            return false;
        }

        if(password.length() < Constants.PASSWORD_MIN_LENGHT){
            tilPassword.setError(context.getString(R.string.password_short));

            return false;
        }

        if(password.length() > Constants.PASSWORD_MAX_LENGHT){
            tilPassword.setError(context.getString(R.string.password_long));

            return false;
        }

        tilPassword.setErrorEnabled(false);

        return true;
    }
}
