package com.project.fdelivery_bus;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

// in here the business can see a list of his on done delivers
public class DeliveryHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_history);
    }
}