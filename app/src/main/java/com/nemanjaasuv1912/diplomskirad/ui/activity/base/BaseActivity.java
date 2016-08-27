package com.nemanjaasuv1912.diplomskirad.ui.activity.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.ui.activity.SignupActivity;

/**
 * Created by nemanjamarkicevic on 8/14/16.
 */
public class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    protected void setIconForMenuItem(MenuItem item, int drawableId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            item.setIcon(getResources().getDrawable(drawableId, null));
        }else {
            item.setIcon(getResources().getDrawable(drawableId));
        }
    }

    protected void startActivity(Class<?> cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void setToolbar(int toolbarId){
        setToolbar(toolbarId, "");
    }

    protected void setToolbar(int toolbarId,String toolbarTitle){
        setToolbar(toolbarId, toolbarTitle, 0);
    }

    protected void setToolbar(int toolbarId,String toolbarTitle,int toolbarIconId){
        toolbar = (Toolbar) findViewById(toolbarId);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(toolbarTitle);

        if(toolbarIconId != 0){
            toolbar.setNavigationIcon(toolbarIconId);
        }else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
