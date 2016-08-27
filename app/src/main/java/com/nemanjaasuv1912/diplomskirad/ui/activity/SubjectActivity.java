package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.Constants;
import com.nemanjaasuv1912.diplomskirad.model.Subject;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;
import com.nemanjaasuv1912.diplomskirad.ui.adapter.SubjectItemsAdapter;
import com.nemanjaasuv1912.diplomskirad.ui.view.TopicsButton;

public class SubjectActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private Subject subject;
    private TopicsButton tbHomework, tbTest, tbExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        subject = Subject.getSubjectFromDatabase(getIntent().getExtras().getString(Constants.SUBJECT_NAME_KEY));

        recyclerView = (RecyclerView) findViewById(R.id.subject_rv_subject_items);
        tbHomework = (TopicsButton) findViewById(R.id.subject_tb_homework);
        tbTest = (TopicsButton) findViewById(R.id.subject_tb_test);
        tbExam = (TopicsButton) findViewById(R.id.subject_tb_exam);

        setToolbar(R.id.subject_toolbar, subject.getFullName());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SubjectItemsAdapter(subject.getSubjectItems()));
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
            //// TODO: 8/14/16 add onClick
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void homeworkOnClick(View view) {

        tbHomework.setSelected(true);
        tbTest.setSelected(false);
        tbExam.setSelected(false);
    }

    public void testOnClick(View view) {

        tbHomework.setSelected(false);
        tbTest.setSelected(true);
        tbExam.setSelected(false);
    }

    public void examOnClick(View view) {

        tbHomework.setSelected(false);
        tbTest.setSelected(false);
        tbExam.setSelected(true);
    }
}
