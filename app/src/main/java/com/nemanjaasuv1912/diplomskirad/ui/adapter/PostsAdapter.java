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
import com.nemanjaasuv1912.diplomskirad.model.Post;
import com.nemanjaasuv1912.diplomskirad.ui.activity.SubjectPostActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.RealmList;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private RealmList<Post> posts;

    public PostsAdapter(RealmList<Post> posts){
        this.posts = posts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_post_view_holder, parent, false);

        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.tvSubjectItemTitle.setText(post.getTitle());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        protected TextView tvSubjectItemTitle;
        protected CircleImageView ivColor;

        public PostViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            tvSubjectItemTitle =  (TextView) v.findViewById(R.id.subject_item_title);
            ivColor = (CircleImageView)  v.findViewById(R.id.subject_item_color);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MyApplication.getContext(), SubjectPostActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constants.SUBJECT_POST_NAME_KEY, tvSubjectItemTitle.getText().toString());
            MyApplication.getContext().startActivity(intent);
        }
    }

}
