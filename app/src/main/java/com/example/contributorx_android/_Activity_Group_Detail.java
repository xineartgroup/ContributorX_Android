package com.example.contributorx_android;
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

public class _Activity_Group_Detail extends AppCompatActivity {
    Group group = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        TextView txtGroupName = findViewById(R.id.txtGroupName);
        TextView txtDescription = findViewById(R.id.txtDescription);
        Button btnSaveGroup = findViewById(R.id.btnSaveGroup);
        Button btnCancel = findViewById(R.id.btnCancel);

        if (_Activity_Login.LoggedOnUser == null){
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
            return;
        }

        if (intent.hasExtra("com.example.contributorx_android.ITEMINDEX")) {
            int id = intent.getIntExtra("com.example.contributorx_android.ITEMINDEX", -1);
            if (id >= 0) {
                group = _DAO_Group.GetGroup(id);

                if (group != null) {
                    txtGroupName.setText(group.getName());
                    txtDescription.setText(group.getDescription());
                }
            }
        }

        btnSaveGroup.setOnClickListener(view -> {
            try {
                if (group == null) {
                    group = new Group();

                    group.setName(txtGroupName.getText().toString());
                    group.setDescription(txtDescription.getText().toString());
                    group.setCommunityId(_Activity_Login.LoggedOnUser.getCommunityId());

                    group.setId(_DAO_Group.AddGroup(group));
                } else {
                    _DAO_Group.UpdateGroup(group);
                }

                Intent startIntent = new Intent(getApplicationContext(), _Activity_Group_List.class);
                startActivity(startIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Error saving: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btnCancel.setOnClickListener(view -> {
            finish();
        });
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