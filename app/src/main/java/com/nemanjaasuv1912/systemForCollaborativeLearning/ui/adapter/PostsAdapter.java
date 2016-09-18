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
import com.nemanjaasuv1912.systemForCollaborativeLearning.model.Post;
import com.nemanjaasuv1912.systemForCollaborativeLearning.ui.activity.PostActivity;

import java.util.ArrayList;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private ArrayList<Post> posts;
    private final int groupId;

    public PostsAdapter(ArrayList<Post> posts, int groupId) {
        this.posts = posts;
        this.groupId = groupId;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_post_view_holder, parent, false);

        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.tvTitle.setText(post.getTitle());
        holder.tvUpdated.setText(post.getUpdatedTime() + " " + post.getUpdatedDate());
        holder.tvCreator.setText(post.getStudent().getUsername());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView tvTitle, tvUpdated, tvCreator;

        public PostViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            tvTitle = (TextView) v.findViewById(R.id.post_title);
            tvUpdated = (TextView) v.findViewById(R.id.post_updated);
            tvCreator = (TextView) v.findViewById(R.id.post_creator);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MyApplication.getContext(), PostActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constants.POST_ID_KEY, posts.get(getAdapterPosition()).getId());
            intent.putExtra(Constants.GROUP_ID_KEY, groupId);
            MyApplication.getContext().startActivity(intent);
        }
    }
}
