package com.example.contributorx_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Activity_Approve_Payment extends AppCompatActivity {

    private TextView tvContributionName;
    private TextView tvAmountOwed;
    private TextView tvDueDate;
    private TextView tvAmountToApprove;
    private ImageView imgReceipt;
    private TextView tvNoReceipt;
    private Button btnApprove;
    private Button btnReject;
    private Button btnCancel;

    int expectationId;
    int contributionId;
    int contributorId;
    float amountToApprove;
    String paymentReceipt;

    private NumberFormat currencyFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_payment);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Initialize formatters
        currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        // Initialize views
        tvContributionName = findViewById(R.id.tvContributionName);
        tvAmountOwed = findViewById(R.id.tvAmountOwed);
        tvDueDate = findViewById(R.id.tvDueDate);
        tvAmountToApprove = findViewById(R.id.tvAmountToApprove);
        imgReceipt = findViewById(R.id.imgReceipt);
        tvNoReceipt = findViewById(R.id.tvNoReceipt);
        btnApprove = findViewById(R.id.btnApprove);
        btnReject = findViewById(R.id.btnReject);
        btnCancel = findViewById(R.id.btnCancel);

        // Get data from intent
        Intent intent = getIntent();
        if (intent != null) {
            expectationId = intent.getIntExtra("EXPECTATION_ID", 0);
            loadExpectationDetails(expectationId);
        }

        setupButtonListeners(expectationId);
    }

    private void loadExpectationDetails(int expectationId) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            APIExpectationResponse response = _DAO_Expectation.GetExpectation(expectationId);

            handler.post(() -> {
                Expectation expectation = response.getExpectation();
                if (expectation == null) {
                    Toast.makeText(this, "Error loading expectation details", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                // Store data for use by button handlers
                this.contributionId = expectation.getContributionId();
                this.contributorId = expectation.getContributorId();
                this.amountToApprove = expectation.getAmountToApprove();
                this.paymentReceipt = expectation.getPaymentReceipt();

                // Get related data
                if (expectation.getContribution() == null)
                    expectation.setContribution(_DAO_Contribution.GetContribution(expectation.getContributionId()));

                if (expectation.getContribution() != null) {
                    tvContributionName.setText(expectation.getContribution().getName());
                    tvAmountOwed.setText(currencyFormatter.format(expectation.getContribution().getAmount()));
                    tvDueDate.setText(expectation.getContribution().getDueDate());
                }

                // Set amount to approve
                tvAmountToApprove.setText(currencyFormatter.format(expectation.getAmountToApprove()));

                // Handle receipt image
                if (expectation.getPaymentReceipt() != null && !expectation.getPaymentReceipt().isEmpty()) {
                    loadReceiptImage(expectation.getPaymentReceipt());
                } else {
                    imgReceipt.setVisibility(View.GONE);
                    tvNoReceipt.setVisibility(View.VISIBLE);
                }
            });
        });
    }

    private void loadReceiptImage(String receiptPath) {
        // You would need to handle file storage and permissions
        
        try {
            File imgFile = new File(getFilesDir(), receiptPath);
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgReceipt.setImageBitmap(bitmap);
                imgReceipt.setVisibility(View.VISIBLE);
                tvNoReceipt.setVisibility(View.GONE);
            } else {
                imgReceipt.setVisibility(View.GONE);
                tvNoReceipt.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            imgReceipt.setVisibility(View.GONE);
            tvNoReceipt.setVisibility(View.VISIBLE);
        }
    }

    private void setupButtonListeners(int expectationId) {
        btnApprove.setOnClickListener(v -> {

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                APIExpectationResponse response = _DAO_Expectation.GetExpectation(expectationId);

                handler.post(() -> {
                    Expectation expectation = response.getExpectation();
                    if (expectation != null) {
                        expectation.setPaymentStatus(2);
                        expectation.setAmountPaid(expectation.getAmountPaid());
                        APIExpectationResponse resp = _DAO_Expectation.UpdateExpectation(expectation);
                        navigateToExpectationsList();
                    }
                });
            });

        });

        btnReject.setOnClickListener(v -> {

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                APIExpectationResponse response = _DAO_Expectation.GetExpectation(expectationId);

                handler.post(() -> {
                    Expectation expectation = response.getExpectation();
                    if (expectation != null) {
                        expectation.setPaymentStatus(0);
                        expectation.setAmountToApprove(0.00f);
                        APIExpectationResponse resp = _DAO_Expectation.UpdateExpectation(expectation);
                        navigateToExpectationsList();
                    }
                });
            });
        });

        btnCancel.setOnClickListener(v -> {
            finish(); // Simply close this activity
        });
    }

    private void navigateToExpectationsList() {
        Intent intent = new Intent(this, _Activity_Expectation_List.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the activity stack
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}