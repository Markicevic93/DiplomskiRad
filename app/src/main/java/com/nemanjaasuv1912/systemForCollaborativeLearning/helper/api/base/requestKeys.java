package com.nemanjaasuv1912.systemForCollaborativeLearning.helper.api.base;

/**
 * Created by nemanjamarkicevic on 9/12/16.
 */
public interface RequestKeys {

    String STUDENT_ID_KEY = "{student_id}";
    String UNIVERSITY_ID_KEY = "{university_id}";
    String GROUP_ID_KEY = "{group_id}";
    String POST_ID_KEY = "{post_id}";

    String IP_ADRESS = "192.168.0.12"; // with 3g 172.20.10.3
    String API_BASE = "http://" + IP_ADRESS + ":8080/api";
    String UNIVERSITY = API_BASE + "/university";
    String REGISTER = API_BASE + "/register";
    String LOGIN = API_BASE + "/login";
    String STUDENT = API_BASE + "/student/" + STUDENT_ID_KEY;
    String GROUPS = UNIVERSITY + "/" + UNIVERSITY_ID_KEY + "/groups";
    String POSTS = API_BASE + "/group/" + GROUP_ID_KEY + "/posts";
    String POST = API_BASE + "/group/" + GROUP_ID_KEY + "/post";
    String COMMENTS = API_BASE + "/post/" + POST_ID_KEY + "/comments";
    String COMMENT = API_BASE + "/post/" + POST_ID_KEY + "/comment";
}
