package com.frh.expandrecyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class ExpandableTestAdapter extends ExpandableRecyclerView.Adapter<ExpandableTestAdapter.ChildViewHolder, ExpandableRecyclerView.SimpleGroupViewHolder, String, String> implements ExpandableRecyclerView.OnGroupItemClickedListener, ExpandableRecyclerView.OnChildItemClickedListener {


    EnumType type;
    EnumType enumType;

    public ExpandableTestAdapter(EnumType type, EnumType enumType) {
        this.type = type;
        this.enumType = enumType;
    }

    @Override
    public int getGroupItemCount() {
        return DataListMassages.id.length;
    }

    @Override
    public int getChildItemCount(int group) {
        return 1;
    }

    @Override
    public String getGroupItem(int position) {
        return "group :" + position;
    }

    @Override
    public String getChildItem(int group, int position) {
        return DataListMassages.descriptionArray[group];
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
        holder.setEnum(enumType);

        setdata(holder, group);

    }


    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int group, final int position) {
        super.onBindChildViewHolder(holder, group, position);
        holder.textviewMassageDescription.setText(getChildItem(group, position));

        setRowData(holder, position);
        onChildItemClicked(group, position);

    }

    private void setdata(ExpandableRecyclerView.SimpleGroupViewHolder holder, int group) {

        if (type == EnumType.LISTTRANSACTION) {
            holder.setTextviewGroupText(DataType.titleArray[group]);
            holder.setimageviewUser(DataType.drawableArray[group]);
            holder.settextviewGroupDescription(DataType.stateArray[group]);
            holder.settextviewGroupDate(DataType.dateArray[group]);
            holder.settextviewGroupTime(DataType.timeArray[group]);
        }

        if (type == EnumType.LISTMASSAGES_NOT_READ) {
            holder.setTextviewGroupText(DataListMassages.titleArray[group]);
            holder.setimageviewUser(DataListMassages.drawableArray[group]);
            holder.settextviewGroupDescription(DataListMassages.stateArray[group]);

            if (DataListMassages.type[group].equals(1)) {
                holder.setEnum(EnumType.LISTMASSAGES_NOT_READ);
            } else
                holder.setEnum(EnumType.LISTMASSAGES_READ);
        }
    }


    private void setRowData(ChildViewHolder holder, final int position) {
        if (type == EnumType.LISTTRANSACTION) {
            holder.linearRowTransaction.setVisibility(View.VISIBLE);
            holder.textviewTrackingCodeValue.setText(DataType.trackingArray[position]);
            holder.textviewLawyersNameValue.setText(DataType.lawyersNameArray[position]);
            holder.textviewCunsultingStatusValue.setText(DataType.consultingArray[position]);
            holder.textviewDocoumentTimeValue.setText(DataType.docoumentTimeArray[position]);
            holder.textviewWriteTimeValue.setText(DataType.writeTimeArray[position]);
            holder.textviewAudioTimeValue.setText(DataType.audioTimeArray[position]);
        }

        if (type == EnumType.LISTMASSAGES_NOT_READ) {
            holder.textviewMassageDescription.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGroupItemClicked(int group) {
        Log.e("Tag", "onGroupItemClicked: " + group);
    }

    @Override
    public void onChildItemClicked(int group, int position) {

        if (DataListMassages.type[group].equals(1)) {
            Log.e("TAGg", "onGroupItemClicked: " + DataListMassages.getType()[position]);
            DataListMassages.getType()[group] = 0;
        }

        Log.e("Tag", "onchikedItemClicked: " + group);
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        private final TextView textviewMassageDescription;
        private final TextView textviewTrackingCodeValue;
        private final TextView textviewLawyersNameValue;
        private final TextView textviewCunsultingStatusValue;
        private final TextView textviewDocoumentTimeValue;
        private final TextView textviewWriteTimeValue;
        private final TextView textviewAudioTimeValue;
        private final LinearLayout linearRowTransaction;


        public ChildViewHolder(View itemView) {
            super(itemView);
            textviewMassageDescription = itemView.findViewById(R.id.textview_massage_description);
            linearRowTransaction = itemView.findViewById(R.id.linear_row_transaction);
            textviewTrackingCodeValue = itemView.findViewById(R.id.textview_tracking_code_value);
            textviewLawyersNameValue = itemView.findViewById(R.id.textview_lawyers_name_value);
            textviewCunsultingStatusValue = itemView.findViewById(R.id.textview_cunsulting_status_value);
            textviewDocoumentTimeValue = itemView.findViewById(R.id.textview_docoument_time_value);
            textviewWriteTimeValue = itemView.findViewById(R.id.textview_write_time_value);
            textviewAudioTimeValue = itemView.findViewById(R.id.textview_audio_time_value);

        }
    }

}