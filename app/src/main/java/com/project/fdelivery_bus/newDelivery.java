package com.project.fdelivery_bus;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newDelivery extends AppCompatActivity {
    private EditText CPhone, CName, Caddress, Cnote, Date, Time;
    private CheckBox Bike, Car;
    private Button submit, getDelivery;
    private RetrofitInterface  rtfBase = RetrofitBase.getRetrofitInterface();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_delivery);

        CPhone = (EditText)findViewById(R.id.CPhone);
        CName = (EditText)findViewById(R.id.CName);
        Caddress = (EditText)findViewById(R.id.Caddress);
        Cnote = (EditText)findViewById(R.id.Cnote);
        Date = (EditText)findViewById(R.id.Date);
        Time = (EditText)findViewById(R.id.Time);
        Car = (CheckBox)findViewById(R.id.car);
        Bike = (CheckBox)findViewById(R.id.bike);
        submit = findViewById(R.id.ndSubmit);
        getDelivery = findViewById(R.id.forGetDelivery);

        getDelivery.setOnClickListener(v -> GetDeliveries("610a910c0a590b7dab5658ee"));

        submit.setOnClickListener((v) -> {
                    String clientPhone = CPhone.getText().toString();
                    String clientName = CName.getText().toString();
                    Address clientAddress = new Address(Caddress.getText().toString(),"gff","gg");
                    String clientNote = Cnote.getText().toString();
                    String date = Date.getText().toString();
                    String time = Time.getText().toString();
                    Boolean car = Car.isChecked();
                    Boolean bike = Bike.isChecked();
                    if(clientPhone.isEmpty())
                    {
                        CPhone.setError("This field is necessary");
                        return;
                    }
                   /* if(clientAddress.getApartment().isEmpty())                           /// לבדוק שלא ריק כל החלקים בכתובת
                    {
                        Caddress.setError("This field is necessary");
                        return;
                    }*/
                    if(clientName.isEmpty())
                    {
                        CName.setError("This field is necessary");
                        return;
                    }
                    if(date.isEmpty())
                    {
                        Date.setError("This field is necessary");
                        return;
                    }
                    if(time.isEmpty())
                    {
                        Time.setError("This field is necessary");
                        return;
                    }
                    Delivery delivery = new Delivery(clientAddress, clientPhone, clientName, clientNote, time, date);
        handleSubmit(delivery);

        }
        );


    }

    private void handleSubmit(Delivery delivery)
    {

        Call<String> call = rtfBase.insertNewDelivery("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTYyODA4MjQ0NSwianRpIjoiMTJjZWI0ZjgtNTY3NC00MTQwLTg5NzMtOTUzNmM5YmYzNWEyIiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjYxMGE5MTBjMGE1OTBiN2RhYjU2NThlZSIsIm5iZiI6MTYyODA4MjQ0NSwiZXhwIjoxNjI4Njg3MjQ1fQ.1ZwkR1jWQ7i8FI-6HyPZquNgTLx1OzSAuHH42br0B34",delivery);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.code() == 400 || response.code() == 401)
                {
                  //  Log.i("newTest",response.body());
                    Toast.makeText(newDelivery.this, "we have a problem",Toast.LENGTH_LONG).show();

                }
                if(response.code() == 200)
                {
                    delivery.setId(response.body());
                    Toast.makeText(newDelivery.this, "successfully add the new delivery",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                Toast.makeText(newDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }




    private void GetDelivery(String id)
    {
        Call<String> call = rtfBase.getDelivery(id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {

                if(response.code() == 400)
                {
                    Toast.makeText(newDelivery.this, "this ID do not exist",Toast.LENGTH_LONG).show();

                }
                if(response.code() == 200)
                {
                    Log.i("TEST1",response.body());
                    Delivery GSON = new Gson().fromJson(response.body(),Delivery.class);
                    Log.i("TEST2",GSON.getClientName());
                    Toast.makeText(newDelivery.this, "We found your Delivery",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(newDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

    private void GetDeliveries(String id)
    {
        Call<List<String>> call = rtfBase.getDeliveries(id);

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response)
            {

                if(response.code() == 400)
                {
                    Toast.makeText(newDelivery.this, "this ID do not exist",Toast.LENGTH_LONG).show();

                }
                if(response.code() == 200)
                {
                    Log.i("TEST1", String.valueOf(response.body()));
                  //  Delivery GSON = new Gson().fromJson(response.body(),Delivery.class);
                 //   Log.i("TEST2",GSON.getClientName());
                    Toast.makeText(newDelivery.this, "We found your Delivery",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(newDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }



    private void UpdateDelivery(String id)
    {
        Call<String> call = rtfBase.updateDelivery(id);
        call.enqueue(new Callback<String>() {
          @Override
          public void onResponse(Call<String> call, Response<String> response)
         {
             {

                 if(response.code() == 400)
                 {
                     Toast.makeText(newDelivery.this, "do not success",Toast.LENGTH_LONG).show();

                 }
                 if(response.code() == 200 || response.code() == 204)
                 {
                     Toast.makeText(newDelivery.this, "update successfully",Toast.LENGTH_LONG).show();

                 }
             }

         }

         @Override
         public void onFailure(Call<String> call, Throwable t) {
             Toast.makeText(newDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

         }
});




    }




}