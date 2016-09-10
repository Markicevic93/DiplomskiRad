package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.Constants;
import com.nemanjaasuv1912.diplomskirad.helper.RequestManager;
import com.nemanjaasuv1912.diplomskirad.model.Group;
import com.nemanjaasuv1912.diplomskirad.model.University;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;
import com.nemanjaasuv1912.diplomskirad.ui.adapter.PostsAdapter;

import java.io.IOException;

import okhttp3.Response;

public class GroupActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        group = University.sharedUniversity.getGroup((getIntent().getExtras().getInt(Constants.GROUP_ID_KEY)));

        recyclerView = (RecyclerView) findViewById(R.id.group_groups_rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setToolbar(R.id.group_toolbar, group.getName());
    }

    @Override
    protected void onResume() {
        super.onResume();

        getGroupsPosts();
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

    private void getGroupsPosts() {
        new RequestManager(){
            @Override
            protected void onResponse(boolean isSuccessful, Response response) {
                if (isSuccessful){
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
            }
        }.getPostsForGroup(group.getId());
    }
}
