package com.nemanjaasuv1912.diplomskirad.ui.adapter;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nemanjaasuv1912.diplomskirad.MyApplication;
import com.nemanjaasuv1912.diplomskirad.R;
import com.nemanjaasuv1912.diplomskirad.model.Group;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class ChooseGroupsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int YEAR_TYPE = 0;
    private static final int GROUP_TYPE = 1;

    private ArrayList<Group> groups;
    private ArrayList<Group> items;

    public ChooseGroupsAdapter(ArrayList<Group> groups){
        this.groups = groups;
        items = new ArrayList<>();

        for (int i = 0; i < groups.size(); i++){
            if(i == 0 || (groups.get(i).getYear() > groups.get(i - 1).getYear())){
                items.add(groups.get(i));
            }

            items.add(groups.get(i));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == YEAR_TYPE){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_choose_group_view_holder_year, parent, false);
            return new YearViewHolder(itemView);
        }else{
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_choose_group_view_holder_group, parent, false);
            return new GroupViewHolder(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return YEAR_TYPE;
        }

        if(items.get(position).getYear() > items.get(position - 1).getYear()){
            return YEAR_TYPE;
        }

        return GROUP_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Group group = items.get(position );

        if(getItemViewType(position) == YEAR_TYPE){
            YearViewHolder yearViewHolder = (YearViewHolder)holder;
            yearViewHolder.tvYear.setText(group.getYearAsString());
        }else{
            GroupViewHolder groupViewHolder = (GroupViewHolder)holder;
            groupViewHolder.tvFullName.setText(group.getName());
            groupViewHolder.setSelected(group.isSelected());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView tvFullName;
        protected CircleImageView civColor;

        public GroupViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            tvFullName =  (TextView) v.findViewById(R.id.group_fullname);
            civColor = (CircleImageView)  v.findViewById(R.id.group_color);
        }

        @Override
        public void onClick(View v) {
            Group group = items.get(getAdapterPosition());
            group.setSelected(!group.isSelected());
            setSelected(group.isSelected());
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

    public class YearViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView tvYear;
        private boolean selected = true;

        public YearViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            tvYear = (TextView)v.findViewById(R.id.group_year);
        }

        @Override
        public void onClick(View v) {
            int currentPosition = getAdapterPosition() + 1;

            while (currentPosition < items.size() &&
                    items.get(getAdapterPosition()).getYear() == items.get(currentPosition).getYear()){
                items.get(currentPosition++).setSelected(selected);
            }

            selected = ! selected;

            notifyDataSetChanged();
        }
    }
}
