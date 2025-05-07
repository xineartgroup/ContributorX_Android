package com.example.contributorx_android;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Layout_User_Group_List extends BaseAdapter {
    LayoutInflater mInflater;
    List<Grouping> groupings;
    Context context;

    public _Layout_User_Group_List(Context c, List<Grouping> items){
        context = c;
        groupings = items;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return groupings.size();
    }

    @Override
    public Object getItem(int position) {
        if (groupings.size() > position && position >= 0){
            return groupings.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (groupings.size() > position && position >= 0){
            return groupings.get(position).getId();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_user_group_list, parent, false);

            holder = new ViewHolder();
            holder.lblName = convertView.findViewById(R.id.lblName);
            holder.btnRemove = convertView.findViewById(R.id.btnRemove);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            Grouping grouping = position < groupings.size() && position >= 0 ? groupings.get(position) : null;
            if (grouping != null) {
                APIGroupResponse response = _DAO_Group.GetGroup(grouping.getGroupId());

                handler.post(() -> {
                    if (response.getIsSuccess()) {
                        Group group = response.getGroup();

                        if (group != null) {
                            holder.lblName.setText(group.getName());

                            holder.btnRemove.setOnClickListener(v -> {
                                handleRemoveButtonClick(group.getId());
                                notifyDataSetChanged();
                            });
                        }
                    }
                });
            }
        });

        executor.shutdown();

        return convertView;
    }

    static class ViewHolder {
        TextView lblName;
        Button btnRemove;
    }

    private void handleRemoveButtonClick(int groupId) {
        for (int i = 0; i < groupings.size(); i++){
            if (groupings.get(i).getGroupId() == groupId){
                groupings.remove(i);
                break;
            }
        }
    }
}
