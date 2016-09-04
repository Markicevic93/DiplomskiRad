package com.nemanjaasuv1912.diplomskirad.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.Constants;
import com.nemanjaasuv1912.diplomskirad.helper.MyRealm;
import com.nemanjaasuv1912.diplomskirad.model.Post;
import com.nemanjaasuv1912.diplomskirad.model.Subject;
import com.nemanjaasuv1912.diplomskirad.model.University;
import com.nemanjaasuv1912.diplomskirad.ui.activity.base.BaseActivity;

import java.util.ArrayList;

public class CreateNewPostActivity extends BaseActivity {

    private ImageView ivAddPhoto;
    private SwitchCompat scHomework, scTest, scExam;
    private EditText etPostText, etPostTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post);

        ivAddPhoto = (ImageView) findViewById(R.id.new_post_add_photo_iv);
        scHomework = (SwitchCompat) findViewById(R.id.new_post_homework_sc);
        scTest = (SwitchCompat) findViewById(R.id.new_post_test_sc);
        scExam = (SwitchCompat) findViewById(R.id.new_post_exam_sc);
        etPostText = (EditText) findViewById(R.id.new_post_text_et);
        etPostTitle = (EditText) findViewById(R.id.new_post_title_et);

        setToolbar(R.id.new_post_toolbar,getResources().getString(R.string.new_post_toolbar_title));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void createPostOnClick(View view) {

        //// TODO: 9/4/16 Do a validation

        Subject subject = Subject.getSubjectFromDatabase(getIntent().getExtras().getString(Constants.SUBJECT_NAME_KEY));
        Post post = new Post(etPostTitle.getText().toString(), etPostText.getText().toString());
        post.setTagHomework(scHomework.isChecked());
        post.setTagExam(scExam.isChecked());
        post.setTagTest(scTest.isChecked());

        MyRealm.getRealm().beginTransaction();
        subject.addPost(post);
        MyRealm.getRealm().commitTransaction();

        onBackPressed();
    }
}
