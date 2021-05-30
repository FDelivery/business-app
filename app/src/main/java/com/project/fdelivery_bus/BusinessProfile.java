package com.project.fdelivery_bus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BusinessProfile extends AppCompatActivity {
    private EditText AddressMP;
    private EditText EmailMP;
    private Button ChangeMP;
    private Button PassChangeMP;
    private TextView TextMP;
    private EditText Phone1MP;
    private EditText Phone2MP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);

        AddressMP=(EditText)findViewById(R.id.AddressMP);
        ChangeMP=(Button)findViewById(R.id.ChangeMP);
        EmailMP=(EditText) findViewById(R.id.EmailMP);
        PassChangeMP=(Button)findViewById(R.id.PassChangeMP);
        TextMP=(TextView)findViewById(R.id.TextMP);
        Phone1MP=(EditText)findViewById(R.id.Phone1MP);
        Phone2MP=(EditText)findViewById(R.id.Phone2MP);

        ChangeMP.setOnClickListener((v) -> {
            startActivity(new Intent(this, EditMyProfile.class));
        });

        PassChangeMP.setOnClickListener((v) -> {
            startActivity(new Intent(this, ChangePassword.class));
        });
    }
}