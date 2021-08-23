package com.project.fdelivery_bus.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.project.fdelivery_bus.classes.Business;
import com.project.fdelivery_bus.R;


public class BusinessProfile extends AppCompatActivity {
    private TextView CityMP,StreetMP,NumberMP,FlootMP,AprtMP,EntranceMP;
    private Button ChangeMP,PassChangeMP;
    private TextView TextMP,Phone1MP,Phone2MP,EmailMP;
     Business businessUser;
     String USER,ID,TOKEN;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);

        CityMP= findViewById(R.id.cityMP);
        StreetMP= findViewById(R.id.streetMP);
        NumberMP= findViewById(R.id.numberMP);
        FlootMP= findViewById(R.id.floorMP);
        AprtMP= findViewById(R.id.aprtMP);
        EntranceMP= findViewById(R.id.entranceMP);
        ChangeMP=findViewById(R.id.ChangeMP);
        EmailMP= findViewById(R.id.EmailMP);
        PassChangeMP=findViewById(R.id.PassChangeMP);
        TextMP=findViewById(R.id.TextMP);
        Phone1MP=findViewById(R.id.Phone1MP);
        Phone2MP=findViewById(R.id.Phone2MP);
        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
                USER = extras.getString("businessUserInGson");
                ID =extras.getString("id");
                TOKEN =extras.getString("token");

            businessUser = new Gson().fromJson(USER, Business.class);


        }


        TextMP.setText("welcome "+businessUser.getBusinessName());
        EmailMP.setText("My email is: "+businessUser.getEmail());
        CityMP.setText("City: "+businessUser.getAddress().getCity());
        StreetMP.setText("street: "+businessUser.getAddress().getStreet());
        NumberMP.setText("number: "+businessUser.getAddress().getNumber());
        FlootMP.setText("floor: "+businessUser.getAddress().getFloor());
        AprtMP.setText("apartment: "+businessUser.getAddress().getApartment());
        EntranceMP.setText("entrance :"+businessUser.getAddress().getEntrance());
        Phone1MP.setText("My phone is: "+businessUser.getPrimaryPhone());
        if(businessUser.getPhoneNumber2()!=null){
            if (!businessUser.getPhoneNumber2().isEmpty()) {
                Phone2MP.setText("My second phone is: " + businessUser.getPhoneNumber2());
            } else {
                Phone2MP.setText("My second phone is: No number entered");

            }
        }

        //change profile info
        ChangeMP.setOnClickListener((v) -> {

            Intent intent =new Intent(this, EditMyProfile.class);
            intent.putExtra("id",ID);
            intent.putExtra("businessUserInGson", USER);
            intent.putExtra("token",TOKEN);
            Toast.makeText(BusinessProfile.this, "fill JUST what you need", Toast.LENGTH_LONG).show();
            startActivity(intent);
            //finish();
        });

//change password
        PassChangeMP.setOnClickListener((v) -> {
            startActivity(new Intent(this, ChangePassword.class));
        });
    }










}