package com.nemanjaasuv1912.systemForCollaborativeLearning.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.nemanjaasuv1912.systemForCollaborativeLearning.R;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.Constants;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.alert.AlerDialog;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.alert.AlertType;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.api.RequestManager;
import com.nemanjaasuv1912.systemForCollaborativeLearning.model.Group;
import com.nemanjaasuv1912.systemForCollaborativeLearning.model.University;
import com.nemanjaasuv1912.systemForCollaborativeLearning.ui.activity.base.ProgressBarActivity;
import com.nemanjaasuv1912.systemForCollaborativeLearning.ui.adapter.PostsAdapter;

import java.io.IOException;

import okhttp3.Response;

public class GroupActivity extends ProgressBarActivity {

    private RecyclerView recyclerView;
    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        group = University.sharedUniversity.getGroup((getIntent().getExtras().getInt(Constants.GROUP_ID_KEY)));

        setToolbar(R.id.group_toolbar, group.getName());

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.group_groups_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        getPostsForGroup();
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
            intent.putExtra(Constants.GROUP_ID_KEY, group.getId());
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getPostsForGroup() {
        showProgressBar();
        new RequestManager() {

            @Override
            protected void onResponse(boolean isSuccessful, Response response) {
                hideProgressBar();
                if (isSuccessful) {
                    try {
                        group.parsePosts(response.body().string());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(new PostsAdapter(group.getPosts(), group.getId()));
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onFailure() {
                hideProgressBar();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlerDialog.showAlert(context, AlertType.REQUEST_ERROR);
                    }
                });
            }
        }.getPostsForGroup(group.getId());
    }
}
