package com.example.assignment5_recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment5_recyclerview.R;

import java.util.HashMap;
import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_GROUP = 0;
    private static final int TYPE_CHILD = 1;

    private Context context;
    private List<String> groupList;
    private HashMap<String, List<String>> childMap;
    private List<Boolean> isExpandedList; // Track the expanded/collapsed state of each group
    private int[] images;

    public CustomRecyclerViewAdapter(Context context, List<String> groupList, HashMap<String, List<String>> childMap,
                                     List<Boolean> isExpandedList, int[] images) {
        this.context = context;
        this.groupList = groupList;
        this.childMap = childMap;
        this.isExpandedList = isExpandedList;
        this.images = images;
    }

    @Override
    public int getItemCount() {
        int count = groupList.size();
        // Count children based on expanded state
        for (int i = 0; i < groupList.size(); i++) {
            if (isExpandedList.get(i)) {
                count += childMap.get(groupList.get(i)).size();
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int groupCount = groupList.size();
        int index = 0;

        for (int i = 0; i < groupCount; i++) {
            if (position == index) {
                return TYPE_GROUP; // Group item
            }
            index++;
            if (isExpandedList.get(i)) {
                List<String> childList = childMap.get(groupList.get(i));
                if (position == index) {
                    return TYPE_CHILD; // Child item
                }
                index += childList.size();
            }
        }
        return TYPE_GROUP;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_GROUP) {
            View view = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false);
            return new GroupViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.child_item, parent, false);
            return new ChildViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int groupCount = groupList.size();
        int index = 0;

        for (int i = 0; i < groupCount; i++) {
            if (position == index) {
                // Bind group item
                String group = groupList.get(i);
                GroupViewHolder groupHolder = (GroupViewHolder) holder;
                groupHolder.groupTitle.setText(group);
                groupHolder.groupImage.setImageResource(images[i]);

                int finalI = i;
                groupHolder.itemView.setOnClickListener(v -> {
                    boolean expanded = isExpandedList.get(finalI);
                    isExpandedList.set(finalI, !expanded); // Toggle expansion
                    notifyDataSetChanged(); // Refresh the adapter to show/hide children
                });
                return;
            }
            index++;
            if (isExpandedList.get(i)) {
                // Bind child items if the group is expanded
                List<String> childList = childMap.get(groupList.get(i));
                if (position == index) {
                    String child = childList.get(position - index);
                    ChildViewHolder childHolder = (ChildViewHolder) holder;
                    childHolder.childTextView.setText(child);
                    childHolder.itemView.setOnClickListener(v -> {
                        Toast.makeText(context, "Clicked: " + child, Toast.LENGTH_SHORT).show();
                    });
                    return;
                }
                index += childList.size();
            }
        }
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView groupTitle;
        ImageView groupImage;

        public GroupViewHolder(View itemView) {
            super(itemView);
            groupTitle = itemView.findViewById(R.id.groupTitle);
            groupImage = itemView.findViewById(R.id.groupImage);
        }
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {
        TextView childTextView;

        public ChildViewHolder(View itemView) {
            super(itemView);
            childTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}
