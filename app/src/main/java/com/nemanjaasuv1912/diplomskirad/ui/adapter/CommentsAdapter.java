package com.nemanjaasuv1912.diplomskirad.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.model.Comment;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.RealmList;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private RealmList<Comment> comments;

    public CommentsAdapter(RealmList<Comment> comments){
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

        holder.tvSubjectPostCommentUsername.setText(comment.getUsername());
        holder.tvSubjectPostCommentText.setText(comment.getText());
        holder.tvSubjectPostCommentText.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ut volutpat ante. Sed at sem vel nunc tincidunt molestie. Vestibulum vitae mi erat. Vivamus fermentum, ante ac luctus congue, enim neq");
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvSubjectPostCommentUsername;
        protected TextView tvSubjectPostCommentText;
        protected CircleImageView ivSubjectPostCommenProfileImage;

        public CommentViewHolder(View v) {
            super(v);

            tvSubjectPostCommentUsername =  (TextView) v.findViewById(R.id.subject_post_comment_username);
            tvSubjectPostCommentText =  (TextView) v.findViewById(R.id.subject_post_comment_text);
            ivSubjectPostCommenProfileImage = (CircleImageView)  v.findViewById(R.id.subject_post_comment_profile);
        }

    }

}
