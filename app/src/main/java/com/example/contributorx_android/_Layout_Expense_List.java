package com.example.contributorx_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import java.util.Locale;

public class _Layout_Expense_List extends BaseAdapter {
    List<Expense> expenses;
    LayoutInflater mInflater;
    Context context;

    public _Layout_Expense_List(Context c, List<Expense> t) {
        expenses = t;
        mInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = c;
    }

    @Override
    public int getCount() {
        return expenses.size();
    }

    @Override
    public Object getItem(int i) {
        return expenses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return expenses.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_expense_list, parent, false);
            holder = new ViewHolder();

            holder.lblExpenseName = convertView.findViewById(R.id.lblExpenseName);
            holder.lblExpenseDescription = convertView.findViewById(R.id.lblExpenseDescription);
            holder.lblExpenseAmount = convertView.findViewById(R.id.lblExpenseAmount);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Expense expense = expenses.get(i);

        holder.lblExpenseName.setText(expense.getName());
        holder.lblExpenseDescription.setText(expense.getDescription());
        holder.lblExpenseAmount.setText(String.format(Locale.getDefault(), "%.2f", expense.getAmountPaid()));

        return convertView;
    }

    private static class ViewHolder {
        TextView lblExpenseName;
        TextView lblExpenseDescription;
        TextView lblExpenseAmount;
    }
}
