package com.nemanjaasuv1912.diplomskirad.helper.validator;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * Created by nemanjamarkicevic on 8/6/16.
 */
public class EmptyEditTextValidator {

    public static boolean isValid(final EditText editText, final String errorText) {

        if (editText.getText().toString().length() == 0) {
            editText.setError(errorText);

            return false;
        }

        editText.setError(null);

        return true;
    }

    public static boolean isValid(final String text, TextInputLayout textInputLayout, String errorText) {

        if (text.length() == 0) {
            textInputLayout.setError(errorText);

            return false;
        }

        textInputLayout.setErrorEnabled(false);

        return true;
    }
}
