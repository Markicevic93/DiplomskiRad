package com.nemanjaasuv1912.systemForCollaborativeLearning.helper.validator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;

import com.nemanjaasuv1912.systemForCollaborativeLearning.MyApplication;
import com.nemanjaasuv1912.systemForCollaborativeLearning.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nemanjamarkicevic on 8/6/16.
 */
public class UsernameValidator {

    public static final int USENNAME_MIN_LENGHT = 5;
    public static final int USENNAME_MAX_LENGHT = 15;

    private static Pattern pattern;
    private static Matcher matcher;

    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";

    public static boolean isValid(final String username, final TextInputLayout tilUsername) {
        Context context = MyApplication.getContext();

        if (username.contains(" ")) {
            tilUsername.setError(context.getString(R.string.username_contains_spaces));

            return false;
        }

        if (username.length() == 0) {
            tilUsername.setError(context.getString(R.string.username_empty));

            return false;
        }

        if (username.length() < USENNAME_MIN_LENGHT) {
            tilUsername.setError(context.getString(R.string.username_short));

            return false;
        }

        if (username.length() > USENNAME_MAX_LENGHT) {
            tilUsername.setError(context.getString(R.string.username_long));

            return false;
        }

        pattern = Pattern.compile(USERNAME_PATTERN);
        matcher = pattern.matcher(username);

        if (!matcher.matches()) {
            tilUsername.setError(context.getString(R.string.username_bad_regex));

            return false;
        }

        tilUsername.setErrorEnabled(false);

        return true;
    }
}
