package com.project.fdelivery_bus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePassword extends AppCompatActivity {
    private EditText NewPassword;
    private EditText ConformNewPassword;
    private Button ConformPasswordChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        NewPassword=findViewById(R.id.NewPassword);
        ConformNewPassword=findViewById(R.id.ConformNewPassword);
        ConformPasswordChange=findViewById(R.id.ConformPasswordChange);


        ConformPasswordChange.setOnClickListener((v) -> {
             // here need to be code for saving the new password
                finish();
          //  startActivity(new Intent(this, MainBusiness.class));
        });
    }
}