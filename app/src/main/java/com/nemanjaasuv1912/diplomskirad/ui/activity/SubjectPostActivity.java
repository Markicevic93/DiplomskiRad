package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.Constants;
import com.nemanjaasuv1912.diplomskirad.model.Post;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;
import com.nemanjaasuv1912.diplomskirad.ui.adapter.CommentsAdapter;
import com.nemanjaasuv1912.diplomskirad.ui.view.TagTextView;

public class SubjectPostActivity extends BaseActivity {


    private RecyclerView recyclerView;
    private Post subjectPost;
    private TextView postText;
    private LinearLayout tagsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_post);

        subjectPost = Post.getPostFromDatabase(getIntent().getExtras().getString(Constants.SUBJECT_POST_NAME_KEY));

        setToolbar(R.id.subject_post_toolbar, subjectPost.getTitle());

        recyclerView = (RecyclerView) findViewById(R.id.subject_post_rv_comments);
        postText = (TextView) findViewById(R.id.subject_post_text);
        tagsContainer = (LinearLayout) findViewById(R.id.subject_post_tags_container);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommentsAdapter(subjectPost.getComments()));

        postText.setText(subjectPost.getText());

        addTags();
    }

    private void addTags() {

        if(subjectPost.isTagExam()){

            tagsContainer.addView(new TagTextView(this,"Exam"));
        }

        if(subjectPost.isTagHomework()){

            tagsContainer.addView(new TagTextView(this,"Homework"));
        }

        if(subjectPost.isTagTest()){

            tagsContainer.addView(new TagTextView(this,"Test"));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
