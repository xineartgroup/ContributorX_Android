package com.example.contributorx_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class _Layout_Contribution_List extends BaseAdapter {
    List<Contribution> contributions;
    LayoutInflater mInflater;
    Context context;

    public _Layout_Contribution_List(Context c, List<Contribution> t) {
        contributions = t;
        mInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = c;
    }

    @Override
    public int getCount() {
        return contributions.size();
    }

    @Override
    public Object getItem(int i) {
        return contributions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return contributions.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_contribution_list, parent, false);
            holder = new ViewHolder();

            holder.lblContributionName = convertView.findViewById(R.id.lblContributionName);
            holder.lblContributionDate = convertView.findViewById(R.id.lblContributionDate);
            holder.lblContributionAmount = convertView.findViewById(R.id.lblContributionAmount);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contribution contribution = contributions.get(i);

        holder.lblContributionName.setText(contribution.getName());
        holder.lblContributionDate.setText(GetDateString(contribution.getDueDate()));
        holder.lblContributionAmount.setText(String.format(Locale.getDefault(), "%.2f", contribution.getAmount()));

        return convertView;
    }

    private static class ViewHolder {
        TextView lblContributionName;
        TextView lblContributionDate;
        TextView lblContributionAmount;
    }

    private String GetDateString(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        return day + "/" + (month + 1) + "/" + year;
    }
}
