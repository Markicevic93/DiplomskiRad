package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.alert.AlerDialog;
import com.nemanjaasuv1912.diplomskirad.helper.alert.AlertType;
import com.nemanjaasuv1912.diplomskirad.helper.api.RequestManager;
import com.nemanjaasuv1912.diplomskirad.model.University;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.ProgressBarActivity;
import com.nemanjaasuv1912.diplomskirad.ui.adapter.GroupAdapter;

import java.io.IOException;

import okhttp3.Response;

public class MainActivity extends ProgressBarActivity {

    private static final int GROUP_GRID_COLUMS = 2;

    private RecyclerView rvGroups;
    private University university;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        university = University.sharedUniversity;

        setToolbar(R.id.main_toolbar, university.getName(), R.drawable.profile);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        rvGroups = (RecyclerView) findViewById(R.id.main_groups_rv);
        rvGroups.setLayoutManager(new GridLayoutManager(this, GROUP_GRID_COLUMS));
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSubjects();
    }

    private void getSubjects() {
        showProgressBar();
        new RequestManager() {

            @Override
            protected void onResponse(boolean isSuccessful, Response response) {
                hideProgressBar();
                if (isSuccessful) {
                    try {
                        university.parseSubjects(response.body().string());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rvGroups.setAdapter(new GroupAdapter(university.getSelectedGroups()));
                            }
                        });

                    } catch (IOException ignored) {
                    }
                }
            }

            @Override
            protected void onFailure() {
                hideProgressBar();
                AlerDialog.showAlert(context, AlertType.REQUEST_ERROR);
            }
        }.getGroups(university.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            startActivity(ProfileActivity.class);
            return true;
        } else if (id == R.id.action_add) {
            startActivity(ChooseGroupActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
