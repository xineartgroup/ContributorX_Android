package com.example.contributorx_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class _Layout_Group_List extends BaseAdapter {
    List<Group> groups;
    LayoutInflater mInflater;
    Context context;

    public _Layout_Group_List(Context c, List<Group> t) {
        groups = t;
        mInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = c;
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Object getItem(int i) {
        return groups.get(i);
    }

    @Override
    public long getItemId(int i) {
        return groups.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_group_list, parent, false);
            holder = new ViewHolder();

            holder.lblGroupName = convertView.findViewById(R.id.lblGroupName);
            holder.lblGroupDescription = convertView.findViewById(R.id.lblGroupDescription);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Group expense = groups.get(i);

        holder.lblGroupName.setText(expense.getName());
        holder.lblGroupDescription.setText(expense.getDescription());

        return convertView;
    }

    private static class ViewHolder {
        TextView lblGroupName;
        TextView lblGroupDescription;
    }
}
