package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.Constants;
import com.nemanjaasuv1912.diplomskirad.helper.RequestManager;
import com.nemanjaasuv1912.diplomskirad.helper.validator.EmptyEditTextValidator;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;

import okhttp3.Response;

public class CreateNewPostActivity extends BaseActivity {

    private TextInputEditText etPostText, etPostTitle;
    private TextInputLayout tilPostText, tilPostTitle;
    private int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post);

        etPostText = (TextInputEditText) findViewById(R.id.new_post_text_et);
        etPostTitle = (TextInputEditText) findViewById(R.id.new_post_title_et);

        tilPostText = (TextInputLayout) findViewById(R.id.new_post_text_til);
        tilPostTitle = (TextInputLayout) findViewById(R.id.new_post_title_til);

        groupId = getIntent().getExtras().getInt(Constants.GROUP_ID_KEY);

        setToolbar(R.id.new_post_toolbar,getString(R.string.new_post_toolbar_title));
       }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void createPostOnClick(View view) {
        tilPostTitle.setErrorEnabled(false);
        tilPostText.setErrorEnabled(false);

        if(EmptyEditTextValidator.isValid(etPostTitle.getText().toString())){
            if(EmptyEditTextValidator.isValid(etPostText.getText().toString())){
                new RequestManager(){

                    @Override
                    protected void onResponse(boolean isSuccessful, Response response) {

                        if(isSuccessful) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    onSupportNavigateUp();
                                }
                            });
                        }
                    }

                    @Override
                    protected void onFailure() {

                    }
                }.createPost(etPostTitle.getText().toString(), etPostText.getText().toString(), groupId);
            } else {
                tilPostText.setError(getString(R.string.post_text_empty));
            }
        } else {
            tilPostTitle.setError(getString(R.string.post_title_empty));
        }
    }
}
