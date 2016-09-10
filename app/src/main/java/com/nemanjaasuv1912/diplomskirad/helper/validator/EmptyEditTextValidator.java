package com.nemanjaasuv1912.diplomskirad.helper.validator;

/**
 * Created by nemanjamarkicevic on 8/6/16.
 */
public class EmptyEditTextValidator {

    public static boolean isValid(final String text){
        return text.length() != 0;
    }
}
