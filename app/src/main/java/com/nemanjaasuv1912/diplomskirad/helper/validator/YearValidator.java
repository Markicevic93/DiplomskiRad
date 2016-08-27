package com.nemanjaasuv1912.diplomskirad.helper.validator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;

import com.nemanjaasuv1912.diplomskirad.MyApplication;
import com.nemanjaasuv1912.diplomskirad.R;

/**
 * Created by nemanjamarkicevic on 8/6/16.
 */
public class YearValidator {

    public static boolean isValid(final String year, final TextInputLayout tilYear){
        Context context = MyApplication.getContext();

        if(year.trim().length() == 0){
            tilYear.setError(context.getString(R.string.year_empty));

            return false;
        }

        tilYear.setErrorEnabled(false);

        return true;
    }
}
