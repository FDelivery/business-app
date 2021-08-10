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

public class EditMyProfile extends AppCompatActivity {
    private EditText AddressEP;
    private EditText EmailEP;
    private Button ChangeEP;
    private TextView TextEP;
    private EditText Phone1EP;
    private EditText Phone2EP;
    private EditText NameEP;
    private RetrofitInterface rtfBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);

        AddressEP=(EditText)findViewById(R.id.AddressEP);
        ChangeEP=(Button)findViewById(R.id.ChangeEP);
        EmailEP=(EditText) findViewById(R.id.EmailEP);
        NameEP=(EditText)findViewById(R.id.NameEP);
        TextEP=(TextView)findViewById(R.id.TextEP);
        Phone1EP=(EditText)findViewById(R.id.Phone1EP);
        Phone2EP=(EditText)findViewById(R.id.Phone2EP);
        rtfBase = RetrofitBase.getRetrofitInterface();


        ChangeEP.setOnClickListener((v) -> {
            updateUser("610803dc57237f3532b78d70");
            startActivity(new Intent(this, BusinessProfile.class));
        });
    }


    private void updateUser(String id)
    {
        HashMap<String, String> map=new HashMap<>();
        String emailText= EmailEP.getText().toString();
        String NameText= NameEP.getText().toString();
        String Phone1Text= Phone1EP.getText().toString();
        String Phone2Text= Phone2EP.getText().toString();


        if(!emailText.isEmpty()) {
            map.put("email",emailText);
        }
        if(!NameText.isEmpty()) {
            map.put("firstName",NameText);
        }
        if(!Phone1Text.isEmpty())
        {
            map.put("primaryPhone",Phone1Text);
        }
        if(!Phone2Text.isEmpty()) {
           map.put("secondaryPhone",Phone2Text);
        }

Log.i("mytest",map.get("primaryPhone")+ " "+ map.get("firstName"));

        Call<Void> call = rtfBase.updateUser(id,map);
    call.enqueue(new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if(response.code() == 200)
            {

                Toast.makeText(EditMyProfile.this, "We update successfully", Toast.LENGTH_LONG).show();

            }

            if(response.code() == 400 || response.code()==500)
            {
                //failure
                Toast.makeText(EditMyProfile.this, "this ID do not exist", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Toast.makeText(EditMyProfile.this, "Something went wrong " +t.getMessage(), Toast.LENGTH_LONG).show();

        }
    });
    }



}