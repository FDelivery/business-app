package com.project.fdelivery_bus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.fdelivery_bus.R;
import com.project.fdelivery_bus.classes.RetrofitBase;
import com.project.fdelivery_bus.classes.RetrofitInterface;

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

        ChangeEP=findViewById(R.id.ChangeEP);
        EmailEP= findViewById(R.id.EmailEP);
        NameEP= findViewById(R.id.NameEP);
        Phone1EP= findViewById(R.id.Phone1EP);
        Phone2EP= findViewById(R.id.Phone2EP);
        CityEP= findViewById(R.id.cityEP);
        StreetEP= findViewById(R.id.streetEP);
        NumberEP= findViewById(R.id.numberEP);
        FlootEP=findViewById(R.id.floorEP);
        AprtEP=findViewById(R.id.aprtEP);
        EntranceEP=findViewById(R.id.entranceEP);
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

//this sent the new info and update in DB
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
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

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