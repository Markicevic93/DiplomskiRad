package com.nemanjaasuv1912.diplomskirad.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nemanjaasuv1912.diplomskirad.MyApplication;
import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.Constants;
import com.nemanjaasuv1912.diplomskirad.model.SubjectPost;
import com.nemanjaasuv1912.diplomskirad.ui.activity.SubjectActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.RealmList;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class SubjectPostsAdapter extends RecyclerView.Adapter<SubjectPostsAdapter.SubjectPostViewHolder> {

    private RealmList<SubjectPost> subjectPosts;

    public SubjectPostsAdapter(RealmList<SubjectPost> subjectPosts){
        this.subjectPosts = subjectPosts;
    }

    @Override
    public SubjectPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_subject_post_view_holder, parent, false);

        return new SubjectPostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubjectPostViewHolder holder, int position) {
        SubjectPost subjectPost = subjectPosts.get(position);

        holder.tvSubjectItemTitle.setText(subjectPost.getName());
    }

    @Override
    public int getItemCount() {
        return subjectPosts.size();
    }

    public class SubjectPostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView tvSubjectItemTitle;
        protected CircleImageView ivColor;

        public SubjectPostViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            tvSubjectItemTitle =  (TextView) v.findViewById(R.id.subject_item_title);
            ivColor = (CircleImageView)  v.findViewById(R.id.subject_item_color);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MyApplication.getContext(), SubjectActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constants.SUBJECT_ITEM_NAME_KEY, tvSubjectItemTitle.getText().toString());
            MyApplication.getContext().startActivity(intent);
        }
    }

}
