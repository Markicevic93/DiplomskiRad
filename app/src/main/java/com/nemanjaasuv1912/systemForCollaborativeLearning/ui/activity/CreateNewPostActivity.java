package com.nemanjaasuv1912.systemForCollaborativeLearning.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ProgressBar;

import com.nemanjaasuv1912.systemForCollaborativeLearning.R;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.Constants;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.alert.AlerDialog;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.alert.AlertType;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.api.RequestManager;
import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.validator.EmptyEditTextValidator;
import com.nemanjaasuv1912.systemForCollaborativeLearning.ui.activity.base.ProgressBarActivity;

import okhttp3.Response;

public class CreateNewPostActivity extends ProgressBarActivity {

    private TextInputEditText etPostText, etPostTitle;
    private TextInputLayout tilPostText, tilPostTitle;
    private int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post);

        groupId = getIntent().getExtras().getInt(Constants.GROUP_ID_KEY);

        setToolbar(R.id.new_post_toolbar, getString(R.string.new_post_toolbar_title));

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        etPostText = (TextInputEditText) findViewById(R.id.new_post_text_et);
        etPostTitle = (TextInputEditText) findViewById(R.id.new_post_title_et);
        tilPostText = (TextInputLayout) findViewById(R.id.new_post_text_til);
        tilPostTitle = (TextInputLayout) findViewById(R.id.new_post_title_til);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return false;
    }

    public void createPostOnClick(View view) {
        tilPostTitle.setErrorEnabled(false);
        tilPostText.setErrorEnabled(false);

        boolean postTitleValid = EmptyEditTextValidator.isValid(etPostTitle.getText().toString(), tilPostTitle, getString(R.string.post_title_empty));
        boolean postTextValid = EmptyEditTextValidator.isValid(etPostText.getText().toString(), tilPostText, getString(R.string.post_text_empty));

        if (postTitleValid && postTextValid && !progressBarVisible()) {
            showProgressBar();
            new RequestManager() {

                @Override
                protected void onResponse(boolean isSuccessful, Response response) {
                    hideProgressBar();
                    if (isSuccessful) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onSupportNavigateUp();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlerDialog.showAlert(context, AlertType.CREATE_POST_FAILED);
                            }
                        });
                    }
                }

                @Override
                protected void onFailure() {
                    hideProgressBar();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlerDialog.showAlert(context, AlertType.REQUEST_ERROR);
                        }
                    });
                }
            }.createPost(etPostTitle.getText().toString(), etPostText.getText().toString(), groupId);
        }
    }
}
