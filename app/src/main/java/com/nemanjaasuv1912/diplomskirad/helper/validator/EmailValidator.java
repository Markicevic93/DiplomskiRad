package com.nemanjaasuv1912.diplomskirad.helper.validator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;

import com.nemanjaasuv1912.diplomskirad.MyApplication;
import com.nemanjaasuv1912.diplomskirad.R;

import java.util.regex.Pattern;

/**
 * Created by nemanjamarkicevic on 8/6/16.
 */
public class EmailValidator {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public static boolean isValid(final String email, final TextInputLayout tilEmail) {
        Context context = MyApplication.getContext();

        if (email.length() == 0) {
            tilEmail.setError(context.getString(R.string.email_empty));

            return false;
        }

        if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email).find()) {
            tilEmail.setError(context.getString(R.string.email_bad));

            return false;
        }

        tilEmail.setErrorEnabled(false);

        return true;
    }
}
