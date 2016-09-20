package com.nemanjaasuv1912.systemForCollaborativeLearning.helper.api;

import android.util.Log;

import com.nemanjaasuv1912.systemForCollaborativeLearning.helper.api.base.RequestKeys;
import com.nemanjaasuv1912.systemForCollaborativeLearning.model.Student;
import com.nemanjaasuv1912.systemForCollaborativeLearning.model.University;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by nemanjamarkicevic on 9/10/16.
 */
public abstract class RequestManager implements RequestKeys {

    public void getUniversity(String studentEmail) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(UNIVERSITY).newBuilder();
        urlBuilder.addQueryParameter(University.EMAIL_KEY, studentEmail);

        newCall(new Request.Builder().url(urlBuilder.build().toString()).build());
    }

    public void getGroups(int universityId) {
        newCall(new Request.Builder().url(GROUPS.replace(UNIVERSITY_ID_KEY, universityId + "")).build());
    }

    public void getCommentsForPost(int postId) {
        newCall(new Request.Builder().url(COMMENTS.replace(POST_ID_KEY, postId + "")).build());
    }

    public void getPostsForGroup(int groupId) {
        newCall(new Request.Builder().url(POSTS.replace(GROUP_ID_KEY, groupId + "")).build());
    }

    public void login(String email, String password) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(LOGIN).newBuilder();
        urlBuilder.addQueryParameter(Student.EMAIL_KEY, email);
        urlBuilder.addQueryParameter(Student.PASSWORD_KEY, password);

        newCall(new Request.Builder().url(urlBuilder.build().toString()).build());
    }

    public void register(String username, String password, String email, String year) {
        RequestBody formBody = new FormBody.Builder()
                .add(Student.USERNAME_KEY, username)
                .add(Student.PASSWORD_KEY, password)
                .add(Student.EMAIL_KEY, email)
                .add(Student.YEAR_KEY, year)
                .build();

        newCall(new Request.Builder().url(REGISTER).post(formBody).build());
    }

    public void updateStudent(Student student) {
        RequestBody formBody = new FormBody.Builder()
                .add(Student.PASSWORD_KEY, student.getPassword())
                .add(Student.YEAR_KEY, student.getYearAsString())
                .add(Student.FULLNAME_KEY, student.getFullname())
                .add(Student.BIRTHDATE_KEY, student.getBirthdate())
                .add(Student.ABOUT_KEY, student.getAboutMe())
                .build();

        Request request = new Request.Builder().url(STUDENT.replace(STUDENT_ID_KEY, student.getId() + ""))
                .post(formBody)
                .build();

        newCall(request);
    }

    public void sendComment(String comment, int postId) {
        Student student = Student.sharedStudent;
        RequestBody formBody = new FormBody.Builder()
                .add(Student.STUDENT_ID_KEY, student.getId() + "")
                .add(Student.PASSWORD_KEY, student.getPassword())
                .add(Student.TEXT_KEY, comment)
                .build();

        Request request = new Request.Builder().url(COMMENT.replace(POST_ID_KEY, postId + ""))
                .post(formBody)
                .build();

        newCall(request);
    }

    public void createPost(String postTitle, String postText, int groupId) {
        Student student = Student.sharedStudent;
        RequestBody formBody = new FormBody.Builder()
                .add(Student.STUDENT_ID_KEY, student.getId() + "")
                .add(Student.PASSWORD_KEY, student.getPassword())
                .add(Student.TITLE_KEY, postTitle)
                .add(Student.TEXT_KEY, postText)
                .add(Student.TYPE_KEY, "text") //type is not possible now
                .build();

        Request request = new Request.Builder().url(POST.replace(GROUP_ID_KEY, groupId + ""))
                .post(formBody)
                .build();

        newCall(request);
    }

    private void newCall(Request request) {
        final RequestManager requestManager = this;

        Log.i("url",request.url().toString());
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                requestManager.onResponse(response.isSuccessful(), response);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                requestManager.onFailure();
            }
        });
    }

    abstract protected void onResponse(final boolean isSuccessful, final Response response);

    abstract protected void onFailure();
}
