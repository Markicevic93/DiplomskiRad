package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.Constants;
import com.nemanjaasuv1912.diplomskirad.model.Subject;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;
import com.nemanjaasuv1912.diplomskirad.ui.adapter.PostsAdapter;
import com.nemanjaasuv1912.diplomskirad.ui.view.TopicsButton;

public class SubjectActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private Subject subject;
    private TopicsButton tbHomework, tbTest, tbExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        recyclerView = (RecyclerView) findViewById(R.id.subject_rv_subject_items);
        tbHomework = (TopicsButton) findViewById(R.id.subject_tb_homework);
        tbTest = (TopicsButton) findViewById(R.id.subject_tb_test);
        tbExam = (TopicsButton) findViewById(R.id.subject_tb_exam);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        subject = Subject.getSubjectFromDatabase(getIntent().getExtras().getString(Constants.SUBJECT_NAME_KEY));

        setToolbar(R.id.subject_toolbar, subject.getName());
        recyclerView.setAdapter(new PostsAdapter(subject.getSubjectPosts()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent intent = new Intent(this, CreateNewPostActivity.class);
            intent.putExtra(Constants.SUBJECT_NAME_KEY, subject.getName());
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void homeworkOnClick(View view) {

        tbHomework.setSelected(!tbHomework.isSelected());
        tbTest.setSelected(false);
        tbExam.setSelected(false);
    }

    public void testOnClick(View view) {

        tbHomework.setSelected(false);
        tbTest.setSelected(!tbTest.isSelected());
        tbExam.setSelected(false);
    }

    public void examOnClick(View view) {

        tbHomework.setSelected(false);
        tbTest.setSelected(false);
        tbExam.setSelected(!tbExam.isSelected());
    }
}
