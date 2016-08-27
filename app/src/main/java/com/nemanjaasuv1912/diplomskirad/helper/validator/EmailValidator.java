package com.nemanjaasuv1912.diplomskirad.helper.validator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;

import com.nemanjaasuv1912.diplomskirad.MyApplication;
import com.nemanjaasuv1912.diplomskirad.R;

/**
 * Created by nemanjamarkicevic on 8/6/16.
 */
public class EmailValidator {

    public static boolean isValid(final String email, final TextInputLayout tilEmail){
        Context context = MyApplication.getContext();

        if(email.length() == 0){
            tilEmail.setError(context.getString(R.string.email_empty));

            return false;
        }

        tilEmail.setErrorEnabled(false);

        return true;
    }
}
