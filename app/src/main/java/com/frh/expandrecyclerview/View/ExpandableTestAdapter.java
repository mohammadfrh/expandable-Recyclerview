package com.frh.expandrecyclerview.View;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.frh.expandrecyclerview.Model.MassageModel;
import com.frh.expandrecyclerview.R;
import com.frh.expandrecyclerview.data.DataListMassages;
import com.frh.expandrecyclerview.Model.TransactionModel;

import java.util.ArrayList;


public class ExpandableTestAdapter extends ExpandableRecyclerView.Adapter<ExpandableTestAdapter.ChildViewHolder, ExpandableRecyclerView.SimpleGroupViewHolder, String, String> implements ExpandableRecyclerView.OnGroupItemClickedListener, ExpandableRecyclerView.OnChildItemClickedListener {

    EnumType type;
    EnumType enumType;
    ArrayList<TransactionModel> transactionModels;
    ArrayList<MassageModel> massageModels;


    public ExpandableTestAdapter(EnumType type, EnumType enumType) {
        this.type = type;
        this.enumType = enumType;
    }

    @Override
    public int getGroupItemCount() {
        if (transactionModels != null)
            return transactionModels.size() - 1;
        else return 0;
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
            holder.setTextviewGroupText(transactionModels.get(group).getTitle());
            holder.setimageviewUser(transactionModels.get(group).getDrawable());
            holder.settextviewGroupDescription(transactionModels.get(group).getDescription());
            holder.settextviewGroupDate(transactionModels.get(group).getDate());
            holder.settextviewGroupTime(transactionModels.get(group).getTime());
        }

        if (type == EnumType.LISTMASSAGES_NOT_READ) {
            holder.setTextviewGroupText(massageModels.get(group).getTitle());
            holder.setimageviewUser(massageModels.get(group).getDrawable());
            holder.settextviewGroupDescription(massageModels.get(group).getState());

            if (massageModels.get(group).getType()==1) {
                holder.setEnum(EnumType.LISTMASSAGES_NOT_READ);
            } else
                holder.setEnum(EnumType.LISTMASSAGES_READ);
        }
    }

    public void setDataTransaction(ArrayList<TransactionModel> transactionModels) {
        this.transactionModels = transactionModels;
        notifyDataSetChanged();
    }

    public void setDataMassage(ArrayList<MassageModel> massageModels) {
        this.massageModels = massageModels;
        notifyDataSetChanged();
    }


    private void setRowData(ChildViewHolder holder, final int position) {
        if (type == EnumType.LISTTRANSACTION) {
            holder.linearRowTransaction.setVisibility(View.VISIBLE);
            holder.textviewTrackingCodeValue.setText(transactionModels.get(position).getTracking());
            holder.textviewLawyersNameValue.setText(transactionModels.get(position).getLawyersName());
            holder.textviewCunsultingStatusValue.setText(transactionModels.get(position).getConsulting());
            holder.textviewDocoumentTimeValue.setText(transactionModels.get(position).getDocoumentTime());
            holder.textviewWriteTimeValue.setText(transactionModels.get(position).getWriteTime());
            holder.textviewAudioTimeValue.setText(transactionModels.get(position).getAudioTime());
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

//        if (DataListMassages.type[group].equals(1)) {
//            Log.e("TAGg", "onGroupItemClicked: " + DataListMassages.getType()[position]);
//            DataListMassages.getType()[group] = 0;
//        }
//
//        Log.e("Tag", "onchikedItemClicked: " + group);
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