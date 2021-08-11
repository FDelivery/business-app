package com.project.fdelivery_bus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;


public class BusinessProfile extends AppCompatActivity {
    private TextView AddressMP;
    private TextView EmailMP;
    private Button ChangeMP;
    private Button PassChangeMP;
    private TextView TextMP;
    private TextView Phone1MP;
    private TextView Phone2MP;
    private Button addDelivery;
    private RetrofitInterface rtfBase;
     Business businessUser;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);

        AddressMP=(TextView)findViewById(R.id.AddressMP);
        ChangeMP=(Button)findViewById(R.id.ChangeMP);
        EmailMP=(TextView) findViewById(R.id.EmailMP);
        PassChangeMP=(Button)findViewById(R.id.PassChangeMP);
        addDelivery=(Button)findViewById(R.id.buttonForAddDeliver);
        rtfBase = RetrofitBase.getRetrofitInterface();
        TextMP=(TextView)findViewById(R.id.TextMP);
        Phone1MP=(TextView)findViewById(R.id.Phone1MP);
        Phone2MP=(TextView)findViewById(R.id.Phone2MP);
        Bundle extras = getIntent().getExtras();
        String FromIntent,ID;
        if(extras!=null)
        {
                FromIntent = extras.getString("businessUserInGson");
                ID =extras.getString("id");
                businessUser = new Gson().fromJson(FromIntent, Business.class);
                Log.i("ttt", businessUser.getFirstName());
                businessUser.setId(ID);


        }


        TextMP.setText("welcome "+businessUser.getFirstName()+ " "+businessUser.getLastName());
        EmailMP.setText("My email is: "+businessUser.getEmail());
        AddressMP.setText("My address is:\n"+ "City: "+businessUser.getAddress().getCity()+"\nstreet: "+businessUser.getAddress().getStreet()
        +"\nnumber: "+businessUser.getAddress().getNumber());
        Phone1MP.setText("My phone is: "+businessUser.getPrimaryPhone());
        if(!businessUser.getPhoneNumber2().isEmpty())
        {
            Phone2MP.setText("My second phone is: "+businessUser.getPhoneNumber2());
        }
        else{
            Phone2MP.setText("My second phone is: No number entered");

        }

       // String finalFromIntent = FromIntent;
        ChangeMP.setOnClickListener((v) -> {

            Intent intent2 =new Intent(this, EditMyProfile.class);
            intent2.putExtra("id",businessUser.getId());
            Toast.makeText(BusinessProfile.this, "fill JUST what you need", Toast.LENGTH_LONG).show();
            startActivity(intent2);

        });

        addDelivery.setOnClickListener((v) -> {
            startActivity(new Intent(this, newDelivery.class));
        });
        PassChangeMP.setOnClickListener((v) -> {
            startActivity(new Intent(this, ChangePassword.class));
        });
    }










}