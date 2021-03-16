package com.project.fdelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        business = (Button) findViewById(R.id.business);
        business.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == business) {
            Intent intent = new Intent(this, FillDetailsBusiness.class);
            startActivity(intent);

        }
    }
}