package com.nemanjaasuv1912.diplomskirad.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nemanjaasuv1912.diplomskirad.MyApplication;
import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.Constants;
import com.nemanjaasuv1912.diplomskirad.model.Subject;
import com.nemanjaasuv1912.diplomskirad.model.SubjectItem;
import com.nemanjaasuv1912.diplomskirad.ui.activity.SignupActivity;
import com.nemanjaasuv1912.diplomskirad.ui.activity.SubjectActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.RealmList;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class SubjectItemsAdapter extends RecyclerView.Adapter<SubjectItemsAdapter.SubjectItemViewHolder> {

    private RealmList<SubjectItem> subjectItems;

    public SubjectItemsAdapter(RealmList<SubjectItem> subjectItems){
        this.subjectItems = subjectItems;
    }

    @Override
    public SubjectItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_subject_item_view_holder, parent, false);

        return new SubjectItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubjectItemViewHolder holder, int position) {
        SubjectItem subjectItem = subjectItems.get(position);

        holder.tvSubjectItemTitle.setText(subjectItem.getName());
    }

    @Override
    public int getItemCount() {
        return subjectItems.size();
    }

    public class SubjectItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView tvSubjectItemTitle;
        protected CircleImageView ivColor;

        public SubjectItemViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            tvSubjectItemTitle =  (TextView) v.findViewById(R.id.subject_item_title);
            ivColor = (CircleImageView)  v.findViewById(R.id.subject_item_color);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MyApplication.getContext(), SubjectActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constants.SUBJECT_ITEM_NAME_KEY, tvSubjectItemTitle.getText().toString());
            MyApplication.getContext().startActivity(intent);
        }
    }

}
