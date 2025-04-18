package com.example.contributorx_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            if (expectation.getPaymentStatus() == 2 || expectation.getPaymentStatus() == 3){
                holder.tvAmountToApprove.setVisibility(View.GONE); // Or use View.INVISIBLE if you want to preserve the layout space
            }
            else {
                holder.tvAmountToApprove.setVisibility(View.VISIBLE);
                if (expectation.getAmountToApprove() > 0.00f) {
                    holder.tvAmountToApprove.setText(String.format("%s%s", context.getString(R.string.approve_reject),
                            currencyFormatter.format(expectation.getAmountToApprove())));
                    holder.tvAmountToApprove.setOnClickListener(v -> handlePayButtonClick(expectation.getId(), "approve"));
                } else {
                    holder.tvAmountToApprove.setText(String.format("%s%s", context.getString(R.string.write_off),
                            currencyFormatter.format(contribution.getAmount() - expectation.getAmountPaid())));
                    holder.tvAmountToApprove.setOnClickListener(v -> handlePayButtonClick(expectation.getId(), "write-off"));
                }
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvContributor;
        TextView tvBillPledge;
        TextView tvAmountOwed;
        Button tvAmountToApprove;
    }

    private void handlePayButtonClick(int expectationId, String str) {
        if ("approve".equals(str)) {
            Intent approvePaymentIntent = new Intent(context, _Activity_Approve_Payment.class);
            approvePaymentIntent.putExtra("EXPECTATION_ID", expectationId);
            context.startActivity(approvePaymentIntent);
        }
    }
}