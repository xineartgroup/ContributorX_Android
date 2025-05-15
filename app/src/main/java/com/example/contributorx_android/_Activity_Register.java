package com.example.contributorx_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
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
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        ConstraintLayout.LayoutParams registerButtonParams = (ConstraintLayout.LayoutParams) buttonRegister.getLayoutParams();

        spinnerCommunity.setVisibility(View.VISIBLE);
        editTextCommunity.setVisibility(View.GONE);
        registerButtonParams.topToBottom = R.id.spinnerCommunity;

        executor.execute(() -> {
            APIResponse communitiesResponse = _DAO_Community.GetAllCommunities();

            handler.post(() -> {
                ArrayAdapter<String> communityArrayAdapter = getStringArrayAdapter(communitiesResponse);

                spinnerCommunity.setAdapter(communityArrayAdapter);
            });

            buttonRegister.setOnClickListener(view -> {
                executor.execute(() -> {
                    String role = spinnerRole.getSelectedItem().toString();

                    APIResponse communityResponse = null;

                    if (role.equals("Administrator")) {
                        String communityName = editTextCommunity.getText().toString();
                        communityResponse = _DAO_Community.AddCommunity(new Community(communityName, communityName));
                    } else {
                        String communityName = spinnerCommunity.getSelectedItem().toString();
                        communityResponse = _DAO_Community.GetCommunityByName(communityName);
                    }

                    int communityId = (communityResponse != null && communityResponse.getIsSuccess() && communityResponse.getCommunity() != null) ?
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
                            } else {
                                Toast.makeText(_Activity_Register.this, response.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } else {
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
                    spinnerCommunity.setVisibility(View.VISIBLE);
                    editTextCommunity.setVisibility(View.GONE);
                    registerButtonParams.topToBottom = R.id.spinnerCommunity;
                }
            });
        });
    }

    @NonNull
    private ArrayAdapter<String> getStringArrayAdapter(APIResponse communitiesResponse) {
        ArrayList<String> items = new ArrayList<>();
        items.add("--Select Community--");
        for (int position = 0; position < communitiesResponse.getCommunities().size(); position++) {
            Community community = communitiesResponse.getCommunities().get(position);
            if (community != null) {
                items.add(community.getName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}