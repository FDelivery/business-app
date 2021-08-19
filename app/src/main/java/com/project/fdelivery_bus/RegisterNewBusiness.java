package com.project.fdelivery_bus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;
import androidx.appcompat.app.AppCompatActivity;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterNewBusiness extends AppCompatActivity {

    private EditText BusinessName, PasswordEt, Phone2, Phone1, EmailEt;
    private EditText city,street,number,floor,apartment,entrance;
    private Button Create, creditCard;
    private RetrofitInterface rtfBase = RetrofitBase.getRetrofitInterface();
    private String ID,TOKEN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_business);
        BusinessName = findViewById(R.id.BusinessName);
        PasswordEt = findViewById(R.id.PasswordEt);
        city =  findViewById(R.id.city);
        street =  findViewById(R.id.street);
        number =  findViewById(R.id.number);
        floor =  findViewById(R.id.floor);
        apartment =  findViewById(R.id.aprt);
        entrance = findViewById(R.id.entrance);
        Phone2 = findViewById(R.id.Phone2);
        Phone1 = findViewById(R.id.Phone1);
        Create=findViewById(R.id.Create);
        EmailEt = findViewById(R.id.EmailEt);
        creditCard =findViewById(R.id.pay);

        creditCard.setOnClickListener((v) -> {
                    startActivity(new Intent(this,cardPay.class));
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
//send business and register
    private void handleRegister(Business business) {

        Call<String> call = rtfBase.register(business); //we get id
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200)
                {
                   // business.setId(response.body());
                   // Toast.makeText(RegisterNewBusiness.this, "registered successfully",Toast.LENGTH_LONG).show();

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
    //connect the app auto after register
    private void connectToApp(Business business){

        HashMap<String, String> connect = new HashMap<>();
        connect.put("email",business.getEmail()) ;
        connect.put("password",business.getPassword());
        Call<String[]> call = rtfBase.connect(connect); //we get token and id
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                if(response.code() == 200)
                {
                    assert response.body() != null;
                   // business.setToken(response.body()[0]);
                   // business.setId(response.body()[1]);

                    TOKEN=response.body()[0];
                    ID=response.body()[1];
                    Toast.makeText(RegisterNewBusiness.this, "registered successfully",Toast.LENGTH_LONG).show();

                    GetUser();


                }
                if(response.code() == 400)
                {
                    Toast.makeText(RegisterNewBusiness.this, "fail to connect",Toast.LENGTH_LONG).show();

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


    // return user for sending is info to MainBusiness activity
    public void GetUser()
    {

        Intent intent = new Intent(this, MainBusiness.class);



        Call<String> call = rtfBase.getUser(ID);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {

                if(response.code() == 200)
                {

                    //success
                    intent.putExtra("businessUserInGson",response.body());
                    intent.putExtra("token", TOKEN);
                    intent.putExtra("id", ID);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); 
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
                Toast.makeText(RegisterNewBusiness.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }



}