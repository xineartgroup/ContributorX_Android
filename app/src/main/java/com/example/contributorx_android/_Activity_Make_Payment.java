package com.example.contributorx_android;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Activity_Make_Payment extends AppCompatActivity {

    private EditText txtPaymentAmount;
    private Spinner edtPaymentMethod;

    private int expectationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

        TextView txtContributionName = findViewById(R.id.txtContributionName);
        TextView txtAmount = findViewById(R.id.txtAmount);
        TextView txtDueDate = findViewById(R.id.txtDueDate);
        TextView txtAmountPaid = findViewById(R.id.txtAmountPaid);
        txtPaymentAmount = findViewById(R.id.txtPaymentAmount);
        edtPaymentMethod = findViewById(R.id.edtPaymentMethod);

        Intent intent = getIntent();
        expectationId = intent.getIntExtra("EXPECTATION_ID", -1);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            APIExpectationResponse response = _DAO_Expectation.GetExpectation(expectationId);

            handler.post(() -> {
                Expectation expectation = response.getExpectation();
                if (expectation != null) {
                    if (expectation.getContribution() == null) {
                        APIContributionResponse contributionResponse = _DAO_Contribution.GetContribution(expectation.getContributionId());
                        if (contributionResponse.getIsSuccess() && contributionResponse.getContribution() != null) {
                            expectation.setContribution(contributionResponse.getContribution());
                        }
                    }

                    if (expectation.getContribution() != null) {
                        txtContributionName.setText(expectation.getContribution().getName());
                        txtAmount.setText(getString(R.string.amount, expectation.getContribution().getAmount()));

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        txtDueDate.setText(getString(R.string.due_date, expectation.getContribution().getDueDate()));

                        float amountPaid = expectation.getAmountPaid();
                        txtAmountPaid.setText(getString(R.string.amount_paid, amountPaid));

                        double remainingBalance = expectation.getContribution().getAmount() - amountPaid;
                        txtPaymentAmount.setText("" + remainingBalance);
                    }
                }
            });
        });

        Button btnMakePayment = findViewById(R.id.btnMakePayment);
        if (btnMakePayment != null) {
            btnMakePayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processPayment();
                }
            });
        }
    }

    private void processPayment() {
        if (txtPaymentAmount.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter a payment amount", Toast.LENGTH_SHORT).show();
            return;
        }

        float paymentAmount = Float.parseFloat(txtPaymentAmount.getText().toString());
        String paymentMethod = edtPaymentMethod.getSelectedItem().toString();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            APIExpectationResponse response = _DAO_Expectation.GetExpectation(expectationId);

            handler.post(() -> {
                Expectation expectation = response.getExpectation();
                if (expectation != null) {
                    if ("Use Payment Gateway".equals(paymentMethod) && PaymentSuccessful(expectation)) {
                        expectation.setPaymentStatus(3);
                        expectation.setAmountPaid(paymentAmount);
                        APIExpectationResponse resp = _DAO_Expectation.UpdateExpectation(expectation);
                        Toast.makeText(this, "Payment successful!!!", Toast.LENGTH_SHORT).show();
                        // Return to previous screen
                        finish();
                    }
                    else if ("Send for Approval".equals(paymentMethod)) {
                        expectation.setPaymentStatus(1);
                        expectation.setAmountToApprove(paymentAmount);
                        APIExpectationResponse resp = _DAO_Expectation.UpdateExpectation(expectation);
                        Toast.makeText(this, "Payment sent for Approval!!!", Toast.LENGTH_SHORT).show();
                        // Return to previous screen
                        finish();
                    }
                    else{
                        Toast.makeText(this, "Payment failed!!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Payment failed!!!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private boolean PaymentSuccessful(Expectation expectation) {
        return false;
    }
}