package com.nemanjaasuv1912.diplomskirad.ui.adapter;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nemanjaasuv1912.diplomskirad.MyApplication;
import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.Constants;
import com.nemanjaasuv1912.diplomskirad.model.Subject;
import com.nemanjaasuv1912.diplomskirad.model.University;
import com.nemanjaasuv1912.diplomskirad.ui.activity.MainActivity;
import com.nemanjaasuv1912.diplomskirad.ui.activity.SubjectActivity;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder> {

    private RealmList<Subject> selectedSubjects;

    public SubjectsAdapter(RealmList<Subject> selectedSubjects){
        this.selectedSubjects = selectedSubjects;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_subject_view_holder, parent, false);

        return new SubjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder holder, int position) {
        Subject subject = selectedSubjects.get(position);

        holder.tvName.setText(subject.getName());
        holder.tvNewItems.setText(String.format( MyApplication.getContext().getString(R.string.newItems), subject.getNewItemsCount()));
    }

    @Override
    public int getItemCount() {
        return selectedSubjects.size();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView tvName;
        protected TextView tvNewItems;
        protected ImageView ivColor;

        public SubjectViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            tvName =  (TextView) v.findViewById(R.id.subject_name);
            tvNewItems = (TextView)  v.findViewById(R.id.subject_new_items);
            ivColor = (ImageView)  v.findViewById(R.id.subject_color);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MyApplication.getContext(), SubjectActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constants.SUBJECT_NAME_KEY, tvName.getText().toString());
            MyApplication.getContext().startActivity(intent);
        }
    }

}
