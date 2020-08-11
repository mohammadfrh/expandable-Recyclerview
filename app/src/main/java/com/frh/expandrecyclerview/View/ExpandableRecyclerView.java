package com.frh.expandrecyclerview.View;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.frh.expandrecyclerview.R;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

public class ExpandableRecyclerView extends RecyclerView {


    public ExpandableRecyclerView(Context context) {
        super(context, null);
        initRecycler();
    }

    public ExpandableRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRecycler();
    }

    public ExpandableRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRecycler();
    }

    private void initRecycler() {
        setClipToPadding(false);
        setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public Parcelable onSaveInstanceState() {
        //begin boilerplate code that allows parent classes to save state
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);
        //end

        if (getAdapter() != null)
            ss.stateToSave = ((Adapter) this.getAdapter()).getExpandedGroups();

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        //begin boilerplate code so parent classes can restore state
        if (!(state instanceof SavedState))  // if state is not instance of out SaveState just restore in reg way
        {
            super.onRestoreInstanceState(state);
            return;
        }
        // else if cast him to SavedState

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        //end

        if (getAdapter() != null)
            ((Adapter) getAdapter()).setExpandedGroups(ss.stateToSave);
    }

    static class SavedState implements Parcelable {
        public static final SavedState EMPTY_STATE = new SavedState() {
        };

        SparseBooleanArray stateToSave;
        Parcelable superState;

        SavedState() {
            superState = null;
        }

        SavedState(Parcelable superState) {
            this.superState = superState != EMPTY_STATE ? superState : null;
        }

        private SavedState(Parcel in) {
            Parcelable superState = in.readParcelable(ExpandableRecyclerView.class.getClassLoader());
            this.superState = superState != null ? superState : EMPTY_STATE;
            this.stateToSave = in.readSparseBooleanArray();
        }

        @Override
        public int describeContents() {
            return 0;
        }


        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            out.writeParcelable(superState, flags);
            out.writeSparseBooleanArray(this.stateToSave);
        }

        public Parcelable getSuperState() {
            return superState;
        }

        //required field that makes Parcelables from a Parcel
        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }


    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (!(adapter instanceof Adapter))
            throw new IllegalArgumentException("adapter has to be of type ExpandableRecyclerView.Adapter");
        super.setAdapter(adapter);
    }


    public static abstract class Adapter<CVH extends ViewHolder, GVH extends ViewHolder, C, G> extends RecyclerView.Adapter<ViewHolder> {

        private OnChildItemClickedListener onChildItemClickedListener;
        private OnGroupItemClickedListener onGroupItemClickedListener;


        private static final int TYPE_HEADER = 0;

        SparseBooleanArray expanded = new SparseBooleanArray();

        public Adapter() {
        }

        boolean isExpanded(int group) {
            return expanded.get(group);
        }

        SparseBooleanArray getExpandedGroups() {
            return expanded;
        }

        public void setExpandedGroups(SparseBooleanArray expanded) {
            this.expanded = expanded;
        }

        public void expand(int group) {
            if (isExpanded(group))
                return;

            // this lines of code calculate number of shown item in recycler view. also group is counting .
            int position = 0;
            for (int i = 0; i < group; i++) {
                position++;
                if (isExpanded(i))
                    position += getChildItemCount(i);
            }
            position++; // this for percent group

            notifyItemRangeInserted(position, getChildItemCount(group)); // notify recycler view for expanding
            expanded.put(group, true); // save expanding in sparce array
        }

        public void collapse(int group) {
            if (!isExpanded(group)) // if is not expanded . so nothing to collapse.
                return;

            int position = 0;
            for (int i = 0; i < group; i++) {
                position++;
                if (isExpanded(i))
                    position += getChildItemCount(i); // item
            }
            position++;
            notifyItemRangeRemoved(position, getChildItemCount(group));
            expanded.put(group, false);
        }

        public abstract int getGroupItemCount();

        public abstract int getChildItemCount(int group);

        @Override
        public int getItemCount() {
            int count = 0;
            for (int i = 0; i <= getGroupItemCount(); i++) {
                count += isExpanded(i) ? getChildItemCount(i) + 1 : 1;
            }
            return count;
        }

        public abstract G getGroupItem(int position);

        public abstract C getChildItem(int group, int position);

        public Object getItem(int i) {
            int group = 0;
            while (group <= getGroupItemCount()) {
                if (i > 0 && !isExpanded(group)) {
                    i--;
                    group++;
                    continue;
                }
                if (i > 0 && isExpanded(group)) {
                    i--;
                    if (i < getChildItemCount(group))
                        return getChildItem(group, i);
                    i -= getChildItemCount(group);
                    group++;
                    continue;
                }
                if (i == 0)
                    i++;
                return getGroupItem(group);
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int i) {
            int group = 0;
            while (group <= getGroupItemCount()) {
                if (i > 0 && !isExpanded(group)) {
                    i--;
                    group++;
                    continue;
                }
                if (i > 0 && isExpanded(group)) {
                    i--;
                    if (i < getChildItemCount(group)) {
                        onBindChildViewHolder((CVH) holder, group, i);
                        return;
                    }
                    i -= getChildItemCount(group);
                    group++;
                    continue;
                }
                if (i == 0) {
                    onBindGroupViewHolder((GVH) holder, group);
                    return;
                }
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return viewType == TYPE_HEADER ? onCreateGroupViewHolder(parent) : onCreateChildViewHolder(parent, viewType);
        }

        protected abstract GVH onCreateGroupViewHolder(ViewGroup parent);

        protected abstract CVH onCreateChildViewHolder(ViewGroup parent, int viewType);

        public abstract int getChildItemViewType(int group, int position);

        @Override
        public int getItemViewType(int i) {
            int group = 0;
            while (group <= getGroupItemCount()) {
                if (i > 0 && !isExpanded(group)) {
                    i--;
                    group++;
                    continue;
                }
                if (i > 0 && isExpanded(group)) {
                    i--;
                    if (i < getChildItemCount(group))
                        return getChildItemViewType(group, i);
                    i -= getChildItemCount(group);
                    group++;
                    continue;
                }
                if (i == 0)
                    return TYPE_HEADER;
            }
            throw new IndexOutOfBoundsException();
        }

        public void setOnChildItemClickedListener(OnChildItemClickedListener onItemClickedListener) {
            this.onChildItemClickedListener = onItemClickedListener;
        }

        public void setOnGroupItemClickedListener(OnGroupItemClickedListener onItemClickedListener) {
            this.onGroupItemClickedListener = onItemClickedListener;
        }

        public void onBindChildViewHolder(CVH holder, final int group, final int position) {
            holder.itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (Adapter.this.onChildItemClickedListener != null) {
                        Adapter.this.onChildItemClickedListener.onChildItemClicked(group, position);
                    }

                }
            });
        }

        public void onBindGroupViewHolder(final GVH holder, final int group) {
            if (holder instanceof GroupViewHolder)
                ((GroupViewHolder) holder).setExpanded(isExpanded(group));

            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Adapter.this.onGroupItemClickedListener != null) {
                        Adapter.this.onGroupItemClickedListener.onGroupItemClicked(group);
                    }

                    if (isExpanded(group)) {
                        collapse(group);
                        if (holder instanceof GroupViewHolder)
                            ((GroupViewHolder) holder).collapse();
                    } else {
                        expand(group);
                        if (holder instanceof GroupViewHolder)
                            ((GroupViewHolder) holder).expand();
                    }
                }

            });
        }
    }

    public static abstract class GroupViewHolder extends RecyclerView.ViewHolder {

        public GroupViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void expand();

        public abstract void collapse();

        public abstract void setExpanded(boolean expanded);

        public abstract boolean isExpanded();
    }

    public static class SimpleGroupViewHolder extends GroupViewHolder {
        ImageView imageviewGroupExpanded;
        TextView textviewGroupText;
        LinearLayout linearExpandable;
        TextView textviewGroupDate;
        TextView textviewGroupTime;
        TextView textviewGroupDescription;
        TextView textviewGroupState;
        TextView textviewGroupType;
        ImageView imageviewUser;

        private boolean expanded;

        public SimpleGroupViewHolder(Context context) {
            super(View.inflate(context, R.layout.view_expandable, null));


            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 30, 0, 0);

            itemView.setLayoutParams(params);

            imageviewGroupExpanded = itemView.findViewById(R.id.imageview_group_expanded);
            textviewGroupText = itemView.findViewById(R.id.textview_group_text);
            linearExpandable = itemView.findViewById(R.id.linear_expandable);
            textviewGroupDate = itemView.findViewById(R.id.textview_group_date);
            textviewGroupTime = itemView.findViewById(R.id.textview_group_time);
            textviewGroupDescription = itemView.findViewById(R.id.textview_group_description);
            textviewGroupState = itemView.findViewById(R.id.textview_group_state);
            imageviewUser = itemView.findViewById(R.id.imageview_user);
            textviewGroupType = itemView.findViewById(R.id.textview_group_type);

        }

        public void expand() {
            ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(200);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    ViewHelper.setRotation(imageviewGroupExpanded, 180 * (float) (animation.getAnimatedValue()));
                    imageviewGroupExpanded.postInvalidate();
                }
            });
            animator.start();
            expanded = true;
        }

        public void collapse() {
            ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(200);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    ViewHelper.setRotation(imageviewGroupExpanded, 180 * (float) (animation.getAnimatedValue()));
                    imageviewGroupExpanded.postInvalidate();
                }
            });
            animator.start();
            expanded = false;
        }

        public void setExpanded(boolean expanded) {
            ViewHelper.setRotation(imageviewGroupExpanded, expanded ? 180 : 0);
            this.expanded = expanded;
        }

        @Override
        public boolean isExpanded() {
            return expanded;
        }

        public void setTextviewGroupText(String t) {
            textviewGroupText.setText(t);
        }

        public void setTextviewGroupTextColor(int color) {
            textviewGroupText.setTextColor(color);
        }

        public void setTextviewGroupColor(int color) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                linearExpandable.getBackground().setTint(color);
            }
        }

        public void settextviewGroupDate(String Datevalue) {
            textviewGroupDate.setText(Datevalue);
        }

        public void settextviewGroupTime(String Timevalue) {
            textviewGroupTime.setText(Timevalue);
        }

        public void settextviewGroupDescription(String Desciptionvalue) {
            textviewGroupDescription.setText(Desciptionvalue);
        }

        public void settextviewGroupState(String Statevalue) {
            textviewGroupState.setText(Statevalue);
        }

        public void setEnum(EnumType type) {

            switch (type) {
                case LISTMASSAGES_NOT_READ:
                    textviewGroupState.setVisibility(VISIBLE);
                    textviewGroupType.setVisibility(GONE);
                    textviewGroupState.setText("وضعیت مشاوره:");
                    textviewGroupState.setTextColor(Color.parseColor("#FFFFFF"));
                    linearExpandable.setBackgroundResource(R.drawable.background_list_selected_messages);
                    textviewGroupDescription.setTextColor(Color.parseColor("#FFFFFF"));
                    textviewGroupDate.setTextColor(Color.parseColor("#FFFFFF"));
                    textviewGroupTime.setTextColor(Color.parseColor("#FFFFFF"));
                    textviewGroupText.setTextColor(Color.parseColor("#FFFFFF"));
                    break;


                case LISTMASSAGES_READ:
                    textviewGroupState.setVisibility(GONE);
                    textviewGroupType.setVisibility(GONE);
                    linearExpandable.setBackgroundResource(R.drawable.background_expand_item);
                    textviewGroupDescription.setTextColor(Color.parseColor("#919194"));
                    textviewGroupDate.setTextColor(Color.parseColor("#919194"));
                    textviewGroupTime.setTextColor(Color.parseColor("#919194"));
                    textviewGroupText.setTextColor(Color.parseColor("#000000"));
                    break;

                case LISTTRANSACTION:
                    textviewGroupState.setVisibility(VISIBLE);
                    textviewGroupType.setVisibility(VISIBLE);
                    textviewGroupDescription.setTextColor(Color.parseColor("#43756A"));
                    textviewGroupDescription.setTextSize(14);
                    textviewGroupState.setTextColor(Color.parseColor("#43756A"));
                    textviewGroupState.setText("مبلغ:");
                    linearExpandable.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    break;

            }
        }

        public void setimageviewUser(int User) {
            imageviewUser.setBackgroundResource(User);
        }

        public String getTextviewGroupText() {
            return textviewGroupText.getText().toString();
        }
    }

    public interface OnChildItemClickedListener {
        void onChildItemClicked(int group, int position);
    }

    public interface OnGroupItemClickedListener {
        void onGroupItemClicked(int group);
    }
}