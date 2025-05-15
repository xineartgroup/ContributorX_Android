package com.example.contributorx_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Activity_Update_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextFirstName = findViewById(R.id.editTextFirstName);
        EditText editTextLastName = findViewById(R.id.editTextLastName);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        EditText editTextRole = findViewById(R.id.editTextRole);
        ImageView imageViewPreview = findViewById(R.id.imageViewPreview);
        Button buttonUploadPicture = findViewById(R.id.buttonUploadPicture);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        Button buttonCancel = findViewById(R.id.buttonCancel);

        if (APIClass.LoggedOnUser == null){
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
            return;
        }

        Contributor contributor = APIClass.LoggedOnUser;

        editTextUsername.setText(contributor.getUserName());
        editTextFirstName.setText(contributor.getFirstname());
        editTextLastName.setText(contributor.getLastname());
        editTextEmail.setText(contributor.getEmail());
        editTextPhoneNumber.setText(contributor.getPhoneNumber());
        editTextRole.setText(contributor.getRole());

        editTextUsername.setEnabled(false);
        editTextRole.setEnabled(false);

        ConstraintLayout.LayoutParams registerButtonParams = (ConstraintLayout.LayoutParams) buttonRegister.getLayoutParams();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        buttonRegister.setOnClickListener(view -> {
            executor.execute(() -> {
                contributor.setFirstname(editTextFirstName.getText().toString());
                contributor.setLastname(editTextLastName.getText().toString());
                contributor.setEmail(editTextEmail.getText().toString());
                contributor.setPhoneNumber(editTextPhoneNumber.getText().toString());

                APIResponse response = _DAO_Contributor.UpdateContributor(contributor);

                handler.post(() -> {
                    if (response != null) {
                        if (response.getIsSuccess()) {
                            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
                            startActivity(startIntent);
                            Toast.makeText(_Activity_Update_Profile.this, "Update was Successful!!!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(_Activity_Update_Profile.this, response.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(_Activity_Update_Profile.this, "Update failed!!!", Toast.LENGTH_LONG).show();
                    }
                });
            });
        });

        buttonCancel.setOnClickListener(view -> {

        });
    }
}