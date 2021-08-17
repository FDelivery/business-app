package com.project.fdelivery_bus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.List;

import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class newDelivery extends AppCompatActivity {
    private EditText CPhone, CName, Date, Time;
    private EditText city,street,Cnote,number,floor,apartment,entrance;
    private CheckBox Motorbike, Car, Bicycle;
    private Button submit;
    private Socket mSocket;
    private RetrofitInterface  rtfBase = RetrofitBase.getRetrofitInterface();
    String FromIntent,ID,TOKEN;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_delivery);

        CPhone = (EditText)findViewById(R.id.CPhone);
        CName = (EditText)findViewById(R.id.CName);

        entrance = (EditText)findViewById(R.id.CNentrance);
        city = (EditText)findViewById(R.id.CNcity);
        street = (EditText)findViewById(R.id.CNstreet);
        number = (EditText)findViewById(R.id.CNnumber);
        floor = (EditText)findViewById(R.id.CNfloor);
        apartment = (EditText)findViewById(R.id.CNaprt);

        Cnote = (EditText)findViewById(R.id.Cnote);
        Date = (EditText)findViewById(R.id.Date);
        Time = (EditText)findViewById(R.id.Time);
        Car = (CheckBox)findViewById(R.id.car);
        Bicycle=(CheckBox)findViewById(R.id.bicycle);
        Motorbike = (CheckBox)findViewById(R.id.motorbike);
        submit = findViewById(R.id.ndSubmit);
        Bundle extras = getIntent().getExtras();
        mSocket = SocketIO.getSocket();

        if(extras!=null)
        {
            TOKEN =extras.getString("token");
            FromIntent = extras.getString("businessUserInGson");
            ID =extras.getString("id");
        }


        Date.setInputType(InputType.TYPE_NULL);
        Time.setInputType(InputType.TYPE_NULL);

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(Date);
            }

            private void showDateDialog(EditText Date) {
                final Calendar calendar=Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd");
                        Date.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                };

                new DatePickerDialog(newDelivery.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(Time);
            }

            private void showTimeDialog(EditText time) {

                final Calendar calendar=Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                        Time.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(newDelivery.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        });





        submit.setOnClickListener((v) -> {


                    String clientPhone = CPhone.getText().toString();
                    String clientName = CName.getText().toString();
                    String City = city.getText().toString();
                    String Street = street.getText().toString();
                    String Floor = floor.getText().toString();
                    String Apartment = apartment.getText().toString();
                    String Entrance = entrance.getText().toString();
                    String Number = number.getText().toString();
                    Address clientAddress = new Address(City,Street,Number,Apartment,Floor,Entrance);
                    String clientNote = Cnote.getText().toString();
                    String date = Date.getText().toString();
                    String time = Time.getText().toString();
                    Boolean car = Car.isChecked();
                    Boolean motorbike = Motorbike.isChecked();
                    Boolean bicycle = Bicycle.isChecked();
                    if(clientPhone.isEmpty())
                    {
                        CPhone.setError("This field is necessary");
                        return;
                    }
                    if(Apartment.isEmpty()) {
                        apartment.setError("This field is necessary");
                        return;
                    }
                    if(Floor.isEmpty()) {
                        floor.setError("This field is necessary");
                        return;
                    }
                    if(Street.isEmpty()) {
                        street.setError("This field is necessary");
                        return;
                    }
                    if(Entrance.isEmpty()) {
                        entrance.setError("This field is necessary");
                        return;
                    }
                    if(Number.isEmpty()) {
                        number.setError("This field is necessary");
                        return;
                    }
                    if(City.isEmpty()) {
                        city.setError("This field is necessary");
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
                    Delivery delivery = new Delivery(clientAddress, clientPhone, clientName, clientNote, time, date,"COURIER_SEARCHING");
        handleSubmit(delivery);

        }
        );


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }

    private void handleSubmit(Delivery delivery)
    {
        Intent intent=new Intent(this,MainBusiness.class);

       // Call<String> call = rtfBase.insertNewDelivery("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTYyODE4ODY3MywianRpIjoiYjA4Zjc0ODYtMzY4Yy00NzQwLWI1OTgtZWY5NmQwMWRlMTNkIiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjYxMDgwM2RjNTcyMzdmMzUzMmI3OGQ3MCIsIm5iZiI6MTYyODE4ODY3MywiZXhwIjoxNjI4NzkzNDczfQ.cguZk7JFCehGHVeuoFQaXcfEii3s1mbz3MSwrzdfAnY",delivery);

        Call<String> call = rtfBase.insertNewDelivery("Bearer "+TOKEN,delivery);
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
                    intent.putExtra("id",ID);
                    intent.putExtra("businessUserInGson",FromIntent);
                    intent.putExtra("token",TOKEN);
                    delivery.setId(response.body());
                    Toast.makeText(newDelivery.this, "successfully add the new delivery",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    mSocket.emit("delivery_posted", ID);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                Toast.makeText(newDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }











}