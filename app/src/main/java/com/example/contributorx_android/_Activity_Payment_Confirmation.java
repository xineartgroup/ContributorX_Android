package com.example.contributorx_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class _Activity_Payment_Confirmation extends AppCompatActivity {

    Contributor contributor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        TextView lblTotalBill = findViewById(R.id.lblTotalBill);
        TextView lblTotalBillPaid = findViewById(R.id.lblTotalBillPaid);
        TextView lblTotalBillDue = findViewById(R.id.lblTotalBillDue);
        ListView lstDetail = findViewById(R.id.lstDetail);
        EditText txtAmount = findViewById(R.id.txtAmount);
        Button btnConfirmationAccept = findViewById(R.id.btnConfirmationAccept);
        Button btnConfirmationReject = findViewById(R.id.btnConfirmationReject);

        Intent intent = getIntent();

        if (intent.hasExtra("com.example.contributorx_android.ITEMINDEX")) {
                int index = Objects.requireNonNull(intent.getExtras()).getInt("com.example.contributorx_android.ITEMINDEX");

                contributor = _DAO_Contributor.GetContributor(index);

                if (contributor != null){
                    List<Expectation> expectations = _DAO_Expectation.GetExpectationsForContributor(contributor.getId());

                    String[] amounts = new String[]{};

                    // Create a List from String Array elements
                    final List<String> amount_list = new ArrayList<String>(Arrays.asList(amounts));

                    // Create an ArrayAdapter from List
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                            (this, android.R.layout.simple_list_item_1, amount_list);

                    // DataBind ListView with items from ArrayAdapter
                    lstDetail.setAdapter(arrayAdapter);

                    float totalAmountOwed = 0.00f;
                    float totalAmountPaid = 0.00f;
                    for (int i = 0; i < expectations.size(); i++) {
                        Expectation expectation = expectations.get(i);
                        totalAmountPaid += expectation.getAmountPaid();
                        Contribution bill = _DAO_Contribution.GetContribution(expectation.getContributionId());
                        if (bill != null) {
                            totalAmountOwed += bill.getAmount();
                        }
                    }

                    lblTotalBill.setText(String.format("Total Bill - NGN%s", totalAmountOwed));
                    lblTotalBillPaid.setText(String.format("Total Paid - NGN%s", totalAmountPaid));
                    lblTotalBillDue.setText(String.format("Total Due - NGN%s", (totalAmountOwed > totalAmountPaid) ? totalAmountOwed - totalAmountPaid : 0.00f));
                }
        }

        btnConfirmationAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (contributor != null) {
                        float amount = Float.parseFloat(txtAmount.getText().toString());

                        //PaymentConfirmationDAO.UpdatePaymentConfirmation(0, new PaymentConfirmation(contributor.getId(), amount));

                        //txtAmount.setText("");
                        Toast.makeText(_Activity_Payment_Confirmation.this, "Request Sent...", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception ex) {
                    Toast.makeText(_Activity_Payment_Confirmation.this, "Invalid amount", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    String getShortText(String text) {
        String result = text;

        if (text.length() > 18)
            result = text.substring(0, 15) + "...";

        int length = result.length();

        for (int i = length; i < 20; i++) {
            result = String.format("%s ", result);
        }

        return result;
    }
}