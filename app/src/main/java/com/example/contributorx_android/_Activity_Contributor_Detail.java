package com.example.contributorx_android;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class _Activity_Contributor_Detail extends AppCompatActivity {
    int SELECT_PHOTO = 1;
    int contributorId = -1;
    Contributor contributor = null;
    List<Grouping> groupings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        ImageView imgContributor = findViewById(R.id.imgContributor);
        TextView lblUsername = findViewById(R.id.lblUsername);
        TextView lblName = findViewById(R.id.lblName);
        TextView lblEmail = findViewById(R.id.lblEmail);
        TextView lblPhoneNumber = findViewById(R.id.lblPhoneNumber);
        Spinner cboStatus = findViewById(R.id.cboStatus);
        Spinner cboGroups = findViewById(R.id.cboGroups);
        ListView lstUserExpectation = findViewById(R.id.lstUserExpectation);
        ListView lstUserGroups = findViewById(R.id.lstUserGroups);
        Button btnSaveContributor = findViewById(R.id.btnSaveContributor);
        Button btnCancel = findViewById(R.id.btnCancel);

        imgContributor.setOnClickListener(view -> {
            Intent intent1 = new Intent(Intent.ACTION_PICK);
            intent1.setType("Image/*");
            //startActivityForResult(intent1, SELECT_PHOTO);
        });

        if (intent.hasExtra("com.example.contributorx_android.ITEMINDEX")){
            //EXISTING ITEM
            contributorId = Objects.requireNonNull(intent.getExtras()).getInt("com.example.contributorx_android.ITEMINDEX");

            contributor = _DAO_Contributor.GetContributor(contributorId);

            if (contributor != null) {
                //setImage(imgContributor, contributor.getPicture());
                lblUsername.setText(contributor.getUserName());
                lblName.setText(String.format("%s %s", contributor.getFirstname(), contributor.getLastname()));
                lblEmail.setText(contributor.getEmail());
                lblPhoneNumber.setText(contributor.getPhoneNumber());
                cboStatus.setSelection(contributor.isActive() ? 0 : 1);
                cboGroups.setAdapter(getStringArrayAdapter());

                groupings = _DAO_Grouping.GetGroupingsForContributor(contributorId);
                _Layout_User_Group_List userGroupItemAdapter = new _Layout_User_Group_List(this, groupings);
                lstUserGroups.setAdapter(userGroupItemAdapter);

                List<Expectation> expectations = _DAO_Expectation.GetExpectationsForContributor(contributorId);
                _Layout_Expectation_List2 expectationItemAdapter = new _Layout_Expectation_List2(this, expectations);
                lstUserExpectation.setAdapter(expectationItemAdapter);
            }
        }

        cboStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

        cboGroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Group group = _DAO_Group.GetGroupByName(selectedItem);
                if (group != null) {
                    boolean found = false;
                    for (Grouping grouping : groupings){
                        if (grouping.getGroupId() == group.getId()){
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        Toast.makeText(getApplicationContext(), "Contributor already belongs to the selected group", Toast.LENGTH_LONG).show();
                    }
                    else{
                        groupings.add(new Grouping(contributorId, group.getId()));
                        _Layout_User_Group_List userGroupItemAdapter = new _Layout_User_Group_List(getApplicationContext(), groupings);
                        lstUserGroups.setAdapter(userGroupItemAdapter);
                        cboGroups.setSelection(0);
                    }
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
                List<Grouping> old_groupings = _DAO_Grouping.GetGroupingsForContributor(contributorId);

                // Find items to delete (in old list but not in new list)
                for (Grouping old_grouping : old_groupings) {
                    boolean found = false;
                    for (Grouping grouping : groupings) {
                        if (old_grouping.getGroupId() == grouping.getGroupId()) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        _DAO_Grouping.DeleteGrouping(old_grouping.getId());
                    }
                }

                // Add new items and update existing ones
                for (Grouping grouping : groupings) {
                    boolean found = false;
                    for (Grouping old_grouping : old_groupings) {
                        if (grouping.getId() == old_grouping.getId()){
                            found = true;
                            break;
                        }
                    }
                    if (!found){
                        _DAO_Grouping.AddGrouping(grouping);
                    }
                }
            }

            Intent startIntent = new Intent(getApplicationContext(), _Activity_Contributor_List.class);
            startActivity(startIntent);
        });

        btnCancel.setOnClickListener(view -> {
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Contributor_List.class);
            startActivity(startIntent);
        });
    }

    @NonNull
    private ArrayAdapter<String> getStringArrayAdapter() {
        List<Group> groups = _DAO_Group.GetAllGroups();
        ArrayList<String> items = new ArrayList<>();
        items.add("");
        for (int position = 0; position < groups.size(); position++) {
            Group group = groups.get(position);
            if (group != null) {
                items.add(group.getName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
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