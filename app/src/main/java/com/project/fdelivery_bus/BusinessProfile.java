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
     Business business;


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

     //   Bundle extras = getIntent().getExtras();
    //    String idFromIntent= extras.getString("id");

//Log.i("myTest",idFromIntent);

      //  GetUser(idFromIntent);





       // TextMP.setText("welcome "+business.getFirstName()+ " "+business.getLastName());


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







    // get- in id, return user
    private void GetUser(String id) // need to know how to use in accepted user
    {


        Log.i("myTest2",id);

        Call<String> call = rtfBase.getUser(id);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {

                if(response.code() == 200)
                {

                    //success
                    // Business b=new Business(response.body().getEmail(), response.body().getPrimaryPhone(), new Address("f","qw","qw"),response.body().getBusinessName(),response.body().getFirstName(),response.body().getLastName(),response.body().getPassword());
                    // Business c = new Business(response.body());
                    Log.i("TEST1",response.body());
                    Business businessUser = new Gson().fromJson(response.body(),Business.class);
                    Log.i("TEST2",businessUser.getFirstName());
                    Toast.makeText(BusinessProfile.this, "We found your user", Toast.LENGTH_LONG).show();

                   //   business = new Business(GSON);


                }


                if(response.code() == 400 || response.code()==500)
                {
                    //failure
                    Toast.makeText(BusinessProfile.this, "this ID do not exist", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(BusinessProfile.this, "Something went wrong " +t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }


}