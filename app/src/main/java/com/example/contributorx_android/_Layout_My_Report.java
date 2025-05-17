package com.example.contributorx_android;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class _Layout_My_Report extends BaseAdapter {
    LayoutInflater mInflater;
    List<Expectation> expectations;
    Context context;

    public _Layout_My_Report(Context c, List<Expectation> items){
        context = c;
        expectations = items;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return expectations.size();
    }

    @Override
    public Object getItem(int position) {
        if (expectations.size() > position && position >= 0){
            return expectations.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (expectations.size() > position && position >= 0){
            return expectations.get(position).getId();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_my_report, parent, false);

            holder = new ViewHolder();
            holder.lblAmountPaid = convertView.findViewById(R.id.lblAmountPaid);
            holder.lblAmountOwed = convertView.findViewById(R.id.lblAmountOwed);
            holder.lblBillName = convertView.findViewById(R.id.lblBillName);
            holder.lblContributorName = convertView.findViewById(R.id.lblContributorName);
            holder.lblAmountPaidText = convertView.findViewById(R.id.lblAmountPaidText);
            holder.lblAmountOwedText = convertView.findViewById(R.id.lblAmountOwedText);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Expectation expectation = position < expectations.size() && position >= 0 ? expectations.get(position) : null;
        if (expectation != null) {
            if (expectation.getContribution() == null) {
                APIResponse contributionResponse = _DAO_Contribution.GetContribution(expectation.getContributionId());
                if (contributionResponse.getIsSuccess() && contributionResponse.getContribution() != null) {
                    expectation.setContribution(contributionResponse.getContribution());
                }
            }

            if (expectation.getContribution() != null) {
                holder.lblAmountPaid.setText(String.valueOf(expectation.getAmountPaid()));
                holder.lblAmountOwed.setText(String.valueOf(expectation.getContribution().getAmount()));
                holder.lblBillName.setText(expectation.getContribution().getName());
                holder.lblContributorName.setText(expectation.getContributor().getFullNames());

                if (holder.lblBillName.getText().equals("Total")) {
                    Typeface typeface = Typeface.create("sans-serif-light", Typeface.BOLD);
                    holder.lblAmountPaid.setTypeface(typeface);
                    holder.lblAmountOwed.setTypeface(typeface);
                    holder.lblBillName.setTypeface(typeface);
                    holder.lblContributorName.setTypeface(typeface);
                    holder.lblAmountPaidText.setTypeface(typeface);
                    holder.lblAmountOwedText.setTypeface(typeface);
                }
                else {
                    Typeface typeface = Typeface.create("sans-serif-light", Typeface.NORMAL);
                    holder.lblAmountPaid.setTypeface(typeface);
                    holder.lblAmountOwed.setTypeface(typeface);
                    holder.lblBillName.setTypeface(typeface);
                    holder.lblContributorName.setTypeface(typeface);
                    holder.lblAmountPaidText.setTypeface(typeface);
                    holder.lblAmountOwedText.setTypeface(typeface);
                }
            }
        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblAmountPaid;
        TextView lblAmountOwed;
        TextView lblBillName;
        TextView lblContributorName;
        TextView lblAmountPaidText;
        TextView lblAmountOwedText;
    }
}
