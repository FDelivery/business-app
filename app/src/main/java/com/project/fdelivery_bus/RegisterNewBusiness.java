package com.project.fdelivery_bus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterNewBusiness extends AppCompatActivity {

    private EditText BusinessName, PasswordEt, Phone2, Phone1, EmailEt;
    private EditText city,street,number,floor,apartment,entrance;
    private Button Create, creditCard;
    private RetrofitInterface rtfBase = RetrofitBase.getRetrofitInterface();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_business);
        BusinessName = (EditText)findViewById(R.id.BusinessName);
        PasswordEt = (EditText)findViewById(R.id.PasswordEt);
        city = (EditText) findViewById(R.id.city);
        street = (EditText) findViewById(R.id.street);
        number = (EditText) findViewById(R.id.number);
        floor = (EditText) findViewById(R.id.floor);
        apartment = (EditText) findViewById(R.id.aprt);
        entrance = (EditText)findViewById(R.id.entrance);

        Phone2 = (EditText)findViewById(R.id.Phone2);
        Phone1 = (EditText)findViewById(R.id.Phone1);
        Create=(Button)findViewById(R.id.Create);
        EmailEt = findViewById(R.id.EmailEt);
        creditCard =findViewById(R.id.pay);

        creditCard.setOnClickListener((v) -> {
                    Intent intent=new Intent(this,cardPay.class);
                    startActivity(intent);
                });
        Create.setOnClickListener((v) -> {

            String email = EmailEt.getText().toString();
            String password = PasswordEt.getText().toString();
            String bName = BusinessName.getText().toString();
            String phone1 = Phone1.getText().toString();
            String phone2 = Phone2.getText().toString();


            String City = city.getText().toString();
            String Street = street.getText().toString();
            String Floor = floor.getText().toString();
            String Apartment = apartment.getText().toString();
            String Entrance = entrance.getText().toString();
            String Number = number.getText().toString();



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
            if(Number.isEmpty()) {
                number.setError("This field is necessary");
                return;
            }
            if(Street.isEmpty()) {
                street.setError("This field is necessary");
                return;
            }
            if(City.isEmpty()) {
                city.setError("This field is necessary");
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


            Address address = new Address(City, Street, Number);

            if (!Floor.isEmpty()) address.setFloor(Floor);
            if (!Entrance.isEmpty()) address.setEntrance(Entrance);
            if (!Apartment.isEmpty()) address.setApartment(Apartment);



            Business business = phone2.isEmpty() ? new Business(email, phone1, address, bName, password)
                    : new Business(email, phone1, phone2, address, bName, password);

            handleRegister(business);


        });
    }

    //still need to handle password...
    private void handleRegister(Business business) {


        Call<String> call = rtfBase.register(business); //we get id
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200)
                {
                    business.setId(response.body());
                    Toast.makeText(RegisterNewBusiness.this, "registered successfully",Toast.LENGTH_LONG).show();

                    connectToApp(business);


                }
                if(response.code() == 400)
                {
                    Toast.makeText(RegisterNewBusiness.this, "you already registered",Toast.LENGTH_LONG).show();

                }
                if(response.code() == 404)
                {
                    Toast.makeText(RegisterNewBusiness.this, "something wrong",Toast.LENGTH_LONG).show();

                }
            }


            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterNewBusiness.this, t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void connectToApp(Business business){

        HashMap<String, String> help = new HashMap<>();
        help.put("email",business.getEmail()) ;
        help.put("password",business.getPassword());
        Call<String[]> call = rtfBase.connect(help); //we get token and id
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                if(response.code() == 200)
                {
                    assert response.body() != null;
                    business.setToken(response.body()[0]);
                    business.setId(response.body()[1]);
                    Toast.makeText(RegisterNewBusiness.this, "successfully",Toast.LENGTH_LONG).show();

                    GetUser(business.getId());


                }
                if(response.code() == 400)
                {
                    Toast.makeText(RegisterNewBusiness.this, "we have a problem",Toast.LENGTH_LONG).show();

                }
                if(response.code() == 401)
                {
                    Toast.makeText(RegisterNewBusiness.this, "Email or password invalid",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                Toast.makeText(RegisterNewBusiness.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }


    // get- in id, return user
    public void GetUser(String id) // need to know how to use in accepted user
    {

        Intent intent = new Intent(this, MainBusiness.class);


        Call<String> call = rtfBase.getUser(id);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {

                if(response.code() == 200)
                {

                    //success

                      Log.i("TEST123",response.body());
                    //  Business businessUser = new Gson().fromJson(response.body(),Business.class);
                    //   Log.i("TEST2",businessUser.getFirstName());
                    //  Toast.makeText(MainActivity.this, "We found your user", Toast.LENGTH_LONG).show();
                    intent.putExtra("businessUserInGson",response.body());
                    startActivity(intent);


                }


                if(response.code() == 400 || response.code()==500)
                {
                    //failure
                    Toast.makeText(RegisterNewBusiness.this, "this ID do not exist", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterNewBusiness.this, "Something went wrong " +t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }



}