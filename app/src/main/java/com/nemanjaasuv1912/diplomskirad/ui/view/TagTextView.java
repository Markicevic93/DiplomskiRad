package com.nemanjaasuv1912.diplomskirad.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nemanjaasuv1912.diplomskirad.R;

/**
 * Created by nemanjamarkicevic on 8/8/16.
 */
public class TagTextView extends TextView {

    public TagTextView(Context context, String title){
        super(context);

        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (10 * scale + 0.5f);

        setText(title);
        setTextColor(Color.WHITE);
        setBackground(getResources().getDrawable(R.drawable.subject_post_tag_background));

        LinearLayout.LayoutParams params = (new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        params.setMargins(dpAsPixels, 0, 0, 0);
        setLayoutParams(params);
        setPadding(dpAsPixels,0,dpAsPixels,0);
    }

    public TagTextView(Context context) {
            super(context);
        }

    public TagTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
    }
}
