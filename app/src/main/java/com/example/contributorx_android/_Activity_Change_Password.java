package com.example.contributorx_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        if (APIClass.LoggedOnUser == null){
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
            return;
        }

        Contributor contributor = APIClass.LoggedOnUser;

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        buttonRegister.setOnClickListener(view -> {
            GenericObj1 obj = new GenericObj1();
            obj.setId(contributor.getId());
            obj.setPasswordOld(editTextOldPassword.getText().toString());
            obj.setPasswordNew(editTextNewPassword.getText().toString());
            obj.setPasswordConfirm(editTextConfirmPassword.getText().toString());
            executor.execute(() -> {
                APIResponse response = _DAO_Contributor.ChangePassword(obj);

                handler.post(() -> {
                    if (response != null) {
                        if (response.getIsSuccess()) {
                            Toast.makeText(_Activity_Change_Password.this, "Password Changed Successfully!!!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            Toast.makeText(_Activity_Change_Password.this, response.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(_Activity_Change_Password.this, "Password Change failed!!!", Toast.LENGTH_LONG).show();
                    }
                });
            });
        });
    }

}