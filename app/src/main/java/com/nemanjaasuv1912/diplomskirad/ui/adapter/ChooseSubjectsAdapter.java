package com.nemanjaasuv1912.diplomskirad.ui.adapter;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nemanjaasuv1912.diplomskirad.MyApplication;
import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.helper.MyRealm;
import com.nemanjaasuv1912.diplomskirad.model.Subject;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.RealmList;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class ChooseSubjectsAdapter extends RecyclerView.Adapter<ChooseSubjectsAdapter.SubjectViewHolder> {

    private RealmList<Subject> subjects;

    public ChooseSubjectsAdapter(RealmList<Subject> subjects){
        this.subjects = subjects;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_choose_subject_view_holder, parent, false);
        return new SubjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder holder, int position) {
        Subject subject = subjects.get(position);

        holder.tvFullName.setText(subject.getName());
        holder.setSelected(subject.isSelected());
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView tvFullName;
        protected CircleImageView civColor;

        public SubjectViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            tvFullName =  (TextView) v.findViewById(R.id.subject_fullname);
            civColor = (CircleImageView)  v.findViewById(R.id.subject_color);
        }

        @Override
        public void onClick(View v) {
            Subject subject = subjects.get(getAdapterPosition());

            MyRealm.getRealm().beginTransaction();
            subject.setSelected(!subject.isSelected());
            MyRealm.getRealm().commitTransaction();

            setSelected(subject.isSelected());
        }

        public void setSelected(boolean selected){
            if(selected){
                tvFullName.setTextColor(getColor(android.R.color.black));
                civColor.setImageDrawable(new ColorDrawable(getColor(android.R.color.holo_red_dark)));
            }else{
                tvFullName.setTextColor(getColor(android.R.color.darker_gray));
                civColor.setImageDrawable(new ColorDrawable(getColor(android.R.color.darker_gray)));
            }
        }

        private int getColor(int colorId) {
            return MyApplication.getContext().getResources().getColor(colorId);
        }
    }
}
