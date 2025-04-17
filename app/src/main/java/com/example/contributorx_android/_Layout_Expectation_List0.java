package com.example.contributorx_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class _Layout_Expectation_List0 extends BaseAdapter {

    List<Expectation> expectations;
    LayoutInflater mInflater;
    Context context;
    NumberFormat currencyFormatter;

    public _Layout_Expectation_List0(Context context, List<Expectation> list) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.expectations = list;
        this.currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
    }

    @Override
    public int getCount() {
        return expectations.size();
    }

    @Override
    public Object getItem(int position) {
        return expectations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return expectations.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        _Layout_Expectation_List0.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_expectation_list0, parent, false);
            holder = new _Layout_Expectation_List0.ViewHolder();

            holder.tvContributor = convertView.findViewById(R.id.tvContributor);
            holder.tvBillPledge = convertView.findViewById(R.id.tvBillPledge);
            holder.tvAmountOwed = convertView.findViewById(R.id.tvAmountOwed);
            holder.tvPreviouslyPaid = convertView.findViewById(R.id.tvPreviouslyPaid);
            holder.tvAmountToApprove = convertView.findViewById(R.id.tvAmountToApprove);

            convertView.setTag(holder);
        } else {
            holder = (_Layout_Expectation_List0.ViewHolder) convertView.getTag();
        }

        Expectation expectation = expectations.get(position);

        Contributor contributor = _DAO_Contributor.GetContributor(expectation.getContributorId());
        Contribution contribution = _DAO_Contribution.GetContribution(expectation.getContributionId());

        if (contributor != null && contribution != null) {
            holder.tvContributor.setText(contributor.getUserName());
            holder.tvBillPledge.setText(contribution.getName());
            holder.tvAmountOwed.setText(currencyFormatter.format(contribution.getAmount() - expectation.getAmountPaid()));
            holder.tvPreviouslyPaid.setText(currencyFormatter.format(expectation.getAmountPaid()));
            holder.tvAmountToApprove.setText(currencyFormatter.format(expectation.getAmountToApprove()));
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvContributor;
        TextView tvBillPledge;
        TextView tvAmountOwed;
        TextView tvPreviouslyPaid;
        TextView tvAmountToApprove;
    }
}