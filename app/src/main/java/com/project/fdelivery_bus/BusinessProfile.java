package com.project.fdelivery_bus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessProfile extends AppCompatActivity {
    private EditText AddressMP;
    private EditText EmailMP;
    private Button ChangeMP;
    private Button PassChangeMP;
    private TextView TextMP;
    private EditText Phone1MP;
    private EditText Phone2MP;
    private Button addDelivery;
    private RetrofitInterface rtfBase;
     Business businessUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);

        AddressMP=(EditText)findViewById(R.id.AddressMP);
        ChangeMP=(Button)findViewById(R.id.ChangeMP);
        EmailMP=(EditText) findViewById(R.id.EmailMP);
        PassChangeMP=(Button)findViewById(R.id.PassChangeMP);
        addDelivery=(Button)findViewById(R.id.buttonForAddDeliver);
        rtfBase = RetrofitBase.getRetrofitInterface();
        TextMP=(TextView)findViewById(R.id.TextMP);
        Phone1MP=(EditText)findViewById(R.id.Phone1MP);
        Phone2MP=(EditText)findViewById(R.id.Phone2MP);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
                String idFromIntent = extras.getString("businessUserInGson");
                businessUser = new Gson().fromJson(idFromIntent, Business.class);
                Log.i("ttt", businessUser.getBusinessName());

        }
        Log.i("ttt2", businessUser.getBusinessName());






        TextMP.setText("welcome "+businessUser.getFirstName()+ " "+businessUser.getLastName());


        ChangeMP.setOnClickListener((v) -> {
            startActivity(new Intent(this, EditMyProfile.class));
        });

        addDelivery.setOnClickListener((v) -> {
            startActivity(new Intent(this, newDelivery.class));
        });
        PassChangeMP.setOnClickListener((v) -> {
            startActivity(new Intent(this, ChangePassword.class));
        });
    }










}