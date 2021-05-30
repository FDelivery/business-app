package com.project.fdelivery_bus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainBusiness extends AppCompatActivity {
    private ImageButton deliveryHistory;
    private ImageButton deliveryRequest;
    private ImageButton deliveryList;
    private ImageButton myprofile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_business);

        deliveryHistory=(ImageButton)findViewById(R.id.deliveryHistory);
        deliveryRequest=(ImageButton)findViewById(R.id.deliveryRequest);
        deliveryList=(ImageButton)findViewById(R.id.deliveryList);
        myprofile=(ImageButton)findViewById(R.id.myprofile);

        deliveryList.setOnClickListener((v) -> {
            startActivity(new Intent(this, DeliveryTable.class));
        });

        myprofile.setOnClickListener((v) -> {
            startActivity(new Intent(this, BusinessProfile.class));
        });

        deliveryHistory.setOnClickListener((v) -> {
            startActivity(new Intent(this, DeliveryHistory.class));
        });

        deliveryRequest.setOnClickListener((v) -> {
            startActivity(new Intent(this, newDelivery.class));
        });
    }
}