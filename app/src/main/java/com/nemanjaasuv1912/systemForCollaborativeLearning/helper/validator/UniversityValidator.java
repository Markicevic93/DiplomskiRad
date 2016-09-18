package com.nemanjaasuv1912.systemForCollaborativeLearning.helper.validator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;

import com.nemanjaasuv1912.systemForCollaborativeLearning.MyApplication;
import com.nemanjaasuv1912.systemForCollaborativeLearning.R;

/**
 * Created by nemanjamarkicevic on 8/6/16.
 */
public class UniversityValidator {

    public static boolean isValid(final String university, final TextInputLayout tilUniversity) {
        Context context = MyApplication.getContext();

        if (university.trim().length() == 0) {
            tilUniversity.setError(context.getString(R.string.university_empty));

            return false;
        }

        tilUniversity.setErrorEnabled(false);

        return true;
    }
}
