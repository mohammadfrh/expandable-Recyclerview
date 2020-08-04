package com.frh.expandrecyclerview;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class ExpandableTestAdapter extends ExpandableRecyclerView.Adapter<ExpandableTestAdapter.ChildViewHolder, ExpandableRecyclerView.SimpleGroupViewHolder, String, String> {


    public ExpandableTestAdapter() {
    }


    @Override
    public int getGroupItemCount() {
        return DataSample.id.length;
    }

    @Override
    public int getChildItemCount(int group) {
        return 1;
    }

    @Override
    public String getGroupItem(int position) {
        Log.e("tag", "getGroupItem: " + position);
        if (position <= DataSample.id.length) {

            return DataSample.titleArray[position];
        } else
            return "group :" + position;
    }

    @Override
    public String getChildItem(int group, int position) {

        return DataSample.descriptionArray[group];

    }

    @Override
    protected ExpandableRecyclerView.SimpleGroupViewHolder onCreateGroupViewHolder(ViewGroup parent) {
        return new ExpandableRecyclerView.SimpleGroupViewHolder(parent.getContext());
    }

    @Override
    protected ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_row_item, parent, false);
        return new ChildViewHolder(view);
    }

    @Override
    public int getChildItemViewType(int group, int position) {
        return 1;
    }

    @Override
    public void onBindGroupViewHolder(ExpandableRecyclerView.SimpleGroupViewHolder holder, int group) {
        super.onBindGroupViewHolder(holder, group);
        holder.setTextviewGroupText(getGroupItem(group));
        holder.setimageviewUser(DataSample.drawableArray[group]);
        holder.settextviewGroupDescription(DataSample.stateArray[group]);

        if (DataSample.type[group].equals(1)) {
            holder.setStyle((Color.parseColor("#FFFFFF")),
                    R.drawable.background_list_selected_messages,
                    true,
                    Color.parseColor("#FFFFFF"),
                    Color.parseColor("#FFFFFF"),
                    Color.parseColor("#FFFFFF")
            );

        }
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int group, final int position) {
        super.onBindChildViewHolder(holder, group, position);
        holder.tv.setText(getChildItem(group, position));
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv;

        public ChildViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textview_row_items);
        }
    }

}