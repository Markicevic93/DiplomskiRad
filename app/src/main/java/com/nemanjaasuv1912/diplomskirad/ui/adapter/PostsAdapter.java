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
import com.nemanjaasuv1912.diplomskirad.ui.activity.PostActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private ArrayList<Post> posts;
    private final int groupId;

    public PostsAdapter(ArrayList<Post> posts, int groupId){
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

        holder.tvPostItemTitle.setText(post.getTitle());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        protected TextView tvPostItemTitle;
        protected CircleImageView ivColor;

        public PostViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            tvPostItemTitle =  (TextView) v.findViewById(R.id.post_item_title);
            ivColor = (CircleImageView)  v.findViewById(R.id.post_item_color);
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
