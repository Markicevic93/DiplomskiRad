package com.nemanjaasuv1912.diplomskirad.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.model.Comment;

import java.util.ArrayList;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private ArrayList<Comment> comments;

    public CommentsAdapter(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_comment_view_holder, parent, false);

        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);

        holder.tvUsername.setText(comment.getStudent().getUsername());
        holder.tvUpdatedDate.setText(comment.getUpdatedDate());
        holder.tvUpdatedTime.setText(comment.getUpdatedTime());
        holder.tvText.setText(comment.getText());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvUsername, tvText, tvUpdatedDate, tvUpdatedTime;

        public CommentViewHolder(View v) {
            super(v);

            tvUpdatedDate = (TextView) v.findViewById(R.id.comment_updated_date);
            tvUpdatedTime = (TextView) v.findViewById(R.id.comment_updated_time);
            tvUsername = (TextView) v.findViewById(R.id.comment_username);
            tvText = (TextView) v.findViewById(R.id.comment_text);
        }
    }
}
