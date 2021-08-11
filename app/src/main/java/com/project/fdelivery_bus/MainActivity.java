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

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText EmailEt;
    private EditText PasswordEt;
    private Button on;
    private TextView ForgotPassword;
    private TextView NewBusiness;
    private RetrofitInterface rtfBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rtfBase = RetrofitBase.getRetrofitInterface();
        EmailEt = findViewById(R.id.Email);
        PasswordEt = findViewById(R.id.Password);
        on=findViewById(R.id.Connect);
        ForgotPassword = findViewById(R.id.forgotPass);
        NewBusiness = findViewById(R.id.newBusiness);


        on.setOnClickListener((v) -> {
            String email = EmailEt.getText().toString();
            if(email.isEmpty())
            {
                Toast.makeText(MainActivity.this, "Something went wrong" , Toast.LENGTH_LONG).show();

                EmailEt.setError("This field is necessary");
                return;
            }
            if(PasswordEt.getText().toString().isEmpty())
            {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                PasswordEt.setError("This field is necessary");
                return;
            }
            handleConnect();
        });
        ForgotPassword.setOnClickListener((v)->{                     //need to complete
         //   GetUser("6106919d6ffd57c4090b6285"); // for my test
        //    Intent i=new Intent(this,newDelivery.class);
        //    i.putExtra("token",response.body()); //נשמור את הטוקן לאקטיביטי הבא?

        //    startActivity(i);


        });
        NewBusiness.setOnClickListener((v) -> {
            startActivity(new Intent(this, RegisterNewBusiness.class));
        });


    }

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
                   // intent.putExtra("token",response.body()); //נשמור את הטוקן לאקטיביטי הבא?

                    String[] arr = new String[2];

                    arr=response.body();
                  //  Log.i("token1",arr[0]);
                   // Log.i("id1",arr[1]);

                    // Log.i("TESSSSSTTT",credentials.get("password"));
                    GetUser(arr[1]);


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
    public void GetUser(String id) // need to know how to use in accepted user
    {

        Intent intent = new Intent(this, BusinessProfile.class);

        Log.i("myTest2",id);

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

/*

 private void useUser(HashMap<String, String> credentials) // we get the user-id after login
 {

     Call<String> call = rtfBase.getUserId(credentials.get("email"));
     call.enqueue(new Callback<String>() {
         @Override
         public void onResponse(Call<String> call, Response<String> response) {
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
             if(response.code() == 200)
             {
                 Log.i("TESSSSSTTT",response.body());

                 GetUser(response.body());



             }
         }

         @Override
         public void onFailure(Call<String> call, Throwable t) {
             Toast.makeText(MainActivity.this, "Something went wrong" +t.getMessage(), Toast.LENGTH_LONG).show();

         }
     });
 }
*/


}
