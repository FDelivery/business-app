package com.project.fdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
        Call<Void> call = rtfBase.insertNewDelivery(delivery);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}