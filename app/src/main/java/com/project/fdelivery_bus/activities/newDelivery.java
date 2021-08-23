package com.project.fdelivery_bus.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.project.fdelivery_bus.classes.Address;
import com.project.fdelivery_bus.classes.Delivery;
import com.project.fdelivery_bus.R;
import com.project.fdelivery_bus.classes.RetrofitBase;
import com.project.fdelivery_bus.classes.RetrofitInterface;
import com.project.fdelivery_bus.classes.SocketIO;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class newDelivery extends AppCompatActivity {
    private EditText CPhone, CName, Date, Time,Ccost;
    private EditText city,street,Cnote,number,floor,apartment,entrance;
    private Button submit;
    private Socket mSocket;
    private RetrofitInterface rtfBase = RetrofitBase.getRetrofitInterface();
    String USER,ID,TOKEN;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_delivery);

        CPhone = findViewById(R.id.CPhone);
        CName = findViewById(R.id.CName);
        Ccost = findViewById(R.id.cost);

        entrance = findViewById(R.id.CNentrance);
        city = findViewById(R.id.CNcity);
        street = findViewById(R.id.CNstreet);
        number = findViewById(R.id.CNnumber);
        floor = findViewById(R.id.CNfloor);
        apartment = findViewById(R.id.CNaprt);

        Cnote = findViewById(R.id.Cnote);
        Date = findViewById(R.id.Date);
        Time = findViewById(R.id.Time);
        submit = findViewById(R.id.ndSubmit);
        Bundle extras = getIntent().getExtras();
        mSocket = SocketIO.getSocket();

        if(extras!=null)
        {
            TOKEN =extras.getString("token");
            USER = extras.getString("businessUserInGson");
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
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd");
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
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                        Time.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(newDelivery.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        });




//send the changes
        submit.setOnClickListener((v) -> {


                    String clientPhone = CPhone.getText().toString();
                    String clientName = CName.getText().toString();
                    String City = city.getText().toString();
                    String Street = street.getText().toString();
                    String Floor = floor.getText().toString();
                    String Apartment = apartment.getText().toString();
                    String Entrance = entrance.getText().toString();
                    String Number = number.getText().toString();
                    String Cost = Ccost.getText().toString();
                    String clientNote = Cnote.getText().toString();
                    String date = Date.getText().toString();
                    String time = Time.getText().toString();


                    if(clientPhone.isEmpty())
                    {
                        CPhone.setError("This field is necessary");
                        return;
                    }

                    if(Street.isEmpty()) {
                        street.setError("This field is necessary");
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
                    if(Cost.isEmpty())
                    {
                        Ccost.setError("This field is necessary");
                        return;
                    }

                    Address clientAddress = new Address(City,Street,Number);

                    if(!Apartment.isEmpty()) {
                        clientAddress.setApartment(Apartment);

                    }
                    if(!Floor.isEmpty()) {
                        clientAddress.setFloor(Floor);

                    }
                    if(Entrance.isEmpty()) {
                        clientAddress.setEntrance(Entrance);

                    }

                    Delivery delivery = new Delivery(clientAddress, clientPhone, clientName, clientNote,
                            time, date,"COURIER_SEARCHING",Double.parseDouble(Cost));
        handleSubmit(delivery);

        }
        );


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }

    //finish put delivery info so add the new delivery
    private void handleSubmit(Delivery delivery)
    {
        Intent intent=new Intent(this,MainBusiness.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                    intent.putExtra("businessUserInGson", USER);
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