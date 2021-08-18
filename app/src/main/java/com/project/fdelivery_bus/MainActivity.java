package com.project.fdelivery_bus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText EmailEt;
    private EditText PasswordEt;
    private Button logIn;
    private TextView ForgotPassword;
    private TextView RegisterNewBusiness;
    private RetrofitInterface rtfBase;
    Intent intent;
    public static Activity a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rtfBase = RetrofitBase.getRetrofitInterface();
        EmailEt = findViewById(R.id.Email);
        PasswordEt = findViewById(R.id.Password);
        logIn=findViewById(R.id.Connect);
        ForgotPassword = findViewById(R.id.forgotPass);
        RegisterNewBusiness = findViewById(R.id.newBusiness);

        intent = new Intent(this, MainBusiness.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //שלא יהיה ניתן לחזור אחורה

        logIn.setOnClickListener((v) -> {
            String email = EmailEt.getText().toString();
            if(email.isEmpty())
            {
            //     Toast.makeText(MainActivity.this, "Email is a required field" , Toast.LENGTH_LONG).show();
                EmailEt.setError("This field is necessary");
                return;
            }
            if(PasswordEt.getText().toString().isEmpty())
            {
              //  Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                PasswordEt.setError("This field is necessary");
                return;
            }
            handleConnect();
        });
        ForgotPassword.setOnClickListener((v)->{
         //   GetUser("6106919d6ffd57c4090b6285");
        //    Intent i=new Intent(this,newDelivery.class);
        //    i.putExtra("token",response.body());

        //    startActivity(i);


        });
        RegisterNewBusiness.setOnClickListener((v) -> {
            startActivity(new Intent(this, RegisterNewBusiness.class));
            finish();
        });


    }
//return token and id if the user is exist
    private void handleConnect() {
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("email",EmailEt.getText().toString());
        credentials.put("password",PasswordEt.getText().toString());
        Call<String[]> call = rtfBase.connect(credentials);

        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                if(response.code() == 200)
                {
                    //success
                    Toast.makeText(MainActivity.this, "You have logged in successfully", Toast.LENGTH_LONG).show();
                   // business.setToken(response.body());

                    String[] TokenAndId = new String[2];

                    TokenAndId=response.body();
                  //  Log.i("token1",TokenAndId[0]);
                   // Log.i("id1",TokenAndId[1]);
                    intent.putExtra("token",TokenAndId[0]);
                    GetUser(TokenAndId[1]);


                }
                if(response.code() == 400 || response.code() == 401)
                {
                    //failure
                    Toast.makeText(MainActivity.this, "log in failed-try again", Toast.LENGTH_LONG).show();

                }
                if(response.code() == 500)
                {
                    //failure
                    Toast.makeText(MainActivity.this, "user do not exist", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong" +t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }


    // get- in id, return user
    public void GetUser(String id)
    {



       // Log.i("myTest2",id);

        Call<String> call = rtfBase.getUser(id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {

                if(response.code() == 200)
                {

                    //success

                  //  Log.i("TEST1",response.body());
                  //  Business businessUser = new Gson().fromJson(response.body(),Business.class);
                 //   Log.i("TEST2",businessUser.getFirstName());
                  //  Toast.makeText(MainActivity.this, "We found your user", Toast.LENGTH_LONG).show();
                    intent.putExtra("businessUserInGson",response.body());
                    intent.putExtra("id",id);

                    startActivity(intent);


                }


                if(response.code() == 400 || response.code()==500)
                {
                    //failure
                    Toast.makeText(MainActivity.this, "this ID do not exist", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong " +t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

}
