package com.example.contributorx_android;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.Calendar;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class _Activity_Contribution_Detail extends AppCompatActivity {
    Contribution contribution = null;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Calendar date = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribution_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        TextView txtContributionName = findViewById(R.id.txtContributionName);
        TextView txtAmount = findViewById(R.id.txtAmount);
        TextView lblDateStart = findViewById(R.id.lblDateStart);
        Button btnSaveContribution = findViewById(R.id.btnSaveContribution);
        Button btnCancel = findViewById(R.id.btnCancel);

        if (APIClass.LoggedOnUser == null){
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
            return;
        }

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
            String strDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(date.getTime());
            lblDateStart.setText(strDate);
        };

        if (intent.hasExtra("com.example.contributorx_android.ITEMINDEX")) {
            int id = intent.getIntExtra("com.example.contributorx_android.ITEMINDEX", -1);
            if (id >= 0) {
                contribution = _DAO_Contribution.GetContribution(id);

                if (contribution != null) {
                    txtContributionName.setText(contribution.getName());
                    txtAmount.setText(String.format("%s", contribution.getAmount()));
                    lblDateStart.setText(new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(date.getTime()));
                }
            }
        }

        btnSaveContribution.setOnClickListener(view -> {
            try {
                if (contribution == null) {
                    contribution = new Contribution();

                    contribution.setName(txtContributionName.getText().toString());
                    contribution.setAmount(Float.parseFloat(txtAmount.getText().toString()));
                    contribution.setDueDate(date.toString());

                    contribution.setId(_DAO_Contribution.AddContribution(contribution));
                    List<Contributor> contributors = _DAO_Contributor.GetContributorsInCommunity(APIClass.LoggedOnUser.getCommunityId());
                    for (int i = 0; i < contributors.size(); i++) {
                        Expectation expectation = new Expectation(contributors.get(i).getId(), contribution.getId(), 0.00f, 0.00f, 0, "");
                        expectation.setId(_DAO_Expectation.AddExpectation(expectation));
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

        btnCancel.setOnClickListener(view -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent startIntent = _Activity_Login.MenuAction(item.getItemId(), getApplicationContext());

        if (startIntent != null) {
            startActivity(startIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}