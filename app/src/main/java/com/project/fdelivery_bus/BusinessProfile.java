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
    private TextView CityMP,StreetMP,NumberMP,FlootMP,AprtMP,EntranceMP;
    private TextView EmailMP;
    private Button ChangeMP;
    private Button PassChangeMP;
    private TextView TextMP;
    private TextView Phone1MP;
    private TextView Phone2MP;
    private RetrofitInterface rtfBase;
     Business businessUser;
    String FromIntent,ID,TOKEN;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);

        CityMP=(TextView) findViewById(R.id.cityMP);
        StreetMP=(TextView) findViewById(R.id.streetMP);
        NumberMP=(TextView) findViewById(R.id.numberMP);
        FlootMP=(TextView) findViewById(R.id.floorMP);
        AprtMP=(TextView) findViewById(R.id.aprtMP);
        EntranceMP=(TextView) findViewById(R.id.entranceMP);
        ChangeMP=(Button)findViewById(R.id.ChangeMP);
        EmailMP=(TextView) findViewById(R.id.EmailMP);
        PassChangeMP=(Button)findViewById(R.id.PassChangeMP);
        rtfBase = RetrofitBase.getRetrofitInterface();
        TextMP=(TextView)findViewById(R.id.TextMP);
        Phone1MP=(TextView)findViewById(R.id.Phone1MP);
        Phone2MP=(TextView)findViewById(R.id.Phone2MP);
        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
                FromIntent = extras.getString("businessUserInGson");
                ID =extras.getString("id");
                TOKEN =extras.getString("token");

            businessUser = new Gson().fromJson(FromIntent, Business.class);
               // Log.i("ttt", businessUser.getFirstName());
                businessUser.setId(ID);
                businessUser.setToken(TOKEN);

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
       // String finalFromIntent = FromIntent;
        ChangeMP.setOnClickListener((v) -> {

            Intent intent2 =new Intent(this, EditMyProfile.class);
            intent2.putExtra("id",ID);
            intent2.putExtra("businessUserInGson",FromIntent);

            Log.i("xxxx1",ID);
            Toast.makeText(BusinessProfile.this, "fill JUST what you need", Toast.LENGTH_LONG).show();
            startActivity(intent2);

        });


        PassChangeMP.setOnClickListener((v) -> {
            startActivity(new Intent(this, ChangePassword.class));
        });
    }










}