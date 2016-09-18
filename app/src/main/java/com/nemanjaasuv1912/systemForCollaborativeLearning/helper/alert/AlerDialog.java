package com.nemanjaasuv1912.systemForCollaborativeLearning.helper.alert;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.nemanjaasuv1912.systemForCollaborativeLearning.R;

public class AlerDialog {

    public static void showAlert(Context context, AlertType type) {
        switch (type) {
            case REQUEST_ERROR:
                showRequestErrorAlert(context);
                break;
            case LOGIN_FAILED:
                showLoginFailedAlert(context);
                break;
            case CREATE_POST_FAILED:
                showCreatePostFailedAlert(context);
                break;
            case ADD_COMMENT_FAILED:
                showAddingCommentFailedAlert(context);
                break;
            default:
        }
    }

    private static void showRequestErrorAlert(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.alert_request_error_title));
        builder.setMessage(context.getString(R.string.alert_request_error_text));
        builder.setPositiveButton(context.getString(R.string.alert_ok_button_title), null);
        builder.show();
    }

    private static void showLoginFailedAlert(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.alert_login_failed_title));
        builder.setMessage(context.getString(R.string.alert_login_failed_text));
        builder.setPositiveButton(context.getString(R.string.alert_ok_button_title), null);
        builder.show();
    }

    private static void showCreatePostFailedAlert(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.alert_create_post_failed_title));
        builder.setMessage(context.getString(R.string.alert_create_post_failed_text));
        builder.setPositiveButton(context.getString(R.string.alert_ok_button_title), null);
        builder.show();
    }

    private static void showAddingCommentFailedAlert(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.alert_add_comment_failed_title));
        builder.setMessage(context.getString(R.string.alert_add_comment_failed_text));
        builder.setPositiveButton(context.getString(R.string.alert_ok_button_title), null);
        builder.show();
    }
}
