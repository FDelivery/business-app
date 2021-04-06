package com.project.fdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
// in here the business can see a list of his on going delivers
public class DeliveryTable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_table);
    }
}