package com.nemanjaasuv1912.diplomskirad.helper.validator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;

import com.nemanjaasuv1912.diplomskirad.MyApplication;
import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nemanjamarkicevic on 8/6/16.
 */
public class UsernameValidator{

        private static Pattern pattern;
        private static Matcher matcher;

        private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";

    public static boolean isValid(final String username, final TextInputLayout tilUsername){
        Context context = MyApplication.getContext();

        if(username.contains(" ")){
            tilUsername.setError(context.getString(R.string.username_contains_spaces));

            return false;
        }

        if(username.length() == 0){
            tilUsername.setError(context.getString(R.string.username_empty));

            return false;
        }

        if(username.length() < Constants.USENNAME_MIN_LENGHT){
            tilUsername.setError(context.getString(R.string.username_short));

            return false;
        }

        if(username.length() > Constants.USENNAME_MAX_LENGHT){
            tilUsername.setError(context.getString(R.string.username_long));

            return false;
        }

        pattern = Pattern.compile(USERNAME_PATTERN);
        matcher = pattern.matcher(username);

        if(!matcher.matches()){
            tilUsername.setError(context.getString(R.string.username_bad_regex));

            return false;
        }

        tilUsername.setErrorEnabled(false);

        return true;
    }
}
