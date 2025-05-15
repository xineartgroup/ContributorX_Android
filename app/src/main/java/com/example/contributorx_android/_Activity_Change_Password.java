package com.example.contributorx_android;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.contributorx_android.databinding.ActivityChangePasswordBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Activity_Change_Password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        EditText editTextOldPassword = findViewById(R.id.editTextOldPassword);
        EditText editTextNewPassword = findViewById(R.id.editTextNewPassword);
        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        buttonRegister.setOnClickListener(view -> {
            executor.execute(() -> {
                /*APIResponse response = _DAO_Auth.Register(editTextUsername.getText().toString(), editTextPassword.getText().toString(),
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
                });*/
            });
        });
    }

}