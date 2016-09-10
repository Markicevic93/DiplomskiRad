package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.Constants;
import com.nemanjaasuv1912.diplomskirad.helper.RequestManager;
import com.nemanjaasuv1912.diplomskirad.helper.validator.EmptyEditTextValidator;
import com.nemanjaasuv1912.diplomskirad.model.Comment;
import com.nemanjaasuv1912.diplomskirad.model.Post;
import com.nemanjaasuv1912.diplomskirad.model.University;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;
import com.nemanjaasuv1912.diplomskirad.ui.adapter.CommentsAdapter;

import java.io.IOException;

import okhttp3.Response;

public class PostActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private Post post;
    private TextView tvPostText;
    private EditText etComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        post = University.sharedUniversity
                .getGroup(getIntent().getExtras().getInt(Constants.GROUP_ID_KEY))
                .getPost(getIntent().getExtras().getInt(Constants.POST_ID_KEY));

        setToolbar(R.id.post_toolbar, post.getTitle());

        recyclerView = (RecyclerView) findViewById(R.id.post_rv_comments);
        tvPostText = (TextView) findViewById(R.id.post_text);
        etComment = (EditText) findViewById(R.id.post_comment_et);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvPostText.setText(post.getText());

        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    etComment.setError(null);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getComments();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


    private void getComments() {
        new RequestManager(){
            @Override
            protected void onResponse(boolean isSuccessful, Response response) {
                if (isSuccessful){
                    try {
                        post.parseComments(response.body().string());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(new CommentsAdapter(post.getComments()));
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onFailure() {
            }
        }.getStudentForPost(post.getId());
    }

    public void sendCommentOnClick(View view) {
        if(EmptyEditTextValidator.isValid(etComment.getText().toString())){
            new RequestManager(){

                @Override
                protected void onResponse(boolean isSuccessful, Response response) {

                    if(isSuccessful){
                        try {
                            post.addComment(Comment.parse(response.body().string()));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    recyclerView.getAdapter().notifyDataSetChanged();
                                    recyclerView.getLayoutManager().scrollToPosition(0);
                                    etComment.setText("");
                                }
                            });
                        } catch (IOException ignored) {}
                    }
                }

                @Override
                protected void onFailure() {

                }
            }.sendComment(etComment.getText().toString(), post.getId());
        }else{
            etComment.setError(getResources().getString(R.string.comment_empty));
        }

    }
}
