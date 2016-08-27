package com.nemanjaasuv1912.diplomskirad.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.nemanjaasuv1912.diplomskirad.R;

/**
 * Created by nemanjamarkicevic on 8/8/16.
 */
public class TopicsButton extends Button {

    private boolean selected;

    public TopicsButton(Context context) {
            super(context);
        }

    public TopicsButton(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

    public TopicsButton(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TopicsButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {

        if(this.selected == selected){
            return;
        }

        this.selected = selected;

        if(selected){

            setTextColor(getResources().getColor(R.color.colorPrimary));
            setBackground(getResources().getDrawable(R.drawable.subject_topic_background_selected));
        }else {

            setBackground(getResources().getDrawable(R.drawable.subject_topic_background));
            setTextColor(Color.WHITE);
        }
    }


}
