package com.project.fdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    private EditText BusinessName, PasswordEt, Address, Phone2, Phone1;
    Button Create;
    RetrofitInterface rtfBase;
    TextView EmailEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_business);
        BusinessName = (EditText)findViewById(R.id.Name);
        PasswordEt = (EditText)findViewById(R.id.Password);
        Address = (EditText)findViewById(R.id.Adress);
        Phone2 = (EditText)findViewById(R.id.Phone2);
        Phone1 = (EditText)findViewById(R.id.Phone1);
        Create=(Button)findViewById(R.id.Update);
        EmailEt = findViewById(R.id.RegEmail);


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

            Business business = phone2.isEmpty() ? new Business(email, phone1, address, bName)
                    : new Business(email, phone1, phone2, address, bName);

            handleRegister(business);


        });
    }

    //still need to handle password...
    private void handleRegister(Business business) {

        HashMap<String, String> credentials = new HashMap<>();

        credentials.put("name",business.getBusinessName());
        credentials.put("email",business.getEmail());
        credentials.put("password", "blablabla");
        credentials.put("address", business.getAddress());
        credentials.put("phone1", business.getPhoneNumber1());
        if(!business.getPhoneNumber2().isEmpty())
            credentials.put("phone2", business.getPhoneNumber2());


        Call<Void> call = rtfBase.register(credentials);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200)
                {
                    Toast.makeText(getApplicationContext(), "registered successfully",Toast.LENGTH_LONG).show();
                }
                if(response.code() == 400)
                {
                    Toast.makeText(getApplicationContext(), "you already registered",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterNewBusiness.this, t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}