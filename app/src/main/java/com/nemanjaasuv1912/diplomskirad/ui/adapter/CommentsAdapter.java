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

    public CommentsAdapter(ArrayList<Comment> comments){
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

        holder.tvCommentUsername.setText(comment.getStudent().getUsername());
        holder.tvCommentText.setText(comment.getText());
        holder.tvCommentUpdatedDate.setText(comment.getUpdatedDate());
        holder.tvCommentUpdatedTime.setText(comment.getUpdatedTime());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvCommentUsername;
        protected TextView tvCommentText;
        protected TextView tvCommentUpdatedDate;
        protected TextView tvCommentUpdatedTime;

        public CommentViewHolder(View v) {
            super(v);

            tvCommentUsername =  (TextView) v.findViewById(R.id.post_comment_username);
            tvCommentText =  (TextView) v.findViewById(R.id.post_comment_text);
            tvCommentUpdatedDate = (TextView)  v.findViewById(R.id.post_comment_updated_date);
            tvCommentUpdatedTime = (TextView)  v.findViewById(R.id.post_comment_updated_time);
        }
    }
}
