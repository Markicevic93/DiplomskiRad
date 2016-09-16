package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.model.University;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;
import com.nemanjaasuv1912.diplomskirad.ui.adapter.ChooseGroupsAdapter;

public class ChooseGroupActivity extends BaseActivity {

    private RecyclerView rvGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_groups);

        setToolbar(R.id.choose_group_toolbar, getString(R.string.choose_groups_toolbar_title));

        rvGroups = (RecyclerView) findViewById(R.id.choose_groups_groups_rv);
        rvGroups.setLayoutManager(new LinearLayoutManager(this));
        rvGroups.setAdapter(new ChooseGroupsAdapter(University.sharedUniversity.getGroup()));
    }
}
