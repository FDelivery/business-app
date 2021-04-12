package com.project.fdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditMyProfile extends AppCompatActivity {
    private EditText AddressEP;
    private EditText EmailEP;
    private Button ChangeEP;
    private TextView TextEP;
    private EditText Phone1EP;
    private EditText Phone2EP;
    private EditText NameEP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);

        AddressEP=(EditText)findViewById(R.id.AddressEP);
        ChangeEP=(Button)findViewById(R.id.ChangeEP);
        EmailEP=(EditText) findViewById(R.id.EmailEP);
        NameEP=(EditText)findViewById(R.id.NameEP);
        TextEP=(TextView)findViewById(R.id.TextEP);
        Phone1EP=(EditText)findViewById(R.id.Phone1EP);
        Phone2EP=(EditText)findViewById(R.id.Phone2EP);

        ChangeEP.setOnClickListener((v) -> {
            startActivity(new Intent(this, BusinessProfile.class));
        });
    }
}