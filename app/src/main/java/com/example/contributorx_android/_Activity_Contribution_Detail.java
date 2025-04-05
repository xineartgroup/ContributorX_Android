package com.example.contributorx_android;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.List;

public class _Activity_Contribution_Detail extends AppCompatActivity {
    Contribution contribution = null;
    //DatePickerDialog.OnDateSetListener mDateSetListener;
    //Calendar date = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribution_detail);

        Intent intent = getIntent();

        TextView txtContributionName = findViewById(R.id.txtContributionName);
        TextView txtAmount = findViewById(R.id.txtAmount);
        TextView lblDateStart = findViewById(R.id.lblDateStart);
        Button btnSaveContribution = findViewById(R.id.btnSaveContribution);
        Button btnCancel = findViewById(R.id.btnCancel);

        if (_Activity_Login.LoggedOnUser == null){
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
            return;
        }

        /*
        lblDateStart.setOnClickListener(view -> {
            int year = date.get(Calendar.YEAR);
            int month = date.get(Calendar.MONTH);
            int day = date.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dlg = new DatePickerDialog(_Activity_Contribution_Detail.this,
                    android.R.style.Theme_DeviceDefault_Dialog,
                    mDateSetListener,
                    year, month, day);
            //dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dlg.show();
        });

        mDateSetListener = (datePicker, year, month, day) -> {
            date.set(year, month, day);
            String strDate = GetDateString(date);
            lblDateStart.setText(strDate);
        };
        */

        if (intent.hasExtra("com.example.contributorx_android.ITEMINDEX")) {
            int index = intent.getIntExtra("com.example.contributorx_android.ITEMINDEX", -1);
            if (index >= 0) {
                contribution = _DAO_Contribution.GetContribution(index);

                if (contribution != null) {
                    txtContributionName.setText(contribution.getName());
                    txtAmount.setText(String.format("%s", contribution.getAmount()));
                    // Set the date if needed
                }
            }
        }

        btnSaveContribution.setOnClickListener(view -> {
            try {
                if (contribution == null) {
                    contribution = new Contribution();

                    contribution.setName(txtContributionName.getText().toString());
                    contribution.setAmount(Float.parseFloat(txtAmount.getText().toString()));
                    //contribution.setDueDate(date);

                    contribution.setId(_DAO_Contribution.AddContribution(contribution));
                    List<Contributor> contributors = _DAO_Contributor.GetContributorsInCommunity(_Activity_Login.LoggedOnUser.getCommunityId());
                    for (int i = 0; i < contributors.size(); i++) {
                        _DAO_Expectation.AddExpectation(new Expectation(contributors.get(i).getId(), contribution.getId(), 0.00f, 0.00f, 0, ""));
                    }
                } else {
                    _DAO_Contribution.UpdateContribution(contribution);
                }

                Intent startIntent = new Intent(getApplicationContext(), _Activity_Contribution_List.class);
                startActivity(startIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Error saving: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btnCancel.setOnClickListener(view -> {
            finish();
        });
    }

    private String GetDateString(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        return day + "/" + (month + 1) + "/" + year;
    }
}