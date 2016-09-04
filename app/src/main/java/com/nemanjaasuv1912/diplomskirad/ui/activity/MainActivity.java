package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.Constants;
import com.nemanjaasuv1912.diplomskirad.helper.MyRealm;
import com.nemanjaasuv1912.diplomskirad.model.Post;
import com.nemanjaasuv1912.diplomskirad.model.Subject;
import com.nemanjaasuv1912.diplomskirad.model.University;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;
import com.nemanjaasuv1912.diplomskirad.ui.adapter.SubjectsAdapter;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private RecyclerView rvSubjects;
    private ImageView ivUniversity;
    private University university;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        university = University.getUniversityFromDatabase();

        rvSubjects = (RecyclerView) findViewById(R.id.main_rv_subjects);
        GridLayoutManager layoutManager = new GridLayoutManager(this, Constants.SUBJECTS_GRID_COLUMS);
        rvSubjects.setLayoutManager(layoutManager);
        rvSubjects.setAdapter(new SubjectsAdapter(university.getSelectedSubjects()));

        setToolbar(R.id.main_toolbar,university.getName(),R.drawable.profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            startActivity(ProfileActivity.class);

            return true;
        }else if (id == R.id.action_add) {
            startActivity(ChooseSubjectsActivity.class);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
