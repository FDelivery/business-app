package com.project.fdelivery_bus;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newDelivery extends AppCompatActivity {
    private EditText CPhone, CName, Caddress, Cnote, Date, Time;
    private CheckBox Bike, Car;
    private Button submit;
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
        submit.setOnClickListener(v -> handleSubmit());


    }

    private void handleSubmit()
    {
        String clientPhone = CPhone.getText().toString();
        String clientName = CName.getText().toString();
        String clientAddress = Caddress.getText().toString();
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
        if(clientAddress.isEmpty())
        {
            Caddress.setError("This field is necessary");
            return;
        }
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
        Call<String> call = rtfBase.insertNewDelivery(delivery);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.code() == 400)
                {
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