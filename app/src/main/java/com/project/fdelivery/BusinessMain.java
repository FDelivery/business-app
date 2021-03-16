package com.project.fdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BusinessMain extends AppCompatActivity implements View.OnClickListener{
    Button DeliveryRequest;
    Button DeliveryList;
    Button DeliveryHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_main);
        DeliveryRequest=(Button)findViewById(R.id.DeliveryRequest);
        DeliveryRequest.setOnClickListener(this);
        DeliveryList=(Button)findViewById(R.id.DeliveryList);
        DeliveryList.setOnClickListener(this);
        DeliveryHistory=(Button)findViewById(R.id.Update);
        DeliveryHistory.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

    }
}