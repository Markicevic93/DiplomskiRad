package com.nemanjaasuv1912.diplomskirad.model;

import com.nemanjaasuv1912.diplomskirad.helper.MyRealm;

import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class Post extends RealmObject {

    private String title;
    private String text;
    private Boolean tagExam;
    private Boolean tagHomework;
    private Boolean tagTest;
    private String created;
    private String updated;
    private RealmList<Comment> comments;

    public Post(){
        title = "";
        text = "";
        created = "";
        updated = "";
        tagExam = false;
        tagHomework = false;
        tagTest = false;
        comments =  new RealmList<>();
    }

    public Post(String title, String text){
        this.title = title;
        this.text = text;
        created = "";
        updated = "";
        tagExam = false;
        tagHomework = false;
        tagTest = false;
        comments =  new RealmList<>();
    }

    public Post(Post item){
        title = item.getTitle();
        text = item.getText();
        tagExam = item.isTagExam();
        tagHomework = item.isTagHomework();
        tagTest = item.isTagTest();
        comments =  item.getComments();
    }

    public static Post getPostFromDatabase(String name){
        return getPostFromDatabase(MyRealm.getRealm(), name);
    }

    public static Post getPostFromDatabase(Realm realm, String title){
        Post post = realm.where(Post.class).equalTo("title",title).findFirst();

        if(post == null){
            realm.beginTransaction();
            post = realm.createObject(Post.class);

            post.title = "";
            post.text = "";
            post.created = "";
            post.updated = "";
            post.tagExam = false;
            post.tagHomework = false;
            post.tagTest = false;
            post.comments =  new RealmList<>();

            realm.commitTransaction();
        }

        return post;
    }

    public static void updatePostInDatabaseAsync(final Post newPost) {
        MyRealm.getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Post post = getPostFromDatabase(realm,newPost.title);

                post.title = newPost.title;
                post.tagExam = newPost.tagExam;
                post.tagHomework = newPost.tagHomework;
                post.tagTest = newPost.tagTest;
                post.created = newPost.created;
                post.updated = newPost.updated;

                Collections.copy(post.comments, newPost.comments);
            }
        });
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public RealmList<Comment> getComments() {
        return comments;
    }

    public Boolean isTagExam() {
        return tagExam;
    }

    public Boolean isTagHomework() {
        return tagHomework;
    }

    public Boolean isTagTest() {
        return tagTest;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTagExam(Boolean tagExam) {
        this.tagExam = tagExam;
    }

    public void setTagHomework(Boolean tagHomework) {
        this.tagHomework = tagHomework;
    }

    public void setTagTest(Boolean tagTest) {
        this.tagTest = tagTest;
    }

    public void setComments(RealmList<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
