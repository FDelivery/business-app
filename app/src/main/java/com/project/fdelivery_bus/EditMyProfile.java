package com.project.fdelivery_bus;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
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

public class EditMyProfile extends AppCompatActivity {
    private EditText NameEP,EmailEP,Phone1EP,Phone2EP;
    private EditText CityEP,StreetEP,NumberEP,FlootEP,AprtEP,EntranceEP;
    private RetrofitInterface rtfBase;
    private Button ChangeEP;
    String ID,TOKEN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);

        ChangeEP=(Button)findViewById(R.id.ChangeEP);
        EmailEP=(EditText) findViewById(R.id.EmailEP);
        NameEP=(EditText)findViewById(R.id.NameEP);
        Phone1EP=(EditText)findViewById(R.id.Phone1EP);
        Phone2EP=(EditText)findViewById(R.id.Phone2EP);
        CityEP=(EditText) findViewById(R.id.cityEP);
        StreetEP=(EditText) findViewById(R.id.streetEP);
        NumberEP=(EditText) findViewById(R.id.numberEP);
        FlootEP=(EditText) findViewById(R.id.floorEP);
        AprtEP=(EditText) findViewById(R.id.aprtEP);
        EntranceEP=(EditText) findViewById(R.id.entranceEP);
        rtfBase = RetrofitBase.getRetrofitInterface();

         Bundle extras = getIntent().getExtras();



        if(extras!=null)
        {
            ID = extras.getString("id");
            TOKEN=extras.getString("token");


        }
        ChangeEP.setOnClickListener((v) -> {


            updateUser(ID);
        });
    }


    private void updateUser(String id)
    {
        HashMap<String, String> map=new HashMap<>();
        String EmailText= EmailEP.getText().toString();
        String NameText= NameEP.getText().toString();
        String Phone1Text= Phone1EP.getText().toString();
        String Phone2Text= Phone2EP.getText().toString();


        if(!EmailText.isEmpty()) {
            map.put("email",EmailText);
        }
        if(!NameText.isEmpty()) {
            map.put("businessName",NameText);
        }
        if(!Phone1Text.isEmpty())
        {
            map.put("primaryPhone",Phone1Text);
        }
        if(!Phone2Text.isEmpty()) {
           map.put("secondaryPhone",Phone2Text);
        }


        Call<Void> call = rtfBase.updateUser(id,map);
    call.enqueue(new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if(response.code() == 200)
            {

                GetUser(id);
            }

            if(response.code() == 400 || response.code()==500)
            {
                //failure
                Toast.makeText(EditMyProfile.this, "this ID do not exist", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Toast.makeText(EditMyProfile.this, t.getMessage(), Toast.LENGTH_LONG).show();

        }
    });
    }

    // get- in id, return user after update info
    public void GetUser(String id)
    {

        Intent intent = new Intent(this, MainBusiness.class);

        Call<String> call = rtfBase.getUser(id);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {

                if(response.code() == 200)
                {
                    Toast.makeText(EditMyProfile.this, "We update successfully", Toast.LENGTH_LONG).show();
                    intent.putExtra("businessUserInGson",response.body());
                    intent.putExtra("id",id);
                    intent.putExtra("token",TOKEN);

                    startActivity(intent);
                    finish();
                }


                if(response.code() == 400 || response.code()==500)
                {
                    //failure
                    Toast.makeText(EditMyProfile.this, "this ID do not exist", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EditMyProfile.this,t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

}