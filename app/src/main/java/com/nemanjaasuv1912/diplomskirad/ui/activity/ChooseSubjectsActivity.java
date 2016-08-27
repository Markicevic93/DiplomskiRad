package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.Constants;
import com.nemanjaasuv1912.diplomskirad.model.University;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;
import com.nemanjaasuv1912.diplomskirad.ui.adapter.ChooseSubjectsAdapter;
import com.nemanjaasuv1912.diplomskirad.ui.adapter.SubjectsAdapter;

public class ChooseSubjectsActivity extends BaseActivity {

    private RecyclerView rvSubjects;
    private University university;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subjects);

        university = University.getUniversityFromDatabase();

        setToolbar(R.id.choose_subjects_toolbar,getString(R.string.choose_subjects_toolbar_title));

        rvSubjects = (RecyclerView) findViewById(R.id.subjects_rv);
        rvSubjects.setLayoutManager(new LinearLayoutManager(this));
        rvSubjects.setAdapter(new ChooseSubjectsAdapter(university.getSubjects()));
    }
}
