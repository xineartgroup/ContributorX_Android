package com.example.contributorx_android;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.Objects;

public class _Activity_Contributor_Detail extends AppCompatActivity {
    int SELECT_PHOTO = 1;
    int id = -1;
    Contributor contributor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor_detail);

        Intent intent = getIntent();

        ImageView imgContributor = findViewById(R.id.imgContributor);
        TextView lblUsername = findViewById(R.id.lblUsername);
        TextView lblName = findViewById(R.id.lblName);
        TextView lblEmail = findViewById(R.id.lblEmail);
        TextView lblPhoneNumber = findViewById(R.id.lblPhoneNumber);
        Button btnSaveContributor = findViewById(R.id.btnSaveContributor);
        Spinner cboStatus = findViewById(R.id.cboStatus);
        Button btnCancel = findViewById(R.id.btnCancel);
        ListView lstUserExpectation = findViewById(R.id.lstUserExpectation);
        ListView lstUserGroups = findViewById(R.id.lstUserGroups);

        imgContributor.setOnClickListener(view -> {
            Intent intent1 = new Intent(Intent.ACTION_PICK);
            intent1.setType("Image/*");
            //startActivityForResult(intent1, SELECT_PHOTO);
        });

        if (intent.hasExtra("com.example.contributorx_android.ITEMINDEX")){
            //EXISTING ITEM
            id = Objects.requireNonNull(intent.getExtras()).getInt("com.example.contributorx_android.ITEMINDEX");

            contributor = _DAO_Contributor.GetContributor(id);

            if (contributor != null) {
                //setImage(imgContributor, contributor.getPicture());
                lblUsername.setText(contributor.getUserName());
                lblName.setText(String.format("%s %s", contributor.getFirstname(), contributor.getLastname()));
                lblEmail.setText(contributor.getEmail());
                lblPhoneNumber.setText(contributor.getPhoneNumber());
                cboStatus.setSelection(contributor.isActive() ? 0 : 1);

                List<Grouping> groupings = _DAO_Grouping.GetGroupingsForContributor(id);
                _Layout_User_Group_List userGroupItemAdapter = new _Layout_User_Group_List(this, groupings);
                lstUserGroups.setAdapter(userGroupItemAdapter);

                List<Expectation> expectations = _DAO_Expectation.GetExpectationsForContributor(id);
                _Layout_Expectation_List2 expectationItemAdapter = new _Layout_Expectation_List2(this, expectations);
                lstUserExpectation.setAdapter(expectationItemAdapter);
            }
        }

        cboStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String selectedItem = parent.getItemAtPosition(position).toString();
                if (contributor != null) {
                    contributor.setActive(position == 0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // This method is called when the selection disappears from the spinner
                // This can happen for example when the adapter becomes empty
            }
        });

        btnSaveContributor.setOnClickListener(view -> {
            if (contributor != null) {
                _DAO_Contributor.UpdateContributor(contributor);
            }

            Intent startIntent = new Intent(getApplicationContext(), _Activity_Contributor_List.class);
            startActivity(startIntent);
        });

        btnCancel.setOnClickListener(view -> {
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Contributor_List.class);
            startActivity(startIntent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imgContributor = findViewById(R.id.imgContributor);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imgContributor.setImageBitmap(bmp);
            }
            catch (Exception ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }
}