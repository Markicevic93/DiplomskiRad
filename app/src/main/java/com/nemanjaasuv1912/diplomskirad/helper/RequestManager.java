package com.nemanjaasuv1912.diplomskirad.helper;

import com.nemanjaasuv1912.diplomskirad.model.Student;

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
public abstract class RequestManager {

    private static final String STUDENT_ID_KEY      = "{student_id}";
    private static final String UNIVERSITY_ID_KEY   = "{university_id}";
    private static final String GROUP_ID_KEY        = "{group_id}";
    private static final String POST_ID_KEY         = "{post_id}";

    private static final String IP_ADRESS   = "192.168.0.13"; // with 3g 172.20.10.3
    private static final String API_BASE    = "http://" + IP_ADRESS + ":8080/api";
    private static final String UNIVERSITY  = API_BASE + "/university";
    private static final String REGISTER    = API_BASE + "/register";
    private static final String LOGIN       = API_BASE + "/login";
    private static final String STUDENT     = API_BASE + "/student/" + STUDENT_ID_KEY;
    private static final String GROUPS      = UNIVERSITY + "/" + UNIVERSITY_ID_KEY + "/groups";
    private static final String POSTS       = API_BASE + "/group/" + GROUP_ID_KEY + "/posts";
    private static final String POST        = API_BASE + "/group/" + GROUP_ID_KEY + "/post";
    private static final String COMMENTS    = API_BASE + "/post/" + POST_ID_KEY + "/comments";
    private static final String COMMENT     = API_BASE + "/post/" + POST_ID_KEY + "/comment";

    public void getUniversity(String studentEmail){
        HttpUrl.Builder urlBuilder = HttpUrl.parse(UNIVERSITY).newBuilder();
        urlBuilder.addQueryParameter("email", studentEmail);

        Request request = new Request.Builder().url(urlBuilder.build().toString()).build();

        newCall(request);
    }

    public void login(String email, String password){
        HttpUrl.Builder urlBuilder = HttpUrl.parse(LOGIN).newBuilder();
        urlBuilder.addQueryParameter("email", email);
        urlBuilder.addQueryParameter("password", password);

        Request request = new Request.Builder().url(urlBuilder.build().toString()).build();

        newCall(request);
    }

    public void register(String username, String password, String email, String year){
        RequestBody formBody = new FormBody.Builder()
                .add(Student.USERNAME_KEY, username)
                .add(Student.PASSWORD_KEY, password)
                .add(Student.EMAIL_KEY, email)
                .add(Student.YEAR_KEY, year)
                .build();

        Request request = new Request.Builder().url(REGISTER)
                .post(formBody)
                .build();

        newCall(request);
    }

    public void getStudent(int  studentId){
        Request request = new Request.Builder().url(STUDENT.replace(STUDENT_ID_KEY, studentId + "")).build();

        newCall(request);
    }

    public void getGroups(int universityId) {
        Request request = new Request.Builder().url(GROUPS.replace(UNIVERSITY_ID_KEY, universityId + "")).build();

        newCall(request);
    }

    public void getStudentForPost(int postId) {
        Request request = new Request.Builder().url(COMMENTS.replace(POST_ID_KEY, postId + "")).build();

        newCall(request);
    }

    public void getPostsForGroup(int groupId) {
        Request request = new Request.Builder().url(POSTS.replace(GROUP_ID_KEY, groupId + "")).build();

        newCall(request);
    }

    public void updateStudent(Student student){
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

    public void sendComment(String comment, int postId){
        Student student = Student.sharedStudent;
        RequestBody formBody = new FormBody.Builder()
                .add(Student.STUDENT_ID_KEY, student.getId() + "")
                .add(Student.PASSWORD_KEY, student.getPassword())
                .add(Student.TEXT_KEY, comment)
                .build();

        Request  request = new Request.Builder().url(COMMENT.replace(POST_ID_KEY, postId + ""))
                .post(formBody)
                .build();

        newCall(request);
    }

    public void createPost(String postTitle, String postText, int groupId){
        Student student = Student.sharedStudent;
        RequestBody formBody = new FormBody.Builder()
                .add(Student.STUDENT_ID_KEY, student.getId() + "")
                .add(Student.PASSWORD_KEY, student.getPassword())
                .add(Student.TITLE_KEY, postTitle)
                .add(Student.TEXT_KEY, postText)
                .add(Student.TYPE_KEY, "text") //type is not possible now
                .build();

        Request  request = new Request.Builder().url(POST.replace(GROUP_ID_KEY, groupId + ""))
                .post(formBody)
                .build();

        newCall(request);
    }

    private void newCall(Request request){
        final RequestManager requestManager = this;

        new OkHttpClient().newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) { requestManager.onFailure(); }

            @Override
            public void onResponse(Call call, final Response response) throws IOException { requestManager.onResponse(response.isSuccessful(),response); }
        });
    }

    abstract protected void onResponse(final boolean isSuccessful, final Response response);

    abstract protected void  onFailure();
}
