package com.project.fdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterNewBusiness extends AppCompatActivity {

    private EditText BusinessName, PasswordEt, Address, Phone2, Phone1, EmailEt;
    private Button Create;
    private RetrofitInterface rtfBase = RetrofitBase.getRetrofitInterface();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_business);
        BusinessName = (EditText)findViewById(R.id.BusinessName);
        PasswordEt = (EditText)findViewById(R.id.PasswordEt);
        Address = (EditText)findViewById(R.id.Address);
        Phone2 = (EditText)findViewById(R.id.Phone2);
        Phone1 = (EditText)findViewById(R.id.Phone1);
        Create=(Button)findViewById(R.id.Create);
        EmailEt = findViewById(R.id.EmailEt);


        Create.setOnClickListener((v) -> {

            String email = EmailEt.getText().toString();
            String password = PasswordEt.getText().toString();
            String bName = BusinessName.getText().toString();
            String phone1 = Phone1.getText().toString();
            String phone2 = Phone2.getText().toString();
            String address = Address.getText().toString();

            //we need to check that the required fields are not empty
            if(email.isEmpty()) {
                EmailEt.setError("This field is necessary");
                return;
            }
            if(password.isEmpty()) {
                PasswordEt.setError("This field is necessary");
                return;
            }
            if(bName.isEmpty()) {
                BusinessName.setError("This field is necessary");
                return;
            }
            if(phone1.isEmpty()) {
                Phone1.setError("This field is necessary");
                return;
            }
            if(address.isEmpty()) {
                Address.setError("This field is necessary");
                return;
            }

            //check whether the given email address is valid
            if(!validations.isValidEmail(email))
            {
                EmailEt.setError("This email address is not valid");
                return;
            }
            //check whether the given password is valid-Minimum eight characters, at least one letter and one number
            if(!validations.isValidPassword(password))
            {
                PasswordEt.setError("This password is not valid, password needs minimum 8 characters, at least one letter");
                return;
            }


            Business business = phone2.isEmpty() ? new Business(email, phone1, address, bName, password)
                    : new Business(email, phone1, phone2, address, bName,password);

            handleRegister(business);


        });
    }

    //still need to handle password...
    private void handleRegister(Business business) {

        HashMap<String, String> credentials = new HashMap<>();

        credentials.put("name",business.getBusinessName());
        credentials.put("email",business.getEmail());
        credentials.put("password", business.getPassword());
        credentials.put("address", business.getAddress());
        credentials.put("phone1", business.getPhoneNumber1());
        if(business.getPhoneNumber2()!=null)
            credentials.put("phone2", business.getPhoneNumber2());


        Call<String> call = rtfBase.register(credentials);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200)
                {
                    Toast.makeText(getApplicationContext(), "registered successfully"+ response.body(),Toast.LENGTH_LONG).show();
                }
                if(response.code() == 400)
                {
                    Toast.makeText(getApplicationContext(), "you already registered",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterNewBusiness.this, t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}