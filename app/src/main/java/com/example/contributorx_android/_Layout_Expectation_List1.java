package com.example.contributorx_android;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class _Layout_Expectation_List1 extends BaseAdapter {
    LayoutInflater mInflater;
    List<Expectation> expectations;
    Context context;

    public _Layout_Expectation_List1(Context c, List<Expectation> items){
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
            convertView = mInflater.inflate(R.layout.layout_expectation_list1, parent, false);

            holder = new ViewHolder();
            holder.txtAmount = convertView.findViewById(R.id.txtAmount);
            holder.txtName = convertView.findViewById(R.id.lblName);
            holder.btnPay = convertView.findViewById(R.id.btnPay);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Expectation expectation = position < expectations.size() && position >= 0 ? expectations.get(position) : null;
        if (expectation != null) {
            if (expectation.getContribution() == null) {
                APIContributionResponse contributionResponse = _DAO_Contribution.GetContribution(expectation.getContributionId());
                if (contributionResponse.getIsSuccess() && contributionResponse.getContribution() != null) {
                    expectation.setContribution(contributionResponse.getContribution());
                }
            }

            if (expectation.getContribution() != null) {
                holder.txtAmount.setText(String.valueOf(expectation.getContribution().getAmount()));
                holder.txtName.setText(expectation.getContribution().getName());

                holder.btnPay.setOnClickListener(v -> handlePayButtonClick(expectation.getId()));
            }
        }

        return convertView;
    }

    static class ViewHolder {
        TextView txtAmount;
        TextView txtName;
        Button btnPay;
    }

    private void handlePayButtonClick(int expectationId) {
        Intent makePaymentIntent = new Intent(context, _Activity_Make_Payment.class);

        makePaymentIntent.putExtra("EXPECTATION_ID", expectationId);

        context.startActivity(makePaymentIntent);
    }
}
