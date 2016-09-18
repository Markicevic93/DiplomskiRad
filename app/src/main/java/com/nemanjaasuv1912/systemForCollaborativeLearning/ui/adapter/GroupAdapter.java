package com.nemanjaasuv1912.systemForCollaborativeLearning.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nemanjaasuv1912.systemForCollaborativeLearning.MyApplication;
import com.nemanjaasuv1912.systemForCollaborativeLearning.R;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.Constants;
import com.nemanjaasuv1912.systemForCollaborativeLearning.model.Group;
import com.nemanjaasuv1912.systemForCollaborativeLearning.ui.activity.GroupActivity;

import java.util.ArrayList;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private ArrayList<Group> selectedGroups;

    public GroupAdapter(ArrayList<Group> selectedGroups) {
        this.selectedGroups = selectedGroups;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main_group_view_holder, parent, false);

        return new GroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        Group group = selectedGroups.get(position);

        holder.tvShortName.setText(group.getShortName());
        holder.tvYear.setText(group.getYearAsString());
        holder.tvName.setText(group.getName());
    }

    @Override
    public int getItemCount() {
        return selectedGroups.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView tvShortName, tvName, tvYear;

        public GroupViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            tvShortName = (TextView) v.findViewById(R.id.group_short_name);
            tvName = (TextView) v.findViewById(R.id.group_name);
            tvYear = (TextView) v.findViewById(R.id.group_year);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MyApplication.getContext(), GroupActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constants.GROUP_ID_KEY, selectedGroups.get(getAdapterPosition()).getId());
            MyApplication.getContext().startActivity(intent);
        }
    }
}
