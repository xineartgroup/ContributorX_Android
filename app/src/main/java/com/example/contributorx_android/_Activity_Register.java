package com.example.contributorx_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Activity_Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        EditText editTextFirstName = findViewById(R.id.editTextFirstName);
        EditText editTextLastName = findViewById(R.id.editTextLastName);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        EditText editTextCommunity = findViewById(R.id.editTextCommunity);
        ImageView imageViewPreview = findViewById(R.id.imageViewPreview);
        Spinner spinnerRole = findViewById(R.id.spinnerRole);
        Spinner spinnerCommunity = findViewById(R.id.spinnerCommunity);
        Button buttonUploadPicture = findViewById(R.id.buttonUploadPicture);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        ConstraintLayout.LayoutParams registerButtonParams = (ConstraintLayout.LayoutParams) buttonRegister.getLayoutParams();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        buttonRegister.setOnClickListener(view -> {
            executor.execute(() -> {
                String role = spinnerRole.getSelectedItem().toString();

                String communityName = role.equals("Administrator") ?
                        editTextCommunity.getText().toString() : spinnerCommunity.getSelectedItem().toString();

                APIResponse communityResponse = _DAO_Community.GetCommunityByName(communityName);

                int communityId = communityResponse.getIsSuccess() && communityResponse.getCommunity() != null ?
                        communityResponse.getCommunity().getId() : 0;

                APIResponse response = _DAO_Auth.Register(editTextUsername.getText().toString(), editTextPassword.getText().toString(),
                        editTextFirstName.getText().toString(), editTextLastName.getText().toString(),
                        editTextEmail.getText().toString(), role,
                        editTextPhoneNumber.getText().toString(), communityId,
                        "", true
                        );

                handler.post(() -> {
                    if (response != null) {
                        if (response.getIsSuccess()) {
                            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
                            startActivity(startIntent);
                            Toast.makeText(_Activity_Register.this, "Registration was Successful!!!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(_Activity_Register.this, response.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(_Activity_Register.this, "Registration failed!!!", Toast.LENGTH_LONG).show();
                    }
                });
            });
        });

        buttonLogin.setOnClickListener(view -> {
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
        });

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String role = parent.getItemAtPosition(position).toString();

                if (role.equals("Administrator")) {
                    spinnerCommunity.setVisibility(View.GONE);
                    editTextCommunity.setVisibility(View.VISIBLE);
                    registerButtonParams.topToBottom = R.id.editTextCommunity;
                } else {
                    spinnerCommunity.setVisibility(View.VISIBLE);
                    editTextCommunity.setVisibility(View.GONE);
                    registerButtonParams.topToBottom = R.id.spinnerCommunity;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optionally handle the case where no item is selected initially
                spinnerCommunity.setVisibility(View.VISIBLE);
                editTextCommunity.setVisibility(View.GONE);
                registerButtonParams.topToBottom = R.id.spinnerCommunity;
            }
        });
    }
}