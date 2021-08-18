package com.project.fdelivery_bus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    private TextView RegisterNewBusiness;
    private RetrofitInterface rtfBase;
    Intent intent;
    String TOKEN,ID;
   // public static Activity a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rtfBase = RetrofitBase.getRetrofitInterface();
        EmailEt = findViewById(R.id.Email);
        PasswordEt = findViewById(R.id.Password);
        logIn=findViewById(R.id.Connect);
        RegisterNewBusiness = findViewById(R.id.newBusiness);

        intent = new Intent(this, MainBusiness.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //שלא יהיה ניתן לחזור אחורה

        logIn.setOnClickListener((v) -> {
            String email = EmailEt.getText().toString();
            if(email.isEmpty())
            {
                EmailEt.setError("This field is necessary");
                return;
            }
            if(PasswordEt.getText().toString().isEmpty())
            {
                PasswordEt.setError("This field is necessary");
                return;
            }
            handleConnect();
        });


        RegisterNewBusiness.setOnClickListener((v) -> {
            startActivity(new Intent(this, RegisterNewBusiness.class));

        });


    }
// log in. return token and id if the user is exist
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
                    String[] TokenAndId = new String[2];
                    TokenAndId=response.body();

                    TOKEN=TokenAndId[0];
                    ID=TokenAndId[1];
                    intent.putExtra("token",TOKEN);

                    GetUser();


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
                Toast.makeText(MainActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }


    // get- in id, return user
    public void GetUser()
    {

        Call<String> call = rtfBase.getUser(ID);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {

                if(response.code() == 200)
                {

                    //success
                    intent.putExtra("token",TOKEN);
                    intent.putExtra("businessUserInGson",response.body());
                    intent.putExtra("id",ID);
                    Toast.makeText(MainActivity.this, "You have logged in successfully", Toast.LENGTH_LONG).show();
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
                Toast.makeText(MainActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

}
